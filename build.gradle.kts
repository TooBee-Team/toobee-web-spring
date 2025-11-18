import net.ltgt.gradle.errorprone.errorprone
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
    alias(libs.plugins.updateCatalog)
	alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencyManagement)
    alias(libs.plugins.graalvmBuildTools)
    alias(libs.plugins.spotless)
    alias(libs.plugins.errorprone)
}

group = "top.toobee"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

spotless {
    java {
        target("src/**/*.java")
        targetExclude("build/**", "generated/**", "patches/**", "logs/**")
        googleJavaFormat().aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

repositories {
	mavenCentral()
}

dependencies {
	setOf("web", "security", "jdbc", "websocket", "data-jpa", "amqp", "cache", "validation").forEach {
		implementation("org.springframework.boot:spring-boot-starter-$it")
	}
    implementation(libs.caffeine)
    implementation(libs.captcha)
	implementation(libs.fastutil)
	implementation(libs.jjwt.api)
    implementation(libs.thumbnailator)

	runtimeOnly(libs.jjwt.impl)
	runtimeOnly(libs.jjwt.jackson)
    runtimeOnly(libs.postgresql)

    testImplementation(libs.bundles.test.spring)

    testRuntimeOnly(libs.junit.platformLauncher)

    errorprone(libs.errorprone.core)
    errorprone(libs.errorprone.refaster)

    //implementation("com.giffing.bucket4j.spring.boot.starter:bucket4j-spring-boot-starter:$bucket4jVersion")
    //implementation("io.hypersistence:hypersistence-utils-hibernate-70:$hypersistenceUtilsVersion")
    //implementation("org.apache.commons:commons-lang3:3.17.0")
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

tasks.withType<JavaCompile> {
    options.errorprone {
        enable("EqualsBrokenForNull")
        enable("NegativeBoolean")
        enable("InitializeInline")
        enable("Varifier")
        enable("ReturnMissingNullable")
        enable("FieldCanBeFinal")
        enable("FieldCanBeLocal")
        enable("FieldCanBeStatic")

        errorproneArgs.addAll(
            "-XepPatchLocation:$projectDir/patches"
        )
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.processResources {
	exclude("**/custom.yml", "**/static-dev/**")
}

tasks.test {
    onlyIf("runTests property present") {
        project.hasProperty("runTests")
    }
}

tasks.register<Exec>("applyPatches") {
    group = "custom"

    doFirst {
        val probe = providers.exec {
            commandLine("git", "--version")
            isIgnoreExitValue = true
        }
        if (probe.result.get().exitValue != 0) {
            throw GradleException("Git not found in PATH.")
        }
    }

    projectDir.resolve("patches").listFiles { _, name -> name.endsWith(".patch") }
        ?.forEach { commandLine("git", "apply", "--ignore-whitespace", it.absolutePath) }
}
