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
