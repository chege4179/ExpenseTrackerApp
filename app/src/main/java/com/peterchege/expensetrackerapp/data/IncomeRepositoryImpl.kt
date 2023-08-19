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
package com.peterchege.expensetrackerapp.data

import com.peterchege.expensetrackerapp.core.di.IoDispatcher
import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.IncomeEntity
import com.peterchege.expensetrackerapp.domain.repository.IncomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val db:ExpenseTrackerAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):IncomeRepository {

    override suspend fun insertIncome(incomeEntity: IncomeEntity) {
        withContext(ioDispatcher){
            db.incomeEntityDao.insertIncome(incomeEntity = incomeEntity)
        }
    }

    override  fun getAllIncome(): Flow<List<IncomeEntity>> {
        return db.incomeEntityDao.getAllIncome().flowOn(ioDispatcher)
    }

    override fun getIncomeById(id:String): Flow<IncomeEntity?> {
        return db.incomeEntityDao.getIncomeById(id = id).flowOn(ioDispatcher)
    }

    override suspend fun deleteIncomeById(id: String) {
        withContext(ioDispatcher){
            db.incomeEntityDao.deleteIncomeById(id = id)
        }
    }

    override suspend fun deleteAllIncome() {
        withContext(ioDispatcher){
            db.incomeEntityDao.deleteAllIncome()
        }
    }
}