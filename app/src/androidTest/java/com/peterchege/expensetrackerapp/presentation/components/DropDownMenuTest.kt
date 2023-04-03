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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.peterchege.expensetrackerapp.core.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DropDownMenuTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val dummyMenuItems = listOf("Item1", "Item2", "Item3").toMutableList()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        composeTestRule.setContent {
            var selectedIndex by remember {
                mutableStateOf(0)
            }
            MenuSample(
                selectedIndex = selectedIndex,
                onChangeSelectedIndex = {
                    selectedIndex = it

                },
                menuItems = dummyMenuItems,
                menuWidth = 300
            )
        }
    }

    @Test
    fun verifyFirstIndexIsShown() {
        val generalDropDown = composeTestRule.onNodeWithTag(testTag = TestTags.GENERAL_DROPDOWN)

        composeTestRule.onNodeWithText(text = dummyMenuItems[0]).assertIsDisplayed()


    }
}