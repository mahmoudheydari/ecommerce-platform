plugins {
    `java-library`
}

group = "com.naderaria.ecommerce"
version = "0.0.1-SNAPSHOT"

dependencies {
    api("org.springframework.boot:spring-boot-starter-validation:4.1.0")
    api("com.fasterxml.jackson.core:jackson-annotations:2.18.3")

    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    testImplementation("org.springframework.boot:spring-boot-starter-test:4.1.0") {}
}