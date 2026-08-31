FROM eclipse-temurin:25-jre

WORKDIR /app

COPY identity-service/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]