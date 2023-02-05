package com.peterchege.expensetrackerapp.presentation.screens.add_transaction_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel:AddTransactionScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val transactionCategories = viewModel.transactionCategories
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(route = event.route)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text ="Create Transaction")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = viewModel.transactionName.value,
                    onValueChange = {
                        viewModel.onChangeTransactionName(text = it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Transaction Name")
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = viewModel.transactionAmount.value.toString(),
                    onValueChange = {
                        viewModel.onChangeTransactionAmount(text = it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Transaction Amount")
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                MenuSample(
                    menuWidth = 300,
                    selectedIndex = viewModel.selectedIndex.value,
                    menuItems = transactionCategories.map { it.transactionCategoryName },
                    onChangeSelectedIndex = {
                        viewModel.onChangeSelectedIndex(index = it)
                        val selectedTransactionCategory = transactionCategories[it]
                        viewModel.onChangeSelectedTransactionCategory(category = selectedTransactionCategory)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text =viewModel.transactionDate.value.toString(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = { dateDialogState.show() }
                    ) {
                        Text("Pick A date")
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text =viewModel.transactionTime.value.toString(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            timeDialogState.show()

                        }
                    ) {
                        Text("Pick A Time")
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        viewModel.addTransaction()
                    }
                ){
                    Text(text = "Save Transaction")

                }
            }
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Pick"){
                        dateDialogState.hide()

                    }
                    negativeButton(text = "Cancel"){

                    }
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                ){
                    viewModel.onChangeTransactionDate(date = it)

                }

            }
            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Pick"){
                        timeDialogState.hide()

                    }
                    negativeButton(text = "Cancel"){

                    }
                }
            ) {
                timepicker(
                    initialTime = LocalTime.now(),
                    title = "Pick a time",
                ){
                    viewModel.onChangeTransactionTime(time = it)

                }

            }
        }
    }
}