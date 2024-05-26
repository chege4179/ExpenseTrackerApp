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
package com.peterchege.expensetrackerapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.installStatus
import com.peterchege.expensetrackerapp.core.util.Constants
import com.peterchege.expensetrackerapp.presentation.navigation.AppNavigation
import com.peterchege.expensetrackerapp.presentation.theme.ExpenseTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val tag = MainActivity::class.java.simpleName
    @Inject
    lateinit var lazyStats: dagger.Lazy<JankStats>

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateAvailable = MutableStateFlow(false)
    private var updateInfo: AppUpdateInfo? = null
    private val updateListener = InstallStateUpdatedListener { state: InstallState ->
        Timber.tag(tag).i("Install status ${state.installStatus}")
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            appUpdateManager.completeUpdate()
        } else if (state.installStatus() == InstallStatus.INSTALLED) {
            removeInstallStateUpdateListener()
        } else if (state.installStatus() == InstallStatus.FAILED) {
            removeInstallStateUpdateListener()
        } else if (state.installStatus() == InstallStatus.UNKNOWN) {
            removeInstallStateUpdateListener()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        try {
            appUpdateManager = AppUpdateManagerFactory.create(this)
            appUpdateManager.registerListener(updateListener)
            checkForUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.tag(tag).e("Try check update info exception: ${e.message}")
        }
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val theme by viewModel.theme.collectAsStateWithLifecycle()
            val shouldShowOnboarding = runBlocking { viewModel.shouldShowOnboarding.first() }

            ExpenseTrackerAppTheme(
                darkTheme = shouldUseDarkTheme(theme = theme)
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    AppNavigation(
                        navHostController = navHostController,
                        shouldShowOnBoarding = shouldShowOnboarding,
                        openOSSMenu = {
                            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lazyStats.get().isTrackingEnabled = true

        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    appUpdateManager.completeUpdate()
                } else if (appUpdateInfo.installStatus() == InstallStatus.INSTALLED) {
                    removeInstallStateUpdateListener()
                } else if (appUpdateInfo.installStatus() == InstallStatus.FAILED) {
                    removeInstallStateUpdateListener()
                } else if (appUpdateInfo.installStatus() == InstallStatus.UNKNOWN) {
                    removeInstallStateUpdateListener()
                }
            }

    }

    private fun removeInstallStateUpdateListener() {
        appUpdateManager.unregisterListener(updateListener)
    }

    private fun startForInAppUpdate(it: AppUpdateInfo?) {
        appUpdateManager.startUpdateFlowForResult(it!!, AppUpdateType.FLEXIBLE, this, 1101)
    }

    private fun checkForUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            Timber.tag(tag).e("Update info: ${it.availableVersionCode()}")
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                it.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                updateInfo = it
                updateAvailable.update { true }
                startForInAppUpdate(updateInfo)
            } else {
                updateAvailable.update { false }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }
}

@Composable
private fun shouldUseDarkTheme(
    theme: String,
): Boolean = when (theme) {
    Constants.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    Constants.LIGHT_MODE -> false
    Constants.DARK_MODE -> true
    else -> isSystemInDarkTheme()
}