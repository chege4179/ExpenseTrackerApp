/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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

import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.peterchege.expensetrackerapp.core.util.ExampleTransactionCategory

@Composable
fun ExampleTransactionCategoryCard(
    category: ExampleTransactionCategory,
    isSelected: Boolean,
    onAddCategory:(ExampleTransactionCategory) -> Unit,
    onRemoveCategory: (ExampleTransactionCategory) -> Unit,
) {
    InputChip(
        modifier = Modifier.padding(horizontal = 6.dp), // gap between items
        selected = isSelected,
        onClick = {
            if (isSelected){
                onRemoveCategory(category)
            }else{
                onAddCategory(category)
            }
        },
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = category.name,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                )
                Spacer(modifier = Modifier.width(5.dp))
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Category Selected",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

        }
    )

}

@ThemePreviews
@Composable
private fun ExampleTransactionCategoryCardPreview() {
    PreviewSurface {
        Row {
            ExampleTransactionCategoryCard(
                category = ExampleTransactionCategory("Transport"),
                isSelected = true,
                onAddCategory = {},
                onRemoveCategory = {},
            )
            ExampleTransactionCategoryCard(
                category = ExampleTransactionCategory("Internet"),
                isSelected = false,
                onAddCategory = {},
                onRemoveCategory = {},
            )
        }
    }
}
