package com.peterchege.expensetrackerapp.presentation.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.presentation.components.PagerIndicator
import com.peterchege.expensetrackerapp.presentation.screens.onboarding.pages.SelectExpenseCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.onboarding.pages.SelectTransactionCategoryScreen
import com.peterchege.expensetrackerapp.presentation.screens.onboarding.pages.WelcomeScreen
import kotlinx.coroutines.launch
import timber.log.Timber
import com.peterchege.expensetrackerapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingScreenViewModel = hiltViewModel(),
    navigateHome: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    OnboardingScreenContent(
        viewModel = viewModel,
        pagerState = pagerState,
        proceed = {
            coroutineScope.launch {
                when (pagerState.currentPage) {
                    0 -> {
                        pagerState.scrollToPage(1)
                    }

                    1 -> {
                        pagerState.scrollToPage(2)
                    }

                    2 -> {
                        viewModel.finishOnboarding(navigateHome)

                    }
                }
            }

        },
        back = {
            coroutineScope.launch {
                when (pagerState.currentPage) {
                    0 -> {
                    }

                    1 -> {
                        pagerState.scrollToPage(0)
                    }

                    2 -> {
                        pagerState.scrollToPage(1)

                    }
                }
            }
        },
        onClickPageIndicator = {
            coroutineScope.launch {
                pagerState.scrollToPage(it)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreenContent(
    pagerState: PagerState,
    proceed: () -> Unit,
    back: () -> Unit,
    onClickPageIndicator: (Int) -> Unit,
    viewModel: OnboardingScreenViewModel,
) {
    Column(
        modifier = Modifier
            .testTag("onboarding")
            .fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    WelcomeScreen()
                }

                1 -> {
                    SelectTransactionCategoryScreen(viewModel = viewModel)
                }

                2 -> {
                    SelectExpenseCategoryScreen(viewModel = viewModel)
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = pagerState.currentPage != 0
            ) {
                Button(
                    modifier = Modifier.width(180.dp),
                    onClick = { back() },
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.back),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }

            Button(
                modifier = Modifier
                    .width(180.dp)
                    .testTag("next"),
                onClick = { proceed() },
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Text(
                    text = if (pagerState.currentPage == 2) 
                        stringResource(id = R.string.finish) 
                    else 
                        stringResource(id = R.string.next),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            PagerIndicator(
                indicatorCount = 3,
                pagerState = pagerState,
                onClick = {
                    onClickPageIndicator(it)
                }
            )
        }
    }

}