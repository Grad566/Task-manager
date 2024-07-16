plugins {
	java
	id("org.springframework.boot") version "3.2.8-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.6"
	checkstyle
	application
	id("io.freefair.lombok") version "8.6"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.data:spring-data-jpa:3.1.3")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.1")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	runtimeOnly("com.h2database:h2")
	implementation("org.postgresql:postgresql:42.7.3")

//	implementation("jakarta.validation:jakarta.validation-api:3.1.0")

	implementation("org.mapstruct:mapstruct:1.6.0.Beta2")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")

	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	implementation("net.datafaker:datafaker:2.0.2")
	implementation("org.instancio:instancio-junit:3.3.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

application {
	mainClass = "hexlet.code.app.AppApplication"
}
