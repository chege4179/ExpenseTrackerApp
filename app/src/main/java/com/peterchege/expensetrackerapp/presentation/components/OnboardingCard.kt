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
package com.peterchege.expensetrackerapp.presentation.components

import com.peterchege.expensetrackerapp.presentation.preview.ThemePreviews
import com.peterchege.expensetrackerapp.presentation.preview.PreviewSurface
import com.peterchege.expensetrackerapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun OnBoardingCard(
    header: String,
    description: String,
    image: Int
) {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(90.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = header,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = description,
                style = TextStyle(color = MaterialTheme.colorScheme.primary)
            )

        }
    }

}

@ThemePreviews
@Composable
private fun OnBoardingCardPreview() {
    PreviewSurface {
        OnBoardingCard(
            header = "Track your spending",
            description = "See where your money goes with simple analytics",
            image = R.drawable.analytics,
        )
    }
}
