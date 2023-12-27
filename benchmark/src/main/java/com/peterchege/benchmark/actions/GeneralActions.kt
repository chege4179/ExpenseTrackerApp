package com.peterchege.benchmark.actions

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.getTopAppBar(tagName:String): UiObject2 {
    device.wait(Until.hasObject(By.res(tagName)), 2_000)
    return device.findObject(By.res(tagName))
}