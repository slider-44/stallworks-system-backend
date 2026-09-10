FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY core-app/pom.xml core-app/pom.xml
COPY core-common/pom.xml core-common/pom.xml
COPY core-sales/pom.xml core-sales/pom.xml
COPY core-inventory/pom.xml core-inventory/pom.xml
COPY core-attendance/pom.xml core-attendance/pom.xml
COPY core-payroll/pom.xml core-payroll/pom.xml
COPY core-reports/pom.xml core-reports/pom.xml
RUN mvn -B -pl core-app -am dependency:go-offline

COPY core-app/src core-app/src
COPY core-common/src core-common/src
COPY core-sales/src core-sales/src
COPY core-inventory/src core-inventory/src
COPY core-attendance/src core-attendance/src
COPY core-payroll/src core-payroll/src
COPY core-reports/src core-reports/src
RUN mvn -B -pl core-app -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/core-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]