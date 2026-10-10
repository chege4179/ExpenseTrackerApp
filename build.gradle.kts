/*
 * Copyright 2023 Expense Tracker App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("com.android.application") version "9.4.1" apply false
    id("com.android.library") version "9.4.1" apply false
    kotlin("android") version "2.4.21" apply false
    kotlin("plugin.serialization") version "2.4.21" apply false
    kotlin("plugin.parcelize") version "2.4.21" apply false
    kotlin("plugin.compose") version "2.4.21" apply false
    id("com.diffplug.spotless") version "8.10.4"
    id("org.jetbrains.kotlinx.kover") version "0.9.11"
    id("com.google.devtools.ksp") version "2.3.12" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("com.google.gms.google-services") version "4.5.0" apply false
    id("com.google.firebase.crashlytics") version "3.0.8" apply false
    id("com.google.firebase.firebase-perf") version "2.0.2" apply false
    id("com.android.test") version "9.4.1" apply false
    id("androidx.baselineprofile") version "1.5.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}
buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.google.android.gms:oss-licenses-plugin:0.13.0")
    }
}

apply(plugin = "com.diffplug.spotless")
spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt", "**/generated/**")
        licenseHeaderFile(
            rootProject.file("${project.rootDir}/spotless/LICENSE.txt"),
            "^(package|object|import|interface)"
        )
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/LICENSE.txt"), "(^(?![\\/ ]\\*).*$)")
    }
}
subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config.setFrom("${project.rootDir}/detekt.yml")
        parallel = true
        buildUponDefaultConfig = true
        baseline = file("${project.projectDir}/detekt-baseline.xml")
    }



}
