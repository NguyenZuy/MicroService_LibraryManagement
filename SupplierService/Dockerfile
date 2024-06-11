# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your JAR file into the container
COPY target/SupplierService-0.0.1-SNAPSHOT.jar supplier-service.jar

# Expose the Eureka server port
EXPOSE 8084

# Define the command to run your application (using the new JAR name)
CMD ["java", "-jar", "supplier-service.jar"]
