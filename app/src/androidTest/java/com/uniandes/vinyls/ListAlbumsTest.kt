package com.uniandes.vinyls

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uniandes.vinyls.ui.MainActivity
import com.uniandes.vinyls.ui.SplashActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListAlbumsTest {

    @get: Rule
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)
    @Test
    fun useAppContext() {

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).check(ViewAssertions.matches(
            ViewMatchers.isDisplayed()))
    }

}