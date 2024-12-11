plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "ca.gbc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/release") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3") // Update version if necessary
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(22))
        }
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/release") }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.cloud:spring-cloud-starter-gateway")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.springframework.boot:spring-boot-testcontainers")
        testImplementation("org.testcontainers:junit-jupiter:1.19.0")
        testImplementation("org.testcontainers:mongodb:1.19.0")
        testImplementation("io.rest-assured:rest-assured:5.3.0")
    }

    configurations.all {
        resolutionStrategy {
            force("org.springframework.cloud:spring-cloud-starter-gateway:3.1.4") // Example version
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        mainClass.convention("ca.gbc.apigateway.ApiGatewayApplication") // Default fallback
        mainClass.set(
            when (project.name) {
                "api-gateway" -> "ca.gbc.apigateway.ApiGatewayApplication"
                "inventory-service" -> "ca.gbc.inventoryservice.InventoryServiceApplication"
                "order-service" -> "ca.gbc.orderservice.OrderServiceApplication"
                "product-service" -> "ca.gbc.productservice.ProductServiceApplication"
                else -> throw IllegalArgumentException("Unknown subproject: ${project.name}")
            }
        )
    }
}
