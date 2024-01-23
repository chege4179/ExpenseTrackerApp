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
package com.peterchege.expensetrackerapp.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.BuildConfig
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.core.util.getAppVersionName
import com.peterchege.expensetrackerapp.presentation.alertDialogs.ThemeDialog
import com.peterchege.expensetrackerapp.presentation.components.SettingsRow

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    openOSSMenu: () -> Unit,
    navigateToAboutScreen:() -> Unit,

    ) {
    val theme by viewModel.theme.collectAsStateWithLifecycle()
    val uiState by viewModel.settingsScreenUiState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        theme = theme,
        uiState = uiState,
        updateTheme = { viewModel.updateTheme(it) },
        openOSSMenu = openOSSMenu,
        toggleThemeDialogVisibility = viewModel::toggleThemeDialogVisibility,
        navigateToAboutScreen = navigateToAboutScreen
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    theme: String,
    uiState: SettingsScreenUiState,
    navigateToAboutScreen:() -> Unit,
    toggleThemeDialogVisibility:() -> Unit,
    updateTheme: (String) -> Unit,
    openOSSMenu: () -> Unit,
) {

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = "Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                )
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            if (uiState.isThemeDialogVisible){
                ThemeDialog(
                    changeTheme = updateTheme,
                    toggleThemeDialog = toggleThemeDialogVisibility,
                    currentTheme = theme
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SettingsRow(
                        title = "About",
                        onClick = navigateToAboutScreen
                    )
                }
                item {
                    SettingsRow(
                        title = "Dark Theme",
                        onClick = toggleThemeDialogVisibility
                    )
                }
                item {
                    SettingsRow(
                        title = "Open Source Licenses",
                        onClick = openOSSMenu
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "App Version: ${getAppVersionName(context)}",
                    modifier = Modifier,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    fontSize = 11.sp
                )
                Text(
                    text = "Build Type: ${BuildConfig.BUILD_TYPE}",
                    modifier = Modifier,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    fontSize = 11.sp
                )
                Text(
                    text = "Made with ❤️ by Peter Chege 🇰🇪",
                    modifier = Modifier,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    fontSize = 12.sp
                )
            }
        }
    }

}