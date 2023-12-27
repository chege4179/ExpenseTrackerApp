package com.peterchege.expensetrackerapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseFilterCard(
    modifier: Modifier = Modifier,
    filterName:String,
    isActive:Boolean,
    onClick:(String) -> Unit

) {
    val currentBackgroundColor = if (isActive)
        MaterialTheme.colorScheme.surface
    else
        MaterialTheme.colorScheme.background
    Box(
        modifier = Modifier
            .padding(6.dp)
            .border(
                width = 1.dp,
                color = if (isActive)
                    MaterialTheme.colorScheme.surface
                else
                    MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(5.dp),
            )
            .clickable {
                onClick(filterName)
            }
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = if (isActive)
                        MaterialTheme.colorScheme.surface
                    else
                        MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(5.dp),
                )
                .background(color = currentBackgroundColor)
                .padding(6.dp)


        ) {
            Text(
                text = filterName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.primary)
            )
        }
    }

}