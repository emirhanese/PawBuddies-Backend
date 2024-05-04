# Stage 1: Build the application
FROM openjdk:21
LABEL authors="Emirhan Ese"

# Set the working directory inside the container
WORKDIR /app

COPY /target/pawbuddies.jar ./pawbuddies.jar

ENTRYPOINT ["java", "-jar", "/pawbuddies.jar"]

EXPOSE 8080
