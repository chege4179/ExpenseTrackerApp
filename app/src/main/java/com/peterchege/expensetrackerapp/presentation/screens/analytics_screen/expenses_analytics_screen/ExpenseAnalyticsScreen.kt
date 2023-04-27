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
package com.peterchege.expensetrackerapp.presentation.screens.analytics_screen.expenses_analytics_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.peterchege.expensetrackerapp.core.util.generateRandomColor
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.screens.analytics_screen.Tabs
import kotlinx.coroutines.flow.map

data class PieDataInfo(
    val slice :PieChartData.Slice,
    val expenseCategory:ExpenseCategory,
)
@Composable
fun ExpensesAnalyticsScreen(
    navController: NavController,
    viewModel: ExpenseAnalyticsScreenViewModel = hiltViewModel()
){

    val expensesCategoriesInfo = viewModel.expenseCategories.value
    val pieData = expensesCategoriesInfo.map { expenseCategoryInfoEntity ->
        val total = expenseCategoryInfoEntity.expenses
            .collectAsStateWithLifecycle(initialValue = emptyList())
            .value
            .map { it.toExternalModel() }
            .map { it.expenseAmount.toFloat() }
            .sum()
        PieDataInfo(
            slice = PieChartData.Slice(
                value = total,
                color = generateRandomColor()
            ),
            expenseCategory = expenseCategoryInfoEntity.expenseCategory
        )
    }



    ExpensesAnalyticsScreenContent(
        pieData = pieData
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpensesAnalyticsScreenContent(
    pieData:List<PieDataInfo>

) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    PieChart(
                        pieChartData = PieChartData(slices = pieData.map { it.slice }),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(250.dp)
                            .padding(10.dp),
                        animation = simpleChartAnimation(),
                        sliceDrawer = SimpleSliceDrawer(),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        pieData.map { 
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Box(
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(20.dp)
                                        .background(color = it.slice.color)
                                        .padding(3.dp)
                                )
                                Text(
                                    text = it.expenseCategory.expenseCategoryName,
                                    modifier = Modifier.padding(3.dp),
                                    fontSize = 17.sp,
                                    style = TextStyle(color = MaterialTheme.colors.primary)
                                    
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }

                    }
                }

            }
        }
    }
}