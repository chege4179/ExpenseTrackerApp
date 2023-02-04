package com.peterchege.expensetrackerapp.core.di

import android.app.Application
import android.provider.SyncStateContract
import androidx.room.Room
import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.data.ExpenseCategoryRepositoryImpl
import com.peterchege.expensetrackerapp.data.ExpenseRepositoryImpl
import com.peterchege.expensetrackerapp.data.TransactionCategoryRepositoryImpl
import com.peterchege.expensetrackerapp.data.TransactionRepositoryImpl
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseTrackerDatabase(app: Application): ExpenseTrackerAppDatabase {
        return Room.databaseBuilder(
            app,
            ExpenseTrackerAppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseCategoryRepository(database:ExpenseTrackerAppDatabase):
            ExpenseCategoryRepository {
        return ExpenseCategoryRepositoryImpl(
            db = database
        )
    }
    @Provides
    @Singleton
    fun provideExpenseRepository(database:ExpenseTrackerAppDatabase):
            ExpenseRepository {
        return ExpenseRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideTransactionCategoryRepository(database:ExpenseTrackerAppDatabase):
            TransactionCategoryRepository {
        return TransactionCategoryRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(database:ExpenseTrackerAppDatabase):
            TransactionRepository {
        return TransactionRepositoryImpl(
            db = database
        )
    }


}