plugins {
    `java-library`
}

group = "com.naderaria.ecommerce"
version = "0.0.1-SNAPSHOT"

dependencies {
    api("org.springframework:spring-context:7.0.8")
// api("org.springframework:spring-web")
    api("org.springframework.security:spring-security-core:7.1.0")
    api("org.springframework.security:spring-security-web:7.1.0")
    api("org.springframework.security:spring-security-config:7.1.0")
    api("org.springframework.security:spring-security-oauth2-jose:7.1.0")
    api("org.springframework.security:spring-security-oauth2-resource-server:7.1.0")
    api("jakarta.servlet:jakarta.servlet-api:6.1.0")

    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")
    implementation("tools.jackson.core:jackson-databind:3.1.4")
    implementation(project(":common:common-core"))

}
