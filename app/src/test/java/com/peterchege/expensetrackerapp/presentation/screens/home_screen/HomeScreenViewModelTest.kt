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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterchege.expensetrackerapp.MainDispatcherRule
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.GetAllIncomeUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import com.peterchege.expensetrackerapp.repository.FakeTransactionRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    val mockUseCase = mockk<GetFilteredTransactionsUseCase>()
    val mockUseCase2 = mockk<GetAllIncomeUseCase>()

    val transactions :List<TransactionEntity> = mockk()

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        every { mockUseCase.invoke(any()) } returns  flowOf(transactions)
        homeScreenViewModel = HomeScreenViewModel(
            getFilteredTransactionsUseCase = mockUseCase,
            getAllIncomeUseCase = mockUseCase2,
        )
        every { homeScreenViewModel.getTransactions(any()) } just Runs

    }



    @Test
    fun`should call use case with correct filter when index is changed`() = runTest {
        val index = 1
        every { mockUseCase(filter = FilterConstants.FilterList[index]) } returns flowOf(emptyList())

        homeScreenViewModel.onChangeSelectedIndex(index)

        verify { mockUseCase(filter = FilterConstants.FilterList[index]) }
    }

    @Test
    fun`When any of the min fabs button is clicked the enum state should update accordingly`() = runTest {
        homeScreenViewModel.onChangeActiveBottomSheet(BottomSheets.ADD_TRANSACTION_CATEGORY)

        assert(homeScreenViewModel._activeBottomSheet.value == BottomSheets.ADD_TRANSACTION_CATEGORY)

    }

}

