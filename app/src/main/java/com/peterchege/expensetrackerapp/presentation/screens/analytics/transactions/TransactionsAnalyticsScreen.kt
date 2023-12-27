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
package com.peterchege.expensetrackerapp.presentation.screens.analytics.transactions

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.getActualDayOfWeek
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.GraphFilterCard
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.peterchege.expensetrackerapp.presentation.theme.GreyColor

@Composable
fun TransactionsAnalyticsScreen(
    viewModel: AnalyticsScreenViewModel = hiltViewModel()
){
    val transactionsState = viewModel.graphData.value
        .map { data -> data.collectAsStateWithLifecycle(initialValue = emptyList()) }
        .map { state -> state.value.map { it.toExternalModel() } }

    val transactions = transactionsState.map { a ->
        val total = a.map { it.transactionAmount.toFloat() }.sum()
        if (a.isNotEmpty()) {
            BarChartData.Bar(
                value = total,
                label = if (transactionsState.size == 7) {
                    getActualDayOfWeek(a[0].transactionCreatedOn).substring(0, 3)
                } else {
                    (transactionsState.indexOf(a) + 1).toString()
                },
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            BarChartData.Bar(
                value = total,
                label = "",
                color = MaterialTheme.colorScheme.primary
            )
        }

    }

    LaunchedEffect(key1 = transactions) {
        viewModel.onChangeBarDataList(data = transactions)


    }

    TransactionsAnalyticsScreenContent(
        transactionsState = transactionsState.flatten(),
        transactions = transactionsState.flatten(),
        activeFilterConstant = viewModel.activeFilterConstant.value,
        bars = transactions,
        onChangeActiveFilterConstant = { viewModel.onChangeActiveFilterConstant(it) }
    )





}

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionsAnalyticsScreenContent(
    transactionsState:List<Transaction>,
    transactions:List<Transaction>,
    activeFilterConstant:String,
    bars:List<BarChartData.Bar>,
    onChangeActiveFilterConstant:(String) -> Unit,


) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),

                    ) {
                    Text(
                        text = "KES ${transactions.sumOf { it.transactionAmount }} /=",
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                    when (activeFilterConstant) {
                        FilterConstants.THIS_WEEK ->
                            Text(
                                text = "Total spent this week",
                                style = TextStyle(color = GreyColor),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )

                        FilterConstants.LAST_7_DAYS ->
                            Text(
                                text = "Total spent in the last 7 days",
                                style = TextStyle(color = GreyColor),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )

                        FilterConstants.THIS_MONTH ->
                            Text(
                                text = "Total spent this month",
                                style = TextStyle(color = GreyColor),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )

                        FilterConstants.ALL ->
                            Text(
                                text = "Total spending",
                                style = TextStyle(color = GreyColor),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                    }


                }
            }
            item {
                AnimatedVisibility(
                    visible = activeFilterConstant != FilterConstants.ALL,
                    enter = slideInHorizontally() + fadeIn()
                ) {
                    BarChart(
                        barChartData = BarChartData(bars = bars),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(10.dp),
                        animation = simpleChartAnimation(),
                        barDrawer = SimpleBarDrawer(),
                        xAxisDrawer = SimpleXAxisDrawer(
                            axisLineThickness = 1.dp,
                            axisLineColor = MaterialTheme.colorScheme.primary
                        ),
                        yAxisDrawer = SimpleYAxisDrawer(
                            axisLineThickness = 1.dp,
                            axisLineColor = MaterialTheme.colorScheme.primary

                        ),
                        labelDrawer = SimpleValueDrawer(
                            drawLocation = SimpleValueDrawer.DrawLocation.XAxis,
                            labelTextColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GraphFilterCard(
                        filterName = FilterConstants.THIS_WEEK,
                        isActive = activeFilterConstant == FilterConstants.THIS_WEEK,
                        onClick = {
                            onChangeActiveFilterConstant(it)
                        }
                    )
                    GraphFilterCard(
                        filterName = FilterConstants.LAST_7_DAYS,
                        isActive = activeFilterConstant == FilterConstants.LAST_7_DAYS,
                        onClick = {
                            onChangeActiveFilterConstant(it)
                        }
                    )
                    GraphFilterCard(
                        filterName = FilterConstants.THIS_MONTH,
                        isActive = activeFilterConstant == FilterConstants.THIS_MONTH,
                        onClick = {
                            onChangeActiveFilterConstant(it)
                        }
                    )
                    GraphFilterCard(
                        filterName = FilterConstants.ALL,
                        isActive = activeFilterConstant == FilterConstants.ALL,
                        onClick = {
                            onChangeActiveFilterConstant(it)
                        }
                    )
                }
            }

            items(items = transactionsState.reversed()) { transaction ->
                TransactionCard(transaction = transaction,
                    onTransactionNavigate = {

                    })
            }

        }
    }

}

