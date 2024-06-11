# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your JAR file into the container
COPY target/OrderSlipService-0.0.1-SNAPSHOT.jar order-slip-service.jar

# Expose the Book Service port
EXPOSE 8085

# Define the command to run your application (using the new JAR name)
CMD ["java", "-jar", "order-slip-service.jar"]
