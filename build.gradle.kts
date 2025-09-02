import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "top.toobee"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	setOf("web", "security", "jdbc", "websocket", "data-jpa", "amqp").forEach {
		implementation("org.springframework.boot:spring-boot-starter-$it")
	}
	//implementation("org.apache.commons:commons-lang3:3.17.0")
	implementation("it.unimi.dsi:fastutil:8.5.16")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

/*
graalvmNative {
	binaries.all {
		buildArgs.add("--initialize-at-build-time=")
	}
}
 */

tasks.named<BootRun>("bootRun") {
	//workingDir = file("run").apply(File::mkdirs)
	standardInput = System.`in`
    systemProperties["spring.profiles.active"] = "dev"
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.processResources {
	exclude("**/custom.yml", "**/static-dev/**")
}