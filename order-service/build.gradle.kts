plugins {
	java
	kotlin("jvm") version "1.9.10"
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
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.testcontainers:junit-jupiter:1.19.0")
	testImplementation("org.testcontainers:postgresql:1.19.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set("ca.gbc.orderservice.OrderServiceApplication")
}
