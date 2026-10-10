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
package com.peterchege.expensetrackerapp.presentation.bottomsheets.view

import com.peterchege.expensetrackerapp.presentation.components.DeleteButton
import com.peterchege.expensetrackerapp.presentation.components.DetailsCard
import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.presentation.bottomsheets.viewModels.IncomeInfoBottomSheetViewModel
import com.peterchege.expensetrackerapp.R

@Composable
fun IncomeInfoBottomSheet(
    activeIncomeId:String?,
    viewModel:IncomeInfoBottomSheetViewModel = hiltViewModel(),
    
) {
    val income by viewModel.income.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = activeIncomeId){
        if (activeIncomeId != null) {
            viewModel.getIncomeById(incomeId = activeIncomeId)
        }
    }
    IncomeInfoBottomSheetContent(
        incomeNullable = income,
        deleteIncome = {
            if (activeIncomeId != null) {
                viewModel.deleteIncome(incomeId = activeIncomeId)
            }
        }
    )

}

@Composable
fun IncomeInfoBottomSheetContent(
    incomeNullable: Income?,
    deleteIncome:() -> Unit,
){
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onBackground)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ){
        incomeNullable?.let { income: Income ->
            DetailsCard(
                title = income.incomeName,
                amount = income.incomeAmount,
                details = listOf(
                    "Date" to income.incomeCreatedAt,
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            DeleteButton(
                modifier = Modifier.testTag(tag = TestTags.CREATE_TRANSACTION_CATEGORY_SAVE_BUTTON),
                text = stringResource(id = R.string.delete_income),
                onClick = deleteIncome,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@ThemePreviews
@Composable
private fun IncomeInfoBottomSheetPreview() {
    PreviewSurface {
        IncomeInfoBottomSheetContent(
            incomeNullable = PreviewData.income,
            deleteIncome = {},
        )
    }
}
