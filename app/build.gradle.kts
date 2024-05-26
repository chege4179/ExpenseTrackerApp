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
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.Packaging

val keyPasswordString: String = gradleLocalProperties(rootDir).getProperty("keyPassword")
plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("plugin.parcelize")
    kotlin("plugin.compose")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("org.jetbrains.kotlinx.kover")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id("com.google.firebase.firebase-perf")
    id("com.google.android.gms.oss-licenses-plugin")
    id("androidx.baselineprofile")

}

android {
    namespace = "com.peterchege.expensetrackerapp"
    compileSdk= 34

    defaultConfig {
        applicationId= "com.peterchege.expensetrackerapp"
        minSdk =21
        targetSdk= 34
        versionCode = 4
        versionName= "4.0"

        testInstrumentationRunner ="com.peterchege.expensetrackerapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary =true
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("expense_tracker_app.jks")
            keyAlias = "expense_tracker_app"
            keyPassword = keyPasswordString
            storePassword =  keyPasswordString
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            baselineProfile.automaticGenerationDuringBuild = true
        }
        getByName("debug") {
            isDebuggable = true
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isMinifyEnabled = true
            proguardFiles("baseline-profiles-rule.pro")
        }
    }
    experimentalProperties["android.experimental.self-instrumenting"] = true
    ksp {
        arg(k ="room.schemaLocation", v= "$projectDir/schemas")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
androidComponents {
    onVariants(selector().withBuildType("release")) {
        // Exclude AndroidX version files
        it.packaging.resources.excludes.add("META-INF/*.version")
    }
}
baselineProfile {
    saveInSrc = true
    automaticGenerationDuringBuild = false
}

composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}
dependencies {
    baselineProfile(project(":benchmark"))

    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.compose.ui:ui:1.6.7")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.7")
    implementation ("androidx.activity:activity-compose:1.9.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.7")

    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")


    implementation("androidx.metrics:metrics-performance:1.0.0-beta01")

    debugImplementation ("androidx.compose.ui:ui-tooling:1.6.7")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.7")
    implementation ("androidx.compose.material:material-icons-extended:1.6.7")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.foundation:foundation:1.6.7")
    implementation("androidx.compose.foundation:foundation-layout:1.6.7")
    implementation ("androidx.navigation:navigation-compose:2.7.7")


    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    //paging
    implementation("androidx.paging:paging-common-ktx:3.3.0")
    implementation("androidx.paging:paging-compose:3.3.0")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.8.0")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:dagger-compiler:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // coil
    implementation ("io.coil-kt:coil-compose:2.6.0")

    implementation ("com.google.android.play:review-ktx:2.0.1")
    implementation ("com.google.android.play:app-update-ktx:2.1.0")

    // room
    implementation ("androidx.room:room-runtime:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    implementation("androidx.core:core-splashscreen:1.0.1")
    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //charts
    implementation ("com.github.tehras:charts:0.2.4-alpha")

    implementation("androidx.profileinstaller:profileinstaller:1.3.1")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation ("com.google.firebase:firebase-crashlytics")
    implementation ("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-perf")

    // Play services OSS
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.1")

    // date picker
    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.4")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.8")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    // Instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.51")
    kspAndroidTest ("com.google.dagger:hilt-android-compiler:2.50")
    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("com.google.truth:truth:1.4.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:core-ktx:1.5.0")
    androidTestImplementation( "com.squareup.okhttp3:mockwebserver:4.12.0")
    androidTestImplementation( "io.mockk:mockk-android:1.13.9")
    androidTestImplementation ("androidx.test:runner:1.5.2")

    testImplementation ("org.robolectric:robolectric:4.11.1")
}






