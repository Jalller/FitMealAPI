# Use OpenJDK 17
FROM eclipse-temurin:17-jdk

# Set working dir
WORKDIR /app

# Copy project
COPY . .

# Build the app (skip tests for speed)
RUN ./mvnw package -DskipTests

# Run the built JAR
CMD ["java", "-jar", "target/moviefetcher-1.0.0.jar"]
