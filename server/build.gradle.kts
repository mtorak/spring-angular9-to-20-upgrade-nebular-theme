import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("org.springframework.boot") version "3.5.3"
  id("io.spring.dependency-management") version "1.1.7"
  kotlin("jvm") version "2.2.0"
  kotlin("plugin.spring") version "2.2.0"
}

group = "com.squrlabs"

version = "0.0.1-SNAPSHOT"

java { sourceCompatibility = JavaVersion.VERSION_21 }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // Data
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

  // Documentation
  implementation("org.springdoc:springdoc-openapi-webmvc-core:1.3.2")
  implementation("org.springdoc:springdoc-openapi-ui:1.3.2")
  implementation("org.springdoc:springdoc-openapi-kotlin:1.3.2")
  implementation("org.springdoc:springdoc-openapi-security:1.3.2")

  // Security
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("io.jsonwebtoken:jjwt:0.12.6")
  implementation("org.springframework.security:spring-security-oauth2-client")
  implementation("org.springframework.security:spring-security-oauth2-jose")
  implementation("org.springframework.security:spring-security-messaging")

  // Websocket
  implementation("org.springframework.boot:spring-boot-starter-websocket")

  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("org.springframework.security:spring-security-test")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
    jvmTarget.set(JvmTarget.JVM_21)
  }
}

tasks.withType<Test> { useJUnitPlatform() }
