/*
 * Copyright 2026 Expense Tracker App By Peter Chege
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
package com.peterchege.expensetrackerapp.core.util

import android.util.Log
import androidx.profileinstaller.ProfileVerifier
import com.peterchege.expensetrackerapp.core.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileVerifierLogger @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope,
) {
    companion object {
        private val TAG = ProfileVerifierLogger::class.java.simpleName
    }

    operator fun invoke() = scope.launch {
        val status = ProfileVerifier.getCompilationStatusAsync().await()
        Timber.tag(TAG).d("Status code: %s", status.profileInstallResultCode)
        when {
            status.isCompiledWithProfile -> {
                Timber.tag(TAG).d(message ="App compiled with profile")
            }
            status.hasProfileEnqueuedForCompilation() -> {
                Timber.tag(TAG).d(message = "Profile enqueued for compilation")
            }
            else -> {
                Timber.tag(TAG).d(message ="Profile not compiled nor enqueued")
            }
        }

    }
}