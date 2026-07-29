package com.stallworks.tako.gateway.config;

/**
 * DEPRECATED / unused — routing here (via Spring Cloud Gateway MVC's
 * RouterFunction beans) proved unreliable in testing: requests kept landing
 * on core-service regardless of path, across two different implementations
 * (one shared builder, then separate beans per route). Replaced by
 * com.stallworks.tako.gateway.proxy.ProxyController, a plain Spring MVC
 * reverse proxy with explicit if/else routing and no framework-level
 * ordering ambiguity. Left as a no-op class (rather than deleted) so the
 * history/reasoning is visible in the codebase; safe to delete once the
 * ProxyController approach is confirmed stable.
 */
public class GatewayRoutesConfig {
}
