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
package com.peterchege.expensetrackerapp.presentation.screens.settings_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.core.util.getAppVersionName
import com.peterchege.expensetrackerapp.presentation.components.SettingsRow

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel:SettingsScreenViewModel = hiltViewModel()

){
    val theme = viewModel.theme.collectAsStateWithLifecycle()
    SettingsScreenContent(
        theme = theme.value,
        updateTheme = { viewModel.updateTheme(it) }
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreenContent(

    theme:String,
    updateTheme:(String) -> Unit,

) {

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = "Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            )
        },
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            LazyColumn(

                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SettingsRow(
                        title = "Dark Theme",
                        checked = theme == Constants.DARK_MODE,
                        onCheckedChange = {
                            if (it){
                                updateTheme(Constants.DARK_MODE)

                            }else{
                                updateTheme(Constants.LIGHT_MODE)
                            }

                        }
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
                    style = TextStyle(color = MaterialTheme.colors.primary),
                    fontSize = 11.sp
                )

                Text(
                    text = "Made with ❤️by Peter Chege 🇰🇪",
                    modifier = Modifier,
                    style = TextStyle(color = MaterialTheme.colors.primary),
                    fontSize = 12.sp
                )
            }
        }
    }

}