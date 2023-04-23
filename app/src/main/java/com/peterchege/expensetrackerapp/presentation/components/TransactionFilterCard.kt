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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionFilterCard(
    modifier: Modifier = Modifier,
    filterName:String,
    isActive:Boolean,
    onClick:(String) -> Unit

) {
    val currentBackgroundColor = if (isActive)
        MaterialTheme.colors.surface
    else
        MaterialTheme.colors.background
    Box(
        modifier = Modifier
            .padding(6.dp)
            .border(
                width = 1.dp,
                color = if (isActive)
                    MaterialTheme.colors.surface
                else
                    MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(5.dp),
            )
            .clickable {
            onClick(filterName)
        }
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = if (isActive)
                        MaterialTheme.colors.surface
                    else
                        MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(5.dp),
                )
                .background(color = currentBackgroundColor)
                .padding(6.dp)


        ) {
            Text(
                text = filterName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                style = TextStyle(color = MaterialTheme.colors.primary)
            )
        }
    }

}