FROM openjdk:22-jdk-slim 
COPY target/unique-cctv-backend-1.0.0.jar app.jar 
ENTRYPOINT ["java","-jar","/app.jar"] 
