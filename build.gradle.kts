import java.net.URI

plugins {
    java
    id("java-library")
    id("maven-publish")
    kotlin("jvm")
}

group = "org.moxqeon"
version = "1.1.0"

repositories {
    mavenLocal()
    maven {
        name = "Aliyun"
        url = URI("https://maven.aliyun.com/repository/public")
    }
    mavenCentral()
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "CodeMC"
        url = URI("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        name = "DMulloy"
        url = URI("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        name = "Minebench"
        url = URI("https://repo.minebench.de/")
    }
}

dependencies {
    compileOnly("org.moxqeon:MoxLib:1.1.0")
    compileOnly(fileTree(mapOf("dir" to "../../BukkitLib", "include" to "*.jar")))
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    compileOnly("com.github.retrooper:packetevents-bungeecord:2.13.0")
    compileOnly(kotlin("stdlib-jdk8"))
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        val props = mapOf("version" to version)
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
        filesMatching("bungee.yml") {
            expand(props)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications {
        create<MavenPublication>("bukkit") {
            from(components["java"])
        }
    }
}

kotlin {
    jvmToolchain(17)
}