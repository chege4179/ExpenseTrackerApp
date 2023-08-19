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
package com.peterchege.expensetrackerapp.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.peterchege.expensetrackerapp.core.datastore.preferences.UserPreferences
import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.data.*
import com.peterchege.expensetrackerapp.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideExpenseTrackerDatabase(app: Application): ExpenseTrackerAppDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            ExpenseTrackerAppDatabase::class.java,
        ).build()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideExpenseCategoryRepository(database: ExpenseTrackerAppDatabase):
            ExpenseCategoryRepository {
        return ExpenseCategoryRepositoryImpl(
            db = database,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(database: ExpenseTrackerAppDatabase):
            ExpenseRepository {
        return ExpenseRepositoryImpl(
            db = database,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideTransactionCategoryRepository(database: ExpenseTrackerAppDatabase):
            TransactionCategoryRepository {
        return TransactionCategoryRepositoryImpl(
            db = database,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideTransactionRepository(database: ExpenseTrackerAppDatabase):
            TransactionRepository {
        return TransactionRepositoryImpl(
            db = database,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }


    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context):
            DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(name = Constants.USER_PREFERENCES)
            }
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>): UserPreferences {
        return UserPreferences(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(userPreferences: UserPreferences):
            UserPreferencesRepository {
        return UserPreferenceRepositoryImpl(
            preferences = userPreferences
        )
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideIncomeRepository(database: ExpenseTrackerAppDatabase):
            IncomeRepository {
        return IncomeRepositoryImpl(
            db = database,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }


}