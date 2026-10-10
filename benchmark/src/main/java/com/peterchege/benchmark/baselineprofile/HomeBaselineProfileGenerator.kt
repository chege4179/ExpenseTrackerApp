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
package com.peterchege.benchmark.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.peterchege.benchmark.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeBaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()


    @Test
    fun generate() = baselineProfileRule.collect(
        Constants.PACKAGE_NAME,
        includeInStartupProfile = false,
        profileBlock = {
            pressHome()
            startActivityAndWait()

            waitForMenuOptionsSection()
        },
    )
}

fun MacrobenchmarkScope.waitForMenuOptionsSection() {
    device.wait(Until.hasObject(By.res("home:menuOptions")), 5000)
}

fun MacrobenchmarkScope.addDemoIncome(){

    val addIncomeButton = device.findObject(By.text("Add Income"))
    addIncomeButton.click()
    device.wait(Until.hasObject(By.res("addIncomeBottomSheet")), 5000)


}
