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
        private const val TAG = "ProfileInstaller"
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