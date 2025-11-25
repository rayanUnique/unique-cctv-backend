FROM maven:3.9-eclipse-temurin-22 AS build 
WORKDIR /app 
COPY . .   
RUN mvn clean package 
 
FROM eclipse-temurin:22-jre 
WORKDIR /app 
COPY --from=build /app/target/unique-cctv-backend-1.0.0.jar app.jar 
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "app.jar"] 
