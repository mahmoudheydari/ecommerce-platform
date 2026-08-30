#Stage 1: Build
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./

COPY common common
COPY identity-service identity-service
COPY product-service product-service
COPY cart-service cart-service

COPY gradle-cache/.gradle /root/.gradle

RUN sed -i 's/\r$//' gradlew && chmod +x ./gradlew

RUN ./gradlew :identity-service:bootJar -x test --no-daemon

#Stage 2: Runtime
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/identity-service/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]