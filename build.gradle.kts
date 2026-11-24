plugins {
    id("java")
    id("io.github.ben-manes.versions") version "0.67.0"
    id("com.diffplug.spotless") version "8.11.0"
    jacoco
    application
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "hexlet.code.App"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("info.picocli:picocli:4.7.7")
    implementation("tools.jackson.core:jackson-databind:3.2.3")
    implementation("tools.jackson.dataformat:jackson-dataformats-text:3.2.3")
    implementation("tools.jackson.dataformat:jackson-dataformat-yaml:3.2.3")
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat().aosp()
        formatAnnotations()
        leadingTabsToSpaces(4)
    }
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.15"
}

val coverageExcludes = listOf("hexlet/code/App.class")

fun JacocoReportBase.excludeEntryPoint() {
    classDirectories.setFrom(
        files(classDirectories.files.map { fileTree(it) { exclude(coverageExcludes) } }),
    )
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    excludeEntryPoint()
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)

    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}



