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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HomeScreenCardActionsPreview(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeScreenActionsCard(
            name ="Add Transaction",
            icon = Icons.Default.Add,
            onClick = {

            }
        )
        HomeScreenActionsCard(
            name ="Add Transaction",
            icon = Icons.Default.Add,
            onClick = {

            }
        )
        HomeScreenActionsCard(
            name ="Add Transaction",
            icon = Icons.Default.Add,
            onClick = {

            }
        )
        HomeScreenActionsCard(
            name ="Add Transaction",
            icon = Icons.Default.Add,
            onClick = {

            }
        )
        HomeScreenActionsCard(
            name ="Add Transaction",
            icon = Icons.Default.Add,
            onClick = {

            }
        )
    }

}

@Composable
fun HomeScreenActionsCard(
    name: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .width(screenWidth / 5)
            .height(120.dp)
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.surface)
            ,
            onClick = { onClick() }) {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = icon,
                contentDescription = name
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,

            )
        )
    }

}