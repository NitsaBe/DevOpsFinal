# Use JDK 24 for building
FROM openjdk:24-jdk-slim as builder

WORKDIR /app

# Copy parent POM and Maven wrapper from root
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copy module files
COPY frontend/pom.xml frontend/
COPY frontend/src frontend/src
COPY backend/pom.xml backend/

# Make mvnw executable and build only frontend module
RUN chmod +x ./mvnw
RUN ./mvnw clean package -pl frontend -am -DskipTests

# Use JDK 24 runtime
FROM openjdk:24-jre-slim

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/frontend/target/frontend-1.0.0.jar app.jar

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:3000/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]