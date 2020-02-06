plugins {
    kotlin("jvm") version "1.3.61"
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "com.medina.juan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("javax.validation:validation-api:2.0.1.Final")

    testRuntimeOnly("org.hibernate.validator:hibernate-validator:6.0.2.Final")
    testRuntimeOnly("org.hibernate.validator:hibernate-validator-annotation-processor:6.0.2.Final")
    testRuntimeOnly("javax.el:javax.el-api:3.0.0")
    testRuntimeOnly("org.glassfish.web:javax.el:2.2.6")

    testImplementation(kotlin("test"))
    testRuntimeOnly(kotlin("reflect"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "gfm"
        outputDirectory = "src/docs/api"
    }

}