package com.uniandes.vinyls

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uniandes.vinyls.ui.SplashActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PerformerTest {
    @get: Rule
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun `goToListPerformersWithData`() {
        Thread.sleep(8000L)

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L)

        Espresso.onView(ViewMatchers.withId(R.id.go_to_performers)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L)

        Espresso.onView(
            CoreMatchers.allOf(
            ViewMatchers.withId(R.id.performer_item_name), ViewMatchers.withText("Taylor Alison Swift")
        )).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun goToDetailPerformerByVisitor() {

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.go_to_performers)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewPerformers))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.detail_performer_name)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }
}