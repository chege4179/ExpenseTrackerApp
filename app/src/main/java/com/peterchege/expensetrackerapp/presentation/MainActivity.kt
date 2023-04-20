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
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.presentation.navigation.AppNavigation
import com.peterchege.expensetrackerapp.presentation.theme.ExpenseTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val theme = viewModel.theme
                .collectAsStateWithLifecycle(
                    initialValue = Constants.DARK_MODE,
                    context = Dispatchers.Main.immediate
                )
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            val currentColor =  if (theme.value == Constants.DARK_MODE)
                MaterialTheme.colors.background
            else
                MaterialTheme.colors.surface
            LaunchedEffect(
                key1 = systemUiController,
                key2 = theme.value,
                key3 = useDarkIcons
            ) {
                println("Theme changed : ${theme.value}")

                systemUiController.setStatusBarColor(
                    color = currentColor,
                    darkIcons = useDarkIcons
                )
                systemUiController.setNavigationBarColor(
                    color = currentColor,
                    darkIcons = useDarkIcons
                )
            }
            ExpenseTrackerAppTheme(
                darkTheme = theme.value == Constants.DARK_MODE
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
}
