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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.peterchege.expensetrackerapp.core.util.TestTags
import com.peterchege.expensetrackerapp.core.util.truncate


@Composable
fun MenuSample(
    selectedIndex: Int,
    onChangeSelectedIndex: (Int) -> Unit,
    menuItems: List<String>,
    menuWidth: Int
) {
    var menuListExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(menuWidth.dp)
            .height(95.dp)
            .padding(10.dp)
            .testTag(TestTags.GENERAL_DROPDOWN),
        contentAlignment = Alignment.CenterStart
    ) {
        ComposeMenu(
            menuItems = menuItems,
            menuExpandedState = menuListExpanded,
            selectedIndex = selectedIndex,
            updateMenuExpandStatus = {
                menuListExpanded = true
            },
            onDismissMenuView = {
                menuListExpanded = false
            },
            onMenuItemclick = { index ->
                onChangeSelectedIndex(index)
                menuListExpanded = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeMenu(
    menuItems: List<String>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemclick: (Int) -> Unit,
) {
    // box
    ExposedDropdownMenuBox(
        expanded = menuExpandedState,
        onExpandedChange = {
            updateMenuExpandStatus()
        }
    ) {
        // text field
        OutlinedTextField(
            value = if (menuItems.isEmpty()) "" else truncate(menuItems[selectedIndex],15),
            onValueChange = {},
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = menuExpandedState
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary
            )
        )

        // menu
        ExposedDropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onBackground)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary)),
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() }
        ) {
            // this is a column scope
            // all the items are added vertically
            menuItems.forEachIndexed { index, selectedOption ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onBackground),
                    text = {
                        Text(text = selectedOption)
                    },
                    onClick = {
                        onMenuItemclick(index)
                    }
                )
            }
        }
    }
}