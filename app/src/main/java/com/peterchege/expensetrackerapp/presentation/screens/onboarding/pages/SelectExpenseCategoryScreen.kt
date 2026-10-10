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
package com.peterchege.expensetrackerapp.presentation.screens.onboarding.pages

import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peterchege.expensetrackerapp.core.util.sampleExpenseCategories
import com.peterchege.expensetrackerapp.presentation.components.ExampleExpenseCategoryCard
import com.peterchege.expensetrackerapp.core.util.ExampleExpenseCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectExpenseCategoryScreen(
    selectedExpenseCategories: List<ExampleExpenseCategory>,
    onAddCategory: (ExampleExpenseCategory) -> Unit,
    onRemoveCategory: (ExampleExpenseCategory) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Create Expense categories you'd like to record in the app",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "You can create custom categories that were not captured here",
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                sampleExpenseCategories.forEach { category ->
                    ExampleExpenseCategoryCard(
                        category = category,
                        isSelected = selectedExpenseCategories.map { it.name }.contains(category.name),
                        onAddCategory = onAddCategory,
                        onRemoveCategory = onRemoveCategory
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun SelectExpenseCategoryScreenPreview() {
    PreviewSurface {
        SelectExpenseCategoryScreen(
            selectedExpenseCategories = sampleExpenseCategories.take(2),
            onAddCategory = {},
            onRemoveCategory = {},
        )
    }
}
