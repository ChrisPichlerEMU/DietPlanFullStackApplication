# Use a base image with JDK
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file built by Maven (Assume the JAR is in the target folder)
COPY target/dietPlanFullStackApplication-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the application's port (for example, 8080)
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "application.jar"]
