package com.peterchege.expensetrackerapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peterchege.expensetrackerapp.core.util.Screens

@Composable
fun HomeSubHeader(
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.weight(1f, fill = false)
        )
        Text(
            text = "See All",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.clickable {
                onClick()
            }
        )

    }

}