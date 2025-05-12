# Build Stage
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper files first (if using them for caching)
COPY gradlew ./
COPY gradle/ ./gradle/

# Copy full project and build
COPY . /app
RUN gradle build -x test

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
