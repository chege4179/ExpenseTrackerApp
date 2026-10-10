/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
package com.peterchege.expensetrackerapp.presentation.screens.onboarding

import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.core.util.ExampleExpenseCategory
import com.peterchege.expensetrackerapp.core.util.ExampleTransactionCategory
import androidx.compose.animation.AnimatedContent
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    val selectedTransactionCategories by viewModel.selectedTransactionCategories.collectAsStateWithLifecycle()
    val selectedExpenseCategories by viewModel.selectedExpenseCategories.collectAsStateWithLifecycle()
    OnboardingScreenContent(
        pagerState = pagerState,
        selectedTransactionCategories = selectedTransactionCategories,
        selectedExpenseCategories = selectedExpenseCategories,
        onAddTransactionCategory = viewModel::addTransactionCategory,
        onRemoveTransactionCategory = viewModel::removeTransactionCategory,
        onAddExpenseCategory = viewModel::addExpenseCategory,
        onRemoveExpenseCategory = viewModel::removeExpenseCategory,
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
    selectedTransactionCategories: List<ExampleTransactionCategory>,
    selectedExpenseCategories: List<ExampleExpenseCategory>,
    onAddTransactionCategory: (ExampleTransactionCategory) -> Unit,
    onRemoveTransactionCategory: (ExampleTransactionCategory) -> Unit,
    onAddExpenseCategory: (ExampleExpenseCategory) -> Unit,
    onRemoveExpenseCategory: (ExampleExpenseCategory) -> Unit,
) {
    Column(
        modifier = Modifier
            .testTag("onboarding")
            .fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            AnimatedContent(targetState = page, label = "page"){ page ->
                when (page) {
                    0 -> {
                        WelcomeScreen()
                    }

                    1 -> {
                        SelectTransactionCategoryScreen(
                            selectedTransactionCategories = selectedTransactionCategories,
                            onAddCategory = onAddTransactionCategory,
                            onRemoveCategory = onRemoveTransactionCategory,
                        )
                    }

                    2 -> {
                        SelectExpenseCategoryScreen(
                            selectedExpenseCategories = selectedExpenseCategories,
                            onAddCategory = onAddExpenseCategory,
                            onRemoveCategory = onRemoveExpenseCategory,
                        )
                    }
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

@ThemePreviews
@Composable
private fun OnboardingScreenPreview() {
    PreviewSurface {
        OnboardingScreenContent(
            pagerState = rememberPagerState(pageCount = { 3 }),
            proceed = {},
            back = {},
            onClickPageIndicator = {},
            selectedTransactionCategories = emptyList(),
            selectedExpenseCategories = emptyList(),
            onAddTransactionCategory = {},
            onRemoveTransactionCategory = {},
            onAddExpenseCategory = {},
            onRemoveExpenseCategory = {},
        )
    }
}
