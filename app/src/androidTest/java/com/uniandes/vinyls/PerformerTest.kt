package com.uniandes.vinyls

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uniandes.vinyls.ui.SplashActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PerformerTest {
    @get: Rule
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun `goToListPerformersWithEmptyData`() {
        Thread.sleep(8000L)

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L)

        Espresso.onView(ViewMatchers.withId(R.id.go_to_performers)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L)

        Espresso.onView(ViewMatchers.withId(R.id.emptyDataTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}