import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    jacoco
    checkstyle
    alias(libs.plugins.lombok)
    alias(libs.plugins.versions)
    alias(libs.plugins.spotless)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.shadow)
    alias(libs.plugins.sonarqube)
}

group = "io.hexlet.blog"
version = "1.0-SNAPSHOT"

application {
    mainClass = "io.hexlet.cv.App"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") } // нужен для inertia4j
}

dependencies {
    // Spring Boot
    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootStarterDataJpa)
    implementation(libs.springBootStarterValidation)
    implementation(libs.springBootStarterActuator)
    implementation(libs.springBootStarterSecurity)
    implementation(libs.springBootStarterOauth2ResourceServer)
    implementation(libs.springBootDevtools)
    implementation(libs.springBootConfigProcessor)

    // OpenAPI
    implementation(libs.springdocOpenapiUi)

    // Utilities
    implementation(libs.jacksonDatabindNullable)
    implementation(libs.commonsLang3)
    implementation(libs.datafaker)
    implementation(libs.instancioJunit)
    implementation(libs.jsonunitAssertj)
    implementation(libs.guava)

    // MapStruct
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstructProcessor)

    // DB
    runtimeOnly(libs.h2)
    implementation(libs.postgresql);

    // Tests
    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.springSecurityTest)
    testImplementation(platform(libs.junitBom))
    testImplementation(libs.junitJupiter)
    testRuntimeOnly(libs.junitPlatformLauncher)

    // Inertia4J
    implementation(libs.inertia4jSpring)

    // Jackson JSR310 для поддержки LocalDateTime
    implementation(libs.jacksonDatatypeJsr310)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events =
            setOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
            )
        showStandardStreams = true
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        leadingTabsToSpaces(4)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}
