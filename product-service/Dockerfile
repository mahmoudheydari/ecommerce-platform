FROM eclipse-temurin:25-jre

WORKDIR /app

COPY product-service/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]