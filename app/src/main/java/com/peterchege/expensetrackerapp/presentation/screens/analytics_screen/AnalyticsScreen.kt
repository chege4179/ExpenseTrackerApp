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
package com.peterchege.expensetrackerapp.presentation.screens.analytics_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

import com.himanshoe.charty.bar.model.BarData
import com.peterchege.expensetrackerapp.domain.toExternalModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AnalyticsScreen(
    navController: NavController,
    viewModel: AnalyticsScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val DaysOfTheWeek = listOf<String>("Sun","Mon","Tue","Wed","Thur","Fri","Sat")
    val test = viewModel.graphData.value.map { data -> data.collectAsStateWithLifecycle(
        initialValue = emptyList()) }
        .map { state -> state.value.map { it.toExternalModel() } }


    val transactions = viewModel.graphData.value
        .map { data ->  data.collectAsStateWithLifecycle(initialValue = emptyList()) }
        .map { state -> state.value.map { it.toExternalModel() } }
        .map { graph ->  BarChartData.Bar(
            value = graph.map { it.transactionAmount.toFloat() }.sum(),
            label = DaysOfTheWeek[test.indexOf(graph)],
            color = MaterialTheme.colors.primary
        )
        }
    LaunchedEffect(key1 = transactions){
        viewModel.onChangeBarDataList(data = transactions)


    }
    val dummyData = listOf(
        BarData(xValue="Sun", yValue=1000.0f),
        BarData(xValue="Mon", yValue=550.0f),
        BarData(xValue="Tue", yValue=3600.0f),
        BarData(xValue="Wed", yValue=0.0f),
        BarData(xValue="Wed", yValue=0.0f),
        BarData(xValue="Wed", yValue=0.0f),
        BarData(xValue="Wed", yValue=0.0f)
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Analytics")
                }
            )
        },
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            ,
            horizontalAlignment = Alignment.Start,

        ){
            Column(
                modifier = Modifier.fillMaxWidth().height(80.dp),

            ) {
                Text(
                    text = "KES ${transactions.map{ it.value }.sum() }"
                )
                Text(
                    text = "Total spent this week",

                )

            }
            BarChart(
                barChartData = BarChartData(bars = viewModel.barDataList.value),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .padding(
                        bottom = 10.dp
                    ),
                animation = simpleChartAnimation(),
                barDrawer = SimpleBarDrawer(),
                xAxisDrawer = SimpleXAxisDrawer(
                    axisLineThickness = 2.dp
                ),
                yAxisDrawer = SimpleYAxisDrawer(
                    axisLineThickness = 2.dp
                ),
                labelDrawer = SimpleValueDrawer(),

            )
        }
    }

}