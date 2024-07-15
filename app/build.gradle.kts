plugins {
	java
	id("org.springframework.boot") version "3.2.8-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.6"
	checkstyle
	application
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

	runtimeOnly("com.h2database:h2")
	implementation("org.postgresql:postgresql:42.7.3")

	compileOnly("org.projectlombok:lombok:1.18.34")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

application {
	mainClass = "hexlet.code.app.AppApplication"
}
