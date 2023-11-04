package com.uniandes.vinyls


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uniandes.vinyls.repositories.AlbumRepository
import com.uniandes.vinyls.ui.SplashActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListAlbumsTest {

    @get: Rule
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    private lateinit var albumRepository: AlbumRepository

    @Test
    fun goToAlbumsAsCollectors() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.withText("Listado de álbumes"))
        )

    }

    @Test
    fun goToAlbumsAsVisitor() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.go_to_album)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.withText("Listado de álbumes"))
        )

    }

    @Test
    fun goToAlbumsAndGetAlbumsOrderByName() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.go_to_album)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(CoreMatchers.allOf(
            ViewMatchers.withId(R.id.album_name1), ViewMatchers.withText("Bethoveen")
        )).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }
    @Test
    fun goToAlbumsAndOrderByGenre() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.go_to_album)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.order_genre_button)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Bethoveen"))))
    }

}