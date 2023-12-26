package com.peterchege.expensetrackerapp.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
):ViewModel() {

    fun finishOnboarding(navigateToHome:() -> Unit){
        viewModelScope.launch {
            userPreferencesRepository.setShouldShowOnboarding()
            navigateToHome()
        }
    }
}