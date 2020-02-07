import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm") version "1.3.61"
    id("org.jetbrains.dokka") version "0.10.0"
    id("maven-publish")
    id("com.jfrog.bintray") version  "1.8.4"
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
    val dokka by getting(DokkaTask::class) {
        outputFormat = "gfm"
        outputDirectory = "src/docs/api"
    }

}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])

            groupId = "$group"
            artifactId = rootProject.name

            artifact(sourcesJar) {
                classifier = "sources"
            }
        }
    }
}

val bintrayUsername = (properties["bintrayUsername"] as String?) ?: System.getenv("BINTRAY_USER")
val bintrayApiKey = (properties["bintrayApiKey"] as String?) ?: System.getenv("BINTRAY_APIKEY")

if (bintrayUsername != null && bintrayApiKey != null) {
    bintray {
        user = bintrayUsername
        key = bintrayApiKey

        pkg.apply {
            repo = "juan-medina"
            name = rootProject.name

            websiteUrl = "https://github.com/juan-medina/regexp-dsl/"
            vcsUrl = "https://github.com/juan-medina/regexp-dsl.git"
            issueTrackerUrl = "https://github.com/juan-medina/regexp-dsl/issues"

            description = "Expressive Regular Expressions with a Domain Specific Language written in Kotlin"
            setLabels("kotlin")
            setLicenses("Apache 2.0")
            setPublications("default")
        }
    }
}
