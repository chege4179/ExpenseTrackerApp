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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsRow(
    title: String,
    checked :Boolean,
    onCheckedChange:(Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .clip(shape = RoundedCornerShape(13.dp))
            ,
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Switch(
                colors = SwitchColors(
                    checkedTrackColor = MaterialTheme.colorScheme.background,
                    checkedBorderColor = MaterialTheme.colorScheme.onBackground,
                    checkedIconColor = MaterialTheme.colorScheme.onBackground,
                    checkedThumbColor = MaterialTheme.colorScheme.background,

                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedIconColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onBackground,


                    disabledCheckedBorderColor = MaterialTheme.colorScheme.background,
                    disabledUncheckedBorderColor = MaterialTheme.colorScheme.onBackground,
                    disabledCheckedIconColor = MaterialTheme.colorScheme.onBackground,
                    disabledCheckedThumbColor = MaterialTheme.colorScheme.background,

                    disabledCheckedTrackColor = MaterialTheme.colorScheme.primary,
                    disabledUncheckedIconColor = MaterialTheme.colorScheme.onPrimary,
                    disabledUncheckedThumbColor = MaterialTheme.colorScheme.primary,
                    disabledUncheckedTrackColor = MaterialTheme.colorScheme.onBackground,

                ),
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },


            )

        }
    }
}
