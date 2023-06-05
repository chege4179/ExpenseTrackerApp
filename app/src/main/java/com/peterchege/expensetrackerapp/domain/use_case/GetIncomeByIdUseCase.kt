package com.peterchege.expensetrackerapp.domain.use_case

import com.peterchege.expensetrackerapp.core.room.entities.IncomeEntity
import com.peterchege.expensetrackerapp.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIncomeByIdUseCase @Inject constructor(
    private val repository: IncomeRepository
) {

    operator fun invoke(incomeId:String): Flow<IncomeEntity?> {
        return repository.getIncomeById(id = incomeId)
    }
}