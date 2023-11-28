package com.uniandes.vinyls


import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uniandes.vinyls.repositories.AlbumRepository
import com.uniandes.vinyls.ui.SplashActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar


@RunWith(AndroidJUnit4ClassRunner::class)
class TracksTest {

    @get: Rule
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun goToAsociationTrackAsCollectors() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_track)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_btn_save)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }

    @Test
    fun goToAsociationTrackByVisitor() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_visitors)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.go_to_album)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_track)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_btn_save)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }

    @Test
    fun goToAddTracksRequiredValues() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_track)).perform(
            ViewActions.click()
        )

        Thread.sleep(2000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Thread.sleep(2000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.error_message_name_track)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_time_track)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }

    @Test
    fun goToCreateInvalidDuration() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_track)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_duracion))
            .perform(
                ViewActions.typeText("22")
            );


        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_time_track)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun goToCreateDataSuccesfull() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_track)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_name))
            .perform(
                ViewActions.typeText("prueba expresso")
            );

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_duracion))
            .perform(
                ViewActions.typeText("2222")
            );

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.asociate_track_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Thread.sleep(2000L) // Espera por 5 segundos

    }

}