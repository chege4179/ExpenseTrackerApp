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
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
}

android {
    namespace = "com.peterchege.expensetrackerapp"
    compileSdk= 33

    defaultConfig {
        applicationId= "com.peterchege.expensetrackerapp"
        minSdk =21
        targetSdk= 33
        versionCode= 1
        versionName= "1.0"

        testInstrumentationRunner ="com.peterchege.expensetrackerapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary =true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion= "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            exclude("META-INF/**")
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.compose.ui:ui:1.4.0-beta01")
    implementation ("androidx.compose.material:material:1.4.0-beta01")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.0-beta01")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.activity:activity-compose:1.6.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.4.0-beta01")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.4.0-beta01")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.4.0-beta01")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.foundation:foundation-layout:1.3.1")
    implementation ("androidx.navigation:navigation-compose:2.5.3")


    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-beta01")

    //paging
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.44.2")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // coil
    implementation ("io.coil-kt:coil-compose:2.2.2")

    // room
    implementation ("androidx.room:room-runtime:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-paging:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")

    // compose icons
    implementation ("androidx.compose.material:material-icons-extended:1.4.0-beta01")

    //pager
    implementation( "com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    // swipe refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.27.1")

    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //charts
    implementation ("com.github.tehras:charts:0.2.4-alpha")


    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.3.5")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.2.0")

    // date picker
    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.2")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation ("app.cash.turbine:turbine:0.7.0")
    testImplementation("io.mockk:mockk:1.13.4")

    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    // Instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.45")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.45")
    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("com.google.truth:truth:1.1.3")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:core-ktx:1.5.0")
    androidTestImplementation( "com.squareup.okhttp3:mockwebserver:4.9.3")
    androidTestImplementation( "io.mockk:mockk-android:1.13.4")
    androidTestImplementation ("androidx.test:runner:1.5.2")
}