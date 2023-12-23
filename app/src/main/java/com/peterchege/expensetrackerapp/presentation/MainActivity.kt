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
package com.peterchege.expensetrackerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.presentation.navigation.AppNavigation
import com.peterchege.expensetrackerapp.presentation.theme.ExpenseTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var lazyStats: dagger.Lazy<JankStats>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {

            val viewModel: MainViewModel = hiltViewModel()
            val theme = viewModel.theme
                .collectAsStateWithLifecycle(
                    initialValue = Constants.DARK_MODE,
                    context = Dispatchers.Main.immediate
                )
            val isInDarkMode = theme.value == Constants.DARK_MODE

//            DisposableEffect(key1 = isInDarkMode) {
//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.auto(
//                        android.graphics.Color.TRANSPARENT,
//                        android.graphics.Color.TRANSPARENT,
//                    ) { isInDarkMode },
//                    navigationBarStyle = SystemBarStyle.auto(
//                        lightScrim,
//                        darkScrim,
//                    ) { isInDarkMode },
//                )
//                onDispose {}
//            }
            ExpenseTrackerAppTheme(
                darkTheme = isInDarkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    AppNavigation(navHostController = navHostController)

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lazyStats.get().isTrackingEnabled = true

    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }
}


private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)