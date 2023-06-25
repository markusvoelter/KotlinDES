import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0"
}

group = "de.voelter"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.5.2")
}

java.sourceSets["main"].kotlin {
    srcDir("src/main/kotlin")
}

java.sourceSets["test"].kotlin {
    srcDir("src/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

