import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.graalvm.buildtools.native") version "0.10.6"
    id("com.diffplug.spotless") version "8.0.0"
}
val fastutilVersion = "8.5.18"
val jjwtVersion = "0.13.0"
//val hypersistenceUtilsVersion = "3.11.0"
val bucket4jVersion = "0.13.0"
val captchaVersion = "1.4.0"

group = "top.toobee"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

spotless {
    java {
        //eclipse().configFile("gradle/idea-eclipse.xml")
        googleJavaFormat("1.32.0").aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

repositories {
	mavenCentral()
}

dependencies {
	setOf("web", "security", "jdbc", "websocket", "data-jpa", "amqp", "cache").forEach {
		implementation("org.springframework.boot:spring-boot-starter-$it")
	}
	//implementation("org.apache.commons:commons-lang3:3.17.0")
	implementation("it.unimi.dsi:fastutil:$fastutilVersion")
    //implementation("com.giffing.bucket4j.spring.boot.starter:bucket4j-spring-boot-starter:$bucket4jVersion")
    //implementation("com.anji-plus:captcha-spring-boot-starter:$captchaVersion")
    //implementation("io.hypersistence:hypersistence-utils-hibernate-70:$hypersistenceUtilsVersion")
	runtimeOnly("org.postgresql:postgresql")

	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
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
    systemProperty("spring.profiles.active", "dev")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.processResources {
	exclude("**/custom.yml", "**/static-dev/**")
}