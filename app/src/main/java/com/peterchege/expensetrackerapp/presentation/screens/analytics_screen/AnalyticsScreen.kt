package com.peterchege.expensetrackerapp.presentation.screens.analytics_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.CustomChart

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AnalyticsScreen(
    navController: NavController,
    viewModel: AnalyticsScreenViewModel = hiltViewModel()
) {

    val transactions = viewModel.graphData.value
        .map { it.transactions.collectAsStateWithLifecycle(initialValue = emptyList()) }
        .map { it.value.map { it.toExternalModel() } }
        .map { GraphItem(transactions = it, total = it.map { it.transactionAmount }.sum()) }
    val maxTotal = transactions.maxBy { it.total }.total
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            CustomChart(
                barValue = transactions.map { (it.total/(maxTotal + 1)).toFloat() },
                xAxisScale = listOf("Sun", "Mon", "Tue", "Wed", "Thur","Fri","Sat"),
                total_amount = maxTotal
            )
        }

    }

}