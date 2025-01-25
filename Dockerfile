# Description: Dockerfile for the Inventario application
# Author: Abdulhamid Mazroua

## Dependency resolution and build stage
# This stage no longer needed as it is managed by CI/CD pipeline
#FROM maven:3.9.9-eclipse-temurin-22 AS builder
#WORKDIR /app
## Copy only pom.xml to cache dependencies
#COPY ./pom.xml /app/pom.xml
#RUN mvn dependency:go-offline -B
## Copy the entire source for build
#COPY ./src /app/src
#RUN mvn clean install -DskipTests

# Runtime stage
FROM eclipse-temurin:22-jdk-jammy
WORKDIR /app
# Copy the wait-for-it script and ensure it's executable
COPY ./wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
# Define build argument for the JAR file name
ARG JAR_FILE
# Copy the built application JAR from the builder stage
COPY ${JAR_FILE} /app/app.jar
# Run the application
ENTRYPOINT ["./wait-for-it.sh", "inventario-db:3306", "--timeout=0", "--", "java", "-jar", "app.jar"]
