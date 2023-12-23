package com.peterchege.expensetrackerapp.core.di

import android.app.Activity
import android.util.Log
import android.view.Window
import androidx.metrics.performance.JankStats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import timber.log.Timber

@Module
@InstallIn(ActivityComponent::class)
object JankStatsModule {
    @Provides
    fun providesOnFrameListener(): JankStats.OnFrameListener {
        return JankStats.OnFrameListener { frameData ->
            // Make sure to only log janky frames.
            if (frameData.isJank) {
                Timber.tag("App Jank").v(frameData.toString())
            }
        }
    }

    @Provides
    fun providesWindow(activity: Activity): Window {
        return activity.window
    }

    @Provides
    fun providesJankStats(
        window: Window,
        frameListener: JankStats.OnFrameListener,
    ): JankStats {
        return JankStats.createAndTrack(window, frameListener)
    }
}
