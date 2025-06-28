# Use JDK 24 for building
FROM openjdk:24-jdk-slim as builder

WORKDIR /app

# Copy parent POM and Maven wrapper from root
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copy module files
COPY backend/pom.xml backend/
COPY backend/src backend/src
COPY frontend/pom.xml frontend/

# Make mvnw executable and build only backend module
RUN chmod +x ./mvnw
RUN ./mvnw clean package -pl backend -am -DskipTests

# Use JDK 24 runtime
FROM openjdk:24-jre-slim

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/backend/target/backend-1.0.0.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]