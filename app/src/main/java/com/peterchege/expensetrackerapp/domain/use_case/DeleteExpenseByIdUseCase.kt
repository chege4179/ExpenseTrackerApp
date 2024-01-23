package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseByIdUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
){
    suspend operator fun invoke(expenseId:String){
        expenseRepository.deleteExpenseById(expenseId)
    }
}