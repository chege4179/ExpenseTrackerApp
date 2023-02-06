package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.peterchege.expensetrackerapp.R
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.Mode
import com.peterchege.expensetrackerapp.core.util.Screens
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
)

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel.selectedIndex.value){
        viewModel.getTransactions(filter = FilterConstants.FilterList[viewModel.selectedIndex.value])

    }
    val transactions = viewModel.transactions.value

    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.COLLAPSED)
    }
    val items = listOf(
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Expense",
            onClick = {
                navController.navigate(Screens.ADD_EXPENSE_SCREEN)

            }
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Transaction",
            onClick = {
                navController.navigate(Screens.ADD_TRANSACTION_SCREEN)
            }
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Expense Category",
            onClick = {
                navController.navigate(Screens.ADD_EXPENSE_CATEGORY_SCREEN)
            }
        ),
        MinFabItem(
            icon = Icons.Default.Add,
            label = "Create Transaction Category",
            onClick = {
                navController.navigate(Screens.ADD_TRANSACTION_CATEGORY_SCREEN)
            }
        ),
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Expense Tracker App")
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
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(5),
                        elevation = 3.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,

                                ) {
                                Text(
                                    text = "Total spending",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "KES ${transactions.sumOf { it.transactionAmount }} /=",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                MenuSample(
                                    menuWidth = 150,
                                    selectedIndex = 0,
                                    menuItems = FilterConstants.FilterList,
                                    onChangeSelectedIndex = {
                                        viewModel.onChangeSelectedIndex(index = it)

                                    }
                                )
                            }
                        }
                    }
                }
                items(items = transactions) {
                    TransactionCard(
                        transaction = it,
                        onTransactionNavigate = {

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
                    fabScale = fabScale.value


                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        FloatingActionButton(
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
) {

    Row() {
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
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)

            )

        }

        Spacer(modifier = Modifier.size(16.dp))
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .clip(CircleShape)
                .width(32.dp)
                .height(32.dp),

            ) {
            IconButton(onClick = {
                onMinFabItemClick(item)
            }) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
//        Canvas(
//            modifier = Modifier
//                .size(32.dp)
//                .clickable(
//                    interactionSource = MutableInteractionSource(),
//                    indication = rememberRipple(
//                        bounded = false,
//                        radius = 20.dp,
//                        color = MaterialTheme.colors.onSurface
//                    ),
//                    onClick = {
//                        onMinFabItemClick.invoke(item)
//                    }
//                ),
//        ) {
//            drawCircle(
//                color = shadow,
//                radius = fabScale,
//                center = Offset(
//                    x = center.x + 2f,
//                    y = center.y + 2f,
//                )
//
//            )
//            drawCircle(
//                color = shadow,
//                radius = fabScale,
//
//                )
//            drawImage(
//                image = item.icon,
//                topLeft = Offset(
//                    x = center.x - (item.icon.width / 2),
//                    y = center.y - (item.icon.width / 2),
//                ),
//                alpha = alpha
//            )
//        }
    }


}


