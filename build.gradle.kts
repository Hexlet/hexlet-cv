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
    // mainClass.set("io.hexlet.blog.Application")
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
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.junit.jupiter)

    //testImplementation(libs.javafaker)

    // Inertia4J
    implementation(libs.inertia4jSpring)
    // implementation(libs.inertia4jSpringStarter)

    // 📧 Email (добавить)
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // 🎯 Thymeleaf для email шаблонов (добавить)
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    //JWT
    // implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    //runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    //runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.flywaydb:flyway-core")

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
    //reports {
    //    xml.required.set(true)
    //}
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
// убрал так как методы выстраивает в цепочки и конфликтует в checkstyle
// в комментариях рушит отступы заменя на *
        //eclipse().sortMembersEnabled(true)
// убрал форматирование аннотаций так как при выстраивании в одну строку
// строка получается слишком длинной и конфликтует в checkstyle
       // formatAnnotations()
        leadingTabsToSpaces(4)
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    sourceCompatibility = JavaVersion.VERSION_21
}

// sonar {
//     properties {
//         property("sonar.projectKey", "hexlet-boilerplates_java-package")
//         property("sonar.organization", "hexlet-boilerplates")
//         property("sonar.host.url", "https://sonarcloud.io")
//     }
// }
