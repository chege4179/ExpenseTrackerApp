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
import android.provider.SyncStateContract
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


    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context):
            DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constants.USER_PREFERENCES)
            }
        )
    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>):UserPreferences {
        return UserPreferences(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(userPreferences: UserPreferences):
            UserPreferencesRepository {
        return UserPreferenceRepositoryImpl(
            preferences = userPreferences
        )
    }



}