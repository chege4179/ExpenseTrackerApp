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

import com.peterchege.expensetrackerapp.core.util.Resource
import com.peterchege.expensetrackerapp.core.util.Response
import com.peterchege.expensetrackerapp.domain.models.Expense
import com.peterchege.expensetrackerapp.domain.models.ExpenseCategory
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreateExpenseCategoryUseCase @Inject constructor(
    private val repository: ExpenseCategoryRepository
){

    suspend operator fun invoke(expenseCategory: ExpenseCategory): Flow<Resource<Response>> = flow {
        try {
            emit(value = Resource.Loading())
            val existingExpenseCategory = repository.getExpenseCategoryByName(name = expenseCategory.expenseCategoryName)
            if (existingExpenseCategory == null){
                repository.saveExpenseCategory(expenseCategory = expenseCategory)
                emit(value = Resource.Success(Response(msg = "Expense Category Added")))
            }else{
                emit(value = Resource.Error(message = "An expense category with a similar name already exists"))
            }
        }catch (e:IOException){
            emit(value = Resource.Error(message = e.localizedMessage?: "An unexpected error occurred"))
        }
    }
}