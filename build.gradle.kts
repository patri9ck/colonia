plugins {
    id("java")
    id("io.freefair.lombok") version "6.5.0.3"
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("redis.clients:jedis:4.2.3")
    implementation("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.6")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
