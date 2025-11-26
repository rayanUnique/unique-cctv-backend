# Use specific versions for better reproducibility
FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
# Copy source code
COPY src ./src

RUN mvn clean package -DskipTests

# Use the same Java version for build and runtime
FROM eclipse-temurin:22-jre-jammy

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/unique-cctv-backend-1.0.0.jar app.jar

# Create a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring \
    && chown -R spring:spring /app
USER spring

EXPOSE 8080

# Use shell form for environment variable expansion
ENTRYPOINT ["java", "-jar", "app.jar"]