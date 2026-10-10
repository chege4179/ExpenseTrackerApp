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
package com.peterchege.expensetrackerapp.core.analytics.analytics

import androidx.metrics.performance.FrameData
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

fun AnalyticsHelper.logJankyFrames(frameData: FrameData){
    logEvent(
        AnalyticsEvent(
            type ="jank",
            extras = listOf(
                Param(key = "frameStartNanos",value = frameData.frameStartNanos.toString()),
                Param(key = "frameDurationUiNanos",value = frameData.frameDurationUiNanos.toString()),
                Param(key = "states",value = frameData.states[0].value),
            )
        )
    )
}