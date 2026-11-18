# Stage 1: Build con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime ligero Java 21 + MariaDB opcional para despliegue autónomo en Render
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends mariadb-server mariadb-client && \
    rm -rf /var/lib/apt/lists/*

COPY --from=builder /build/target/*.jar /app/app.jar
COPY dbgestor_init.sql /app/dbgestor_init.sql
COPY docker-entrypoint.sh /app/docker-entrypoint.sh

RUN sed -i 's/\r$//' /app/docker-entrypoint.sh && chmod +x /app/docker-entrypoint.sh

EXPOSE 8081
ENTRYPOINT ["/app/docker-entrypoint.sh"]
