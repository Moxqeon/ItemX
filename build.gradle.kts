import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption

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
        name = "CodeMC"
        url = URI("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        name = "Minebench"
        url = URI("https://repo.minebench.de/")
    }
}

dependencies {
    compileOnly("org.moxqeon:MoxLib:1.1.0")
    compileOnly("com.google.guava:guava:33.2.0-jre")
    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly("io.papermc:paper-api:1.20.4")
    compileOnly("io.papermc:paper-core:1.20.4")
//    compileOnly(fileTree(mapOf("dir" to "../../BukkitLib", "include" to "*.jar")))
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
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
    jar{
        doLast {
            val jarFile = this@jar.archiveFile.get().asFile
            val dir = File("C:\\Users\\Kciyamat\\Desktop\\Purpur\\plugins")
            val dest = File(dir, "${jarFile.name}")
            dir.mkdirs()
            Files.copy(jarFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
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
    jvmToolchain(21)
}