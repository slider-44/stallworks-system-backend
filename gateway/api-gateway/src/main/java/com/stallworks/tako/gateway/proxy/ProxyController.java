package com.stallworks.tako.gateway.proxy;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Manual reverse proxy for every /api/v1/** request.
 *
 * auth-service owns /api/v1/accounts/** and /api/v1/auth/**; everything else
 * under /api/v1/** belongs to core-service. This is deliberately plain
 * Spring MVC (an explicit if/else path check) rather than Spring Cloud
 * Gateway's routing DSL, which proved unreliable here — every request kept
 * landing on core-service regardless of path, across two different
 * RouterFunction-based implementations. This version has no framework-level
 * route ordering to get wrong.
 *
 * Forwards method, headers (except Host/Content-Length, which RestClient
 * sets itself based on the actual outgoing request), query string, and body
 * as-is, and passes the downstream status/headers/body straight back —
 * .exchange() is used instead of .retrieve() specifically so 4xx/5xx
 * responses come back as normal data instead of being thrown as exceptions.
 */
@RestController
public class ProxyController {

	private static final Logger log = LoggerFactory.getLogger(ProxyController.class);

	private final RestClient restClient = RestClient.create();
	private final String authUri;
	private final String coreUri;

	public ProxyController(
			@Value("${app.auth-uri}") String authUri,
			@Value("${app.core-uri}") String coreUri) {
		this.authUri = authUri;
		this.coreUri = coreUri;
	}

	@RequestMapping("/api/v1/**")
	public ResponseEntity<byte[]> proxy(HttpServletRequest request, @RequestBody(required = false) byte[] body) {
		String path = request.getRequestURI();
		String target = (path.startsWith("/api/v1/accounts") || path.startsWith("/api/v1/auth"))
				? authUri
				: coreUri;

		String query = request.getQueryString();
		String url = target + path + (query != null ? "?" + query : "");

		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			// host/content-length: RestClient recomputes these for the outgoing
			// request. accept-encoding: deliberately dropped so the upstream never
			// compresses the response — this proxy relays raw bytes with no
			// decompression handling, so a compressed body plus a forwarded
			// Content-Encoding header would otherwise produce a mismatched,
			// invalid response.
			if (name.equalsIgnoreCase("host")
					|| name.equalsIgnoreCase("content-length")
					|| name.equalsIgnoreCase("accept-encoding")) {
				continue;
			}
			Enumeration<String> values = request.getHeaders(name);
			while (values.hasMoreElements()) {
				headers.add(name, values.nextElement());
			}
		}

		try {
			return restClient.method(HttpMethod.valueOf(request.getMethod()))
					.uri(url)
					.headers(h -> h.addAll(headers))
					.body(body != null ? body : new byte[0])
					.exchange((req, res) -> {
						byte[] responseBody = res.getBody().readAllBytes();

						// Whitelist, not blacklist: forward only Content-Type from the
						// downstream response and let Tomcat generate everything else
						// itself (Date, Server, Connection, Content-Length, ...).
						// Forwarding the upstream's full header set — even after
						// stripping the obvious hop-by-hop ones — still left Tomcat
						// writing a response with headers it also generates itself
						// (e.g. Date), producing duplicates that Cloud Run's Envoy
						// front end rejected as a protocol error. Content-Type is the
						// only header actually needed for the client to parse the body
						// correctly, so this sidesteps the whole category of issues.
						HttpHeaders responseHeaders = new HttpHeaders();
						MediaType contentType = res.getHeaders().getContentType();
						if (contentType != null) {
							responseHeaders.setContentType(contentType);
						}

						return ResponseEntity.status(res.getStatusCode())
								.headers(responseHeaders)
								.body(responseBody);
					});
		} catch (Exception e) {
			log.error("Proxy call failed: {} {} -> {}", request.getMethod(), path, url, e);
			return ResponseEntity.status(502)
					.contentType(MediaType.APPLICATION_JSON)
					.body(("{\"error\":\"proxy failed\",\"target\":\"" + url + "\",\"message\":\""
							+ String.valueOf(e.getMessage()).replace("\"", "'") + "\"}").getBytes());
		}
	}

}
