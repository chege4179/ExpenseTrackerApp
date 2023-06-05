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