# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your JAR file into the container
COPY target/ServiceRegistry-0.0.1-SNAPSHOT.jar service-registry.jar

# Expose the Eureka server port
EXPOSE 8761

# Define the command to run your application (using the new JAR name)
CMD ["java", "-jar", "service-registry.jar"]
