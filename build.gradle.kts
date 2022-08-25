/*
 * Copyright (C) 2022 Patrick Zwick and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
