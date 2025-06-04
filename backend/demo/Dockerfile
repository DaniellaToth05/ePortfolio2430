# ---- Build Stage ----
FROM --platform=linux/amd64 eclipse-temurin:17 AS builder
WORKDIR /app

COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# ---- Runtime Stage ----
FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine AS runtime
WORKDIR /app

COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
