import java.util.Properties

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
dependencies {
    intellijPlatform {
        val type = providers.gradleProperty("platformType")
        val version = providers.gradleProperty("platformVersion")
        create(type, version)

        instrumentationTools()

        plugin("org.vane.hub:1.0.0")
//        localPlugin(project(":VaneHub"))
    }
}

intellijPlatform {
    pluginConfiguration {
        group = "org.vane"
        version = "1.0.0"

        ideaVersion {
            sinceBuild = "232"
            untilBuild = "242.*"
        }
    }

    val properties = Properties()
    properties.load(project.file("../key/password.properties").inputStream())

    signing {
        certificateChainFile.set(file("../key/chain.crt"))
        privateKeyFile.set(file("../key/private.pem"))
        password.set(properties.getProperty("PRIVATE_KEY_PASSWORD"))
    }

    publishing {
        token.set(properties.getProperty("PUBLISH_TOKEN"))
    }
}