# Stage 1: Build the application
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app

# Copy Gradle wrapper and configuration files first to leverage Docker's layer caching
COPY ../build.gradle.kts ../settings.gradle.kts ../gradle.properties ./
COPY gradle ./gradle

# Copy the rest of the application files
COPY src ./src

# Run the Gradle build to create the JAR file
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
