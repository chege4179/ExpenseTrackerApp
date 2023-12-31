package com.peterchege.benchmark.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.peterchege.benchmark.Constants
import com.peterchege.benchmark.actions.getTopAppBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingBaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()



    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = Constants.PACKAGE_NAME,
        includeInStartupProfile = true,
        profileBlock = {
            pressHome()
            startActivityAndWait()
            device.wait(Until.hasObject(By.res("onboarding")),5000)
            goThroughOnboardingProcess()
        },
    )
}

fun MacrobenchmarkScope.getNextButtonOnboarding(): UiObject2 {
    device.wait(Until.hasObject(By.res("next")), 10_000)
    return device.findObject(By.res("next"))
}
fun MacrobenchmarkScope.goThroughOnboardingProcess(){
    getNextButtonOnboarding().click()
    getNextButtonOnboarding().click()
    getNextButtonOnboarding().click()

    getTopAppBar(tagName = "HOME_SCREEN_TOP_APP_BAR_TAG")
}
