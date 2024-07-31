package com.peterchege.expensetrackerapp.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.domain.repository.UserPreferencesRepository
import com.peterchege.expensetrackerapp.core.util.ExampleExpenseCategory
import com.peterchege.expensetrackerapp.core.util.ExampleTransactionCategory
import com.peterchege.expensetrackerapp.core.util.toExpenseCategory
import com.peterchege.expensetrackerapp.core.util.toTransactionCategory
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository,
    private val expenseCategoryRepository: ExpenseCategoryRepository,
) : ViewModel() {
    private var _selectedTransactionCategories =
        MutableStateFlow<List<ExampleTransactionCategory>>(emptyList())
    val selectedTransactionCategories = _selectedTransactionCategories.asStateFlow()

    private var _selectedExpenseCategories =
        MutableStateFlow<List<ExampleExpenseCategory>>(emptyList())
    val selectedExpenseCategories = _selectedExpenseCategories.asStateFlow()

    fun finishOnboarding(navigateToHome: () -> Unit) {
        val transactionCategories = _selectedTransactionCategories.value.map { it.toTransactionCategory() }
        val expenseCategories = _selectedExpenseCategories.value.map { it.toExpenseCategory() }
        viewModelScope.launch {
            transactionCategories.map {
                transactionCategoryRepository.saveTransactionCategory(it)
            }
            expenseCategories.map {
                expenseCategoryRepository.saveExpenseCategory(it)
            }
            userPreferencesRepository.setShouldShowOnboarding()
            navigateToHome()
        }
    }

    fun addTransactionCategory(category: ExampleTransactionCategory) {
        _selectedTransactionCategories.update { it + category }

    }

    fun addExpenseCategory(category: ExampleExpenseCategory) {
        _selectedExpenseCategories.update { it + category }
    }

    fun removeTransactionCategory(category: ExampleTransactionCategory) {
        _selectedTransactionCategories.value =
            _selectedTransactionCategories.value.filterNot { it.name == category.name }
    }
    fun removeExpenseCategory(category: ExampleExpenseCategory) {
        _selectedExpenseCategories.value =
            _selectedExpenseCategories.value.filterNot { it.name == category.name }
    }
}