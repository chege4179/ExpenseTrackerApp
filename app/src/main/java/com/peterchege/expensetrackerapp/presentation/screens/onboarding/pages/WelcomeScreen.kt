package com.peterchege.expensetrackerapp.presentation.screens.onboarding.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.presentation.components.OnBoardingCard

@Composable
fun WelcomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(15.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to My Expense Tracker App",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 42.sp,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    textAlign = TextAlign.Start,

                    )
                OnBoardingCard(
                    header = "The perfect expense tracker app",
                    description = "Expense Tracker App is an app that allows you to track your income , daily transactions and expenses ",
                    image = R.drawable.heart
                )
                OnBoardingCard(
                    header = "Search Transactions ",
                    description = "You can search transactions from a certain period ",
                    image = R.drawable.search
                )
                OnBoardingCard(
                    header = "Visualize Expenditure",
                    description = "Expense Tracker App visualizes your data in bar and pie charts for your insights",
                    image = R.drawable.analytics
                )
            }
        }


    }

}