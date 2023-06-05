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
package com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.DeleteIncomeByIdUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetIncomeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IncomeInfoBottomSheetViewModel @Inject constructor(
    private val getIncomeByIdUseCase: GetIncomeByIdUseCase,
    private val deleteIncomeByIdUseCase: DeleteIncomeByIdUseCase,
) : ViewModel(){

    private val _income = MutableStateFlow<Income?>(null)
    val income = _income.asStateFlow()


    fun getIncomeById(incomeId:String){
        viewModelScope.launch {
            val income = getIncomeByIdUseCase(incomeId = incomeId)
            income.collectLatest {
                it?.let {
                    _income.value = it.toExternalModel()
                }
            }
        }
    }

    fun deleteIncome(incomeId: String){
        viewModelScope.launch {
            deleteIncomeByIdUseCase(incomeId = incomeId)
        }

    }

}