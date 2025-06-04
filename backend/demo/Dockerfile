FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN ./mvnw clean install

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
