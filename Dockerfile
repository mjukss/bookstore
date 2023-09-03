FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/bookstore-1.0.0 app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
