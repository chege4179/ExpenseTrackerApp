package com.peterchege.expensetrackerapp.core.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.peterchege.expensetrackerapp.core.analytics.analytics.AnalyticsHelper
import com.peterchege.expensetrackerapp.core.analytics.analytics.FirebaseAnalyticsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule  {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalyticsHelper(firebaseAnalytics: FirebaseAnalytics): AnalyticsHelper {
        return FirebaseAnalyticsHelper(firebaseAnalytics = firebaseAnalytics)

    }
}