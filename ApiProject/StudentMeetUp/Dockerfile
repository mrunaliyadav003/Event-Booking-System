# Use the official OpenJDK base image
FROM openjdk:25-ea

# Copy the built jar file into the container
ADD target/meetupapi.jar meetupapi.jar
# Run the application
ENTRYPOINT ["java", "-jar", "meetupapi.jar"]