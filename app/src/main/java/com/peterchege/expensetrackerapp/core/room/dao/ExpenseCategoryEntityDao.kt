package com.peterchege.expensetrackerapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.expensetrackerapp.core.room.entities.ExpenseCategoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpenseCategoryEntityDao {

    @Query("SELECT * FROM ExpenseCategory")
    fun getExpenseCategories(): Flow<List<ExpenseCategoryEntity>>

    @Query("SELECT * FROM ExpenseCategory WHERE expenseCategoryName =:name")
    suspend fun getExpenseCategoryByName(name:String):ExpenseCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseCategory(expenseCategoryEntity: ExpenseCategoryEntity)


    @Query("DELETE FROM ExpenseCategory WHERE expenseCategoryId = :id")
    suspend fun deleteExpenseCategoryById(id: String)

    @Query("DELETE FROM ExpenseCategory")
    suspend fun deleteAllExpenseCategories()

    @Query("UPDATE ExpenseCategory SET expenseCategoryName = :name WHERE expenseCategoryId = :id")
    suspend fun updateExpenseCategoryName(id:String,name:String)

}
