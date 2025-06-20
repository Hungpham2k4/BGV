FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/BGV-0.0.1-SNAPSHOT.jar"]
