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
package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.presentation.components.MenuSample
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard

enum class MultiFloatingState {
    EXPANDED,
    COLLAPSED
}

data class MinFabItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit,
    val testTag: String,
)

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel.selectedIndex.value) {
        viewModel.getTransactions(filter = FilterConstants.FilterList[viewModel.selectedIndex.value])

    }
    val transactions = viewModel.transactions
        .value
        .collectAsStateWithLifecycle(initialValue = emptyList())
        .value
        .map { it.toExternalModel() }

    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.COLLAPSED)
    }
    val items = listOf(
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Expense",
            onClick = {
                navController.navigate(Screens.ADD_EXPENSE_SCREEN)

            },
            testTag = ""
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Transaction",
            onClick = {
                navController.navigate(Screens.ADD_TRANSACTION_SCREEN)
            },
            testTag = TestTags.CREATE_TRANSACTION_BUTTON
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Expense Category",
            onClick = {
                navController.navigate(Screens.ADD_EXPENSE_CATEGORY_SCREEN)
            },
            testTag = ""
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Transaction Category",
            onClick = {
                navController.navigate(Screens.ADD_TRANSACTION_CATEGORY_SCREEN)
            },
            testTag = TestTags.CREATE_TRANSACTION_CATEGORY_BUTTON
        ),
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    Text(
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = "My Expense Tracker App",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            )
        },
        floatingActionButton = {
            MultiFloatingButton(
                multiFloatingState = multiFloatingState,
                items = items,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
            )

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground
                        ),
                        onClick = {
                            throw RuntimeException("Test crash")

                        }
                    ) {
                        Text(text = "test crash")
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(5),
                        elevation = 3.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.onBackground),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,

                                ) {
                                Text(
                                    text = "Total spending",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    style = TextStyle(color = MaterialTheme.colors.primary)
                                )
                                Text(
                                    text = "KES ${transactions.sumOf { it.transactionAmount }} /=",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                    style = TextStyle(color = MaterialTheme.colors.primary)
                                )
                                MenuSample(
                                    menuWidth = 170,
                                    selectedIndex = viewModel.selectedIndex.value,
                                    menuItems = FilterConstants.FilterList,
                                    onChangeSelectedIndex = {
                                        viewModel.onChangeSelectedIndex(index = it)

                                    }
                                )
                            }
                        }
                    }
                }
                items(items = transactions) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onTransactionNavigate = {
                            navController.navigate(Screens.TRANSACTIONS_SCREEN + "/$it")

                        }
                    )
                }

            }


        }
    }

}

@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    items: List<MinFabItem>
) {
    val transition = updateTransition(
        targetState = multiFloatingState,
        label = "transition"
    )
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.EXPANDED) 315f else 0f
    }
    val fabScale = transition.animateFloat(label = "fabscale") {
        if (it == MultiFloatingState.EXPANDED) 36f else 0f
    }
    val alpha = transition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }
    ) {
        if (it == MultiFloatingState.EXPANDED) 1f else 0f
    }
    val textshadow = transition.animateDp(
        label = "text shaow",
        transitionSpec = { tween(durationMillis = 50) }
    ) {
        if (it == MultiFloatingState.EXPANDED) 2.dp else 0.dp
    }

    Column(
        horizontalAlignment = Alignment.End,
    ) {
        if (transition.currentState == MultiFloatingState.EXPANDED) {
            items.forEach {
                MinFab(
                    item = it,
                    onMinFabItemClick = {
                        it.onClick()
                    },
                    alpha = alpha.value,
                    textShadow = textshadow.value,
                    fabScale = fabScale.value,
                    testTag = it.testTag


                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        FloatingActionButton(
            modifier = Modifier.testTag(TestTags.FLOATING_ACTION_BUTTON),
            backgroundColor = MaterialTheme.colors.background,
            onClick = {
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.EXPANDED) {
                        MultiFloatingState.COLLAPSED
                    } else {
                        MultiFloatingState.EXPANDED
                    }
                )
            }

        ) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.rotate(rotate),
                tint = MaterialTheme.colors.primary,
                contentDescription = null
            )
        }
    }

}


@Composable
fun MinFab(
    item: MinFabItem,
    onMinFabItemClick: (MinFabItem) -> Unit,
    alpha: Float,
    fabScale: Float,
    textShadow: Dp,
    showLabel: Boolean = true,
    testTag: String,
) {

    Row(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        if (showLabel) {
            Text(
                text = item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(
                        animateFloatAsState(
                            targetValue = alpha,
                            animationSpec = tween(durationMillis = 30)
                        ).value
                    )
                    .shadow(textShadow)
                    .background(MaterialTheme.colors.primary)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)

            )

        }

        Spacer(modifier = Modifier.size(16.dp))
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.onBackground)
                .clip(CircleShape)
                .width(32.dp)
                .height(32.dp),

            ) {
            IconButton(
                modifier = Modifier.testTag(tag = testTag),
                onClick = {
                    onMinFabItemClick(item)
                }) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }


}


