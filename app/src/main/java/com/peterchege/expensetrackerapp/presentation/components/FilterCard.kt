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

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.Screens



@Composable
fun FilterCard(
    modifier: Modifier = Modifier,
    filterName:String,
    isActive:Boolean,
    onClick:(String) -> Unit

    ) {
    Box(
        modifier =Modifier.padding(6.dp).clickable {
            onClick(filterName)
        }
    ){
        if (isActive){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .padding(6.dp)

            ) {
                Text(
                    text = filterName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    style = TextStyle(color = MaterialTheme.colors.primary)
                )
            }
        }else{
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
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

}