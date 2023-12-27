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

import androidx.datastore.preferences.core.edit
import com.peterchege.expensetrackerapp.core.datastore.preferences.UserPreferences
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
):UserPreferencesRepository {


    override suspend fun setTheme(themeValue: String) {
        preferences.setTheme(themeValue = themeValue)
    }

    override fun getTheme(): Flow<String> {
        return preferences.getTheme()
    }

    override suspend fun setShouldShowOnboarding(){
        preferences.setShouldShowOnboarding()
    }

    override fun getShouldShowOnBoarding(): Flow<Boolean> {
        return preferences.getShouldShowOnBoarding()
    }
}