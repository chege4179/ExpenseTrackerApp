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
package com.peterchege.expensetrackerapp.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.peterchege.expensetrackerapp.core.util.Constants

@Composable
fun ThemesDialog(
    onDismiss: () -> Unit,
    onSelectTheme: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Themes",
                style = TextStyle(color = MaterialTheme.colors.primary),

            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ThemeItem(
                    themeName = "Light Mode",
                    themeValue = Constants.LIGHT_MODE,
                    icon = Icons.Outlined.LightMode,
                    onSelectTheme = onSelectTheme
                )
                ThemeItem(
                    themeName = "Dark Mode",
                    themeValue =Constants.DARK_MODE,
                    icon = Icons.Outlined.DarkMode,
                    onSelectTheme = onSelectTheme
                )
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}




@Composable
fun ThemeItem(
    themeName: String,
    themeValue: String,
    icon: ImageVector,
    onSelectTheme: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.clickable{
            onSelectTheme(themeValue)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = themeName,
                style = TextStyle(color = MaterialTheme.colors.primary),
            )
        }
    }
}