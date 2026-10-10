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
package com.peterchege.expensetrackerapp.presentation.components

import com.peterchege.expensetrackerapp.core.util.formatAmount
import androidx.compose.ui.text.style.TextOverflow
import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.presentation.preview.PreviewData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.peterchege.expensetrackerapp.core.util.generateAvatarURL
import com.peterchege.expensetrackerapp.domain.models.Transaction

@Composable
fun TransactionCard(
    transaction: Transaction,
    onTransactionNavigate: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp)
            .clickable {
                onTransactionNavigate(transaction.transactionId)
            },
        shape = RoundedCornerShape(15),
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ){

                Spacer(modifier = Modifier.width(11.dp))
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(generateAvatarURL(transaction.transactionName))
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = transaction.transactionName,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)

                    )
                    Text(
                        text = transaction.transactionCreatedOn,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    )

                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = formatAmount(transaction.transactionAmount),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
            IconButton(onClick = {
                onTransactionNavigate(transaction.transactionId)
            }) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "More "
                )

            }
        }

    }

}

@ThemePreviews
@Composable
private fun TransactionCardPreview() {
    PreviewSurface {
        TransactionCard(
            transaction = PreviewData.transaction,
            onTransactionNavigate = {},
        )
    }
}
