# Runtime stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
# Copy the wait-for-it script and ensure it's executable
COPY ./wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
# Copy the built application JAR from the builder stage
COPY --from=builder /app/target/inventario-0.0.1-SNAPSHOT.jar /app/app.jar
# Run the application
ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--timeout=0", "--", "java", "-jar", "app.jar"]
