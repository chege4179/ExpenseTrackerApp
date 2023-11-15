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

val keyPasswordString: String = gradleLocalProperties(rootDir).getProperty("keyPassword")
plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("org.jetbrains.kotlinx.kover")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.peterchege.expensetrackerapp"
    compileSdk= 34

    defaultConfig {
        applicationId= "com.peterchege.expensetrackerapp"
        minSdk =21
        targetSdk= 34
        versionCode= 1
        versionName= "1.0"

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
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {

        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion= "1.5.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            exclude("META-INF/**")
        }
    }
    testOptions {
        managedDevices {
            devices {
                maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>(name = "pixel4api30").apply {
                    device = "Pixel 4"
                    apiLevel = 30
                    systemImageSource = "google"
                }
            }
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.compose.ui:ui:1.6.0-alpha08")
    implementation ("androidx.compose.material:material:1.6.0-alpha08")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0-alpha08")
    implementation ("androidx.activity:activity-compose:1.8.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0-alpha08")



    debugImplementation ("androidx.compose.ui:ui-tooling:1.6.0-alpha08")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.0-alpha08")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.compose.foundation:foundation-layout:1.5.4")
    implementation ("androidx.navigation:navigation-compose:2.7.5")


    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    //paging
    implementation("androidx.paging:paging-common-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:dagger-compiler:2.48.1") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.48.1")   // Hilt compiler
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")

    // coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    // room
    implementation ("androidx.room:room-runtime:2.6.0")
    ksp ("androidx.room:room-compiler:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-paging:2.6.0")


    // compose icons
    implementation ("androidx.compose.material:material-icons-extended:1.6.0-alpha08")

    //pager
    implementation( "com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.33.1-alpha")

    // swipe refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.28.0")

    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //charts
    implementation ("com.github.tehras:charts:0.2.4-alpha")


    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.5.1")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.5.0")

    // date picker
    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.4")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.8")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    // Instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.48.1")
    kspAndroidTest ("com.google.dagger:hilt-android-compiler:2.48.1")
    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("com.google.truth:truth:1.1.5")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:core-ktx:1.5.0")
    androidTestImplementation( "com.squareup.okhttp3:mockwebserver:4.11.0")
    androidTestImplementation( "io.mockk:mockk-android:1.13.8")
    androidTestImplementation ("androidx.test:runner:1.5.2")

    testImplementation ("org.robolectric:robolectric:4.10.3")
}






