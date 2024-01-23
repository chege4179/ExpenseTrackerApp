package com.peterchege.expensetrackerapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomIconButton(
    imageVector: ImageVector,
    modifier:Modifier = Modifier,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}