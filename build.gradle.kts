plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "com.github.locxter"
version = "1.0"
description = "This is the next generation rewrite of scrtcrtr, which is a GUI based ASCII art creator."

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.formdev:flatlaf:3.1.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.github.locxter.scrtcrtr.ng.MainKt")
}

tasks {
    val standalone = register<Jar>("standalone") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(standalone)
    }
}
