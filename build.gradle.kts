plugins {
    java
}

allprojects {
    group = "com.naderaria.ecommerce"
    version = "0.0.1-SNAPSHOT"

    repositories {
        maven( url = uri("https://maven.aliyun.com/repository/public/"))
        maven( url = uri("https://maven.aliyun.com/repository/gradle-plugin"))
        maven( url = uri("https://maven.aliyun.com/repository/central"))

        mavenCentral()
        gradlePluginPortal()
    }
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        // Lombok
        compileOnly("org.projectlombok:lombok:1.18.46")
        annotationProcessor("org.projectlombok:lombok:1.18.46")

        // MapStruct
        implementation("org.mapstruct:mapstruct:1.6.3")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test:4.1.0")
    }
}