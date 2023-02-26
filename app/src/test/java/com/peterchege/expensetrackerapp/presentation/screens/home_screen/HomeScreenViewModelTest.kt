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

import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import com.peterchege.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import com.peterchege.expensetrackerapp.repository.FakeTransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class HomeScreenViewModelTest {

    private lateinit var homeScreenViewModel:HomeScreenViewModel;
    private lateinit var repository: TransactionRepository
    private lateinit var getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = FakeTransactionRepository()
        getFilteredTransactionsUseCase = GetFilteredTransactionsUseCase(repository = repository)
        homeScreenViewModel = HomeScreenViewModel(getFilteredTransactionsUseCase =getFilteredTransactionsUseCase)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Assert the default starting index is 0`(){
        assert(homeScreenViewModel.selectedIndex.value == 0)

    }
    @Test
    fun `Assert initial transactions are empty `(){
        assert(homeScreenViewModel.transactions.value.isEmpty())

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Load Transactions into state ` () = runTest{
        homeScreenViewModel.getTransactions(filter = FilterConstants.ALL)
        assert(homeScreenViewModel.transactions.value.isNotEmpty())
    }
}

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}