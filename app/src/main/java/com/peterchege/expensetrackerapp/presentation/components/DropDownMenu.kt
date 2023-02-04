package com.peterchege.expensetrackerapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MenuSample(
    selectedIndex:Int,
    onChangeSelectedIndex:(Int) -> Unit,
    menuItems: List<String>
){
    var menuListExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        ComposeMenu(
            menuItems = menuItems ,
            menuExpandedState = menuListExpanded,
            selectedIndex = selectedIndex,
            updateMenuExpandStatus = {
                menuListExpanded = true
            },
            onDismissMenuView = {
                menuListExpanded = false
            },
            onMenuItemclick = { index->
                Log.d("Index",index.toString())
                onChangeSelectedIndex(index)
                menuListExpanded = false
            }
        )
    }
}


@Composable
fun ComposeMenu(
    menuItems: List<String>,
    menuExpandedState: Boolean,
    selectedIndex : Int,
    updateMenuExpandStatus : () -> Unit,
    onDismissMenuView : () -> Unit,
    onMenuItemclick : (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),

        ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (lable, iconView) = createRefs()
            if (menuItems.isNotEmpty()){
                Text(
                    text= menuItems[selectedIndex],
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(lable) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(iconView.start)
                            width = Dimension.fillToConstraints
                        }
                )
            }


            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = menuExpandedState,
                onDismissRequest = { onDismissMenuView() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(MaterialTheme.colors.surface)
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            onMenuItemclick(index)
                        }) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}