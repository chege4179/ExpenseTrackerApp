package com.peterchege.expensetrackerapp.core.analytics.analytics

import com.peterchege.expensetrackerapp.core.analytics.analytics.AnalyticsEvent.Param
fun AnalyticsHelper.logNewTransactionCategoryCreated() {
    logEvent(
        AnalyticsEvent(
            type = "new_transaction_category",
            extras = listOf(
                Param(key = "new_transaction_category",value = "new_transaction_category")
            ),
        ),
    )
}

fun AnalyticsHelper.logNewExpenseCategoryCreated() {
    logEvent(
        AnalyticsEvent(
            type = "new_expense_category",
            extras = listOf(
                Param(key = "new_expense_category",value = "new_expense_category")
            ),
        ),
    )
}

fun AnalyticsHelper.logNewTransaction() {
    logEvent(
        AnalyticsEvent(
            type = "new_transaction",
            extras = listOf(
                Param(key = "new_transaction",value = "new_transaction")
            ),
        ),
    )
}
fun AnalyticsHelper.logNewExpense() {
    logEvent(
        AnalyticsEvent(
            type = "new_expense",
            extras = listOf(
                Param(key = "new_expense",value = "new_expense")
            ),
        ),
    )
}

fun AnalyticsHelper.logNewIncome() {
    logEvent(
        AnalyticsEvent(
            type = "new_income",
            extras = listOf(
                Param(key = "new_income",value = "new_income")
            ),
        ),
    )
}