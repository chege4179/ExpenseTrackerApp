package com.peterchege.expensetrackerapp.presentation.screens.add_edit_transaction_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditTransactionScreen(
    navController: NavController
) {
    Scaffold(
        modifier= Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(text = "Add edit TRANSACTION screen")

        }
    }


}