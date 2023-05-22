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
    id ("org.jetbrains.kotlinx.kover")


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
        getByName("debug") {

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
        kotlinCompilerExtensionVersion= "1.4.4"
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
    implementation ("androidx.core:core-ktx:1.10.0")
    implementation ("androidx.compose.ui:ui:1.5.0-alpha03")
    implementation ("androidx.compose.material:material:1.5.0-alpha03")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha03")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0-alpha03")



    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0-alpha03")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.0-alpha03")

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.foundation:foundation-layout:1.4.3")
    implementation ("androidx.navigation:navigation-compose:2.5.3")


    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    //paging
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha19")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-android-compiler:2.45")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // coil
    implementation ("io.coil-kt:coil-compose:2.3.0")

    // room
    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-ktx:2.5.1")
    implementation("androidx.room:room-paging:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")

    // compose icons
    implementation ("androidx.compose.material:material-icons-extended:1.5.0-alpha03")

    //pager
    implementation( "com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")

    // swipe refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.27.1")

    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //charts
    implementation ("com.github.tehras:charts:0.2.4-alpha")


    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.3.7")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.2.2")

    // date picker
    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation ("app.cash.turbine:turbine:0.12.3")
    testImplementation("io.mockk:mockk:1.13.5")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")
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

    testImplementation ("org.robolectric:robolectric:4.10.2")
}






apply(plugin = "org.jetbrains.kotlinx.kover")
kover {
    // disable()

    excludeJavaCode()

    useKoverTool()

    excludeInstrumentation {
        classes("com.example.subpackage.*")
    }


}



koverReport {
    // common filters for all reports of all variants
    filters {
        // exclusions for reports
        excludes {
            // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
//            classes("com.example.*")
//            // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
//            packages("com.another.subpackage")
            // excludes all classes and functions, annotated by specified annotations, wildcards '*' and '?' are available
            annotatedBy("*Generated*")
        }

        // inclusions for reports
        includes {
            // includes class by fully-qualified JVM class name, wildcards '*' and '?' are available
            classes("com.peterchege.expensetrackerapp.*")
            // includes all classes located in specified package and it subpackages
            packages("com.peterchege.expensetrackerapp")
        }
    }

    defaults {
        // add reports of 'release' Android build variant to default reports - generated by tasks `koverXmlReport`, `koverHtmlReport` etc
        mergeWith("debug")
    }

    // configure report for `release` build variant (Build Type + Flavor) - generated by tasks `koverXmlReportRelease`, `koverHtmlReportRelease` etc
    androidReports("debug") {



        // configure HTML report for `release` build variant (task `koverHtmlReportRelease`)
        html {
            // custom header in HTML reports, project path by default
            title = "Expense Tracker App Test Report"

            //  generate a HTML report when running the `check` task
            onCheck = false

            // directory for HTML report
            setReportDir(layout.buildDirectory.dir("kover/html-result"))

            // overriding filters only for the HTML report
            filters {
                // exclusions for HTML reports
                excludes {
                    // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.example.*")
                    // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
                    packages("com.another.subpackage")
                    // excludes all classes and functions, annotated by specified annotations, wildcards '*' and '?' are available
                    annotatedBy("*Generated*")
                }

                // inclusions for HTML reports
                includes {
                    // includes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.peterchege.expensetrackerapp.*")
                    // includes all classes located in specified package and it subpackages
                    packages("com.peterchege.expensetrackerapp")
                }
            }
        }

        // configure verification for `release` build variant (task `koverVerifyRelease`)
        verify {
            //  verify coverage when running the `check` task
            onCheck = true

            // add verification rule
            rule {
                // check this rule during verification
                isEnabled = true

                // specify the code unit for which coverage will be aggregated
                entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

                // overriding filters only for current rule
                filters {
                    excludes {
                        // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                        classes("com.example.*")
                        // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
                        packages("com.another.subpackage")
                        // excludes all classes and functions, annotated by specified annotations, wildcards '*' and '?' are available
                        annotatedBy("*Generated*")
                    }
                    includes {
                        // includes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                        classes("com.example.*")
                        // includes all classes located in specified package and it subpackages
                        packages("com.another.subpackage")
                    }
                }

                // specify verification bound for this rule
                bound {
                    // lower bound
                    minValue = 1

                    // upper bound
                    maxValue = 99

                    // specify which units to measure coverage for
                    metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE

                    // specify an aggregating function to obtain a single value that will be checked against the lower and upper boundaries
                    aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
                }

                // add lower bound for percentage of covered lines
                minBound(2)

                // add upper bound for percentage of covered lines
                maxBound(98)
            }
        }
    }
}