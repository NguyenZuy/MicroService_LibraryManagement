# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your JAR file into the container
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose the Book Service port
EXPOSE 9191

# Define the command to run your application (using the new JAR name)
CMD ["java", "-jar", "api-gateway.jar"]
