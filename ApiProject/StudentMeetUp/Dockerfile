# Use the official OpenJDK base image
FROM openjdk:25-ea

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/meetupapi.jar meetupapi.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "meetupapi.jar"]