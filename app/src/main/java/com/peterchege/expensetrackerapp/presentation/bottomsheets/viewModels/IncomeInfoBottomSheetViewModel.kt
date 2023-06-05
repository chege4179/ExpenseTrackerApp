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