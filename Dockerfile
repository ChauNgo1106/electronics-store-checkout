FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN ./mvnw clean package
CMD ["java", "-jar", "target/electronic-store-0.0.1-SNAPSHOT.jar"]