# Build Stage
FROM gradle:8-jdk22-alpine AS builder
COPY --chown=gradle:gradle ../.. /home/gradle/src
WORKDIR /home/gradle/src/order-service
RUN gradle build -x test

# Package Stage
FROM openjdk:22-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/order-service/build/libs/*.jar /app/order-service.jar

# Environment Variables
ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PASSWORD=password

# Expose Port
EXPOSE 8082

# Entry Point
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
