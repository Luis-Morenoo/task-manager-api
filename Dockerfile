# Stage 1: Build the application using Gradle
FROM eclipse-temurin:21-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files first (for caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Give execute permission to gradlew
RUN chmod +x gradlew

# Build the JAR file, skipping tests since CI already ran them
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy only the built JAR from Stage 1
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 so Railway knows what port to connect to
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]