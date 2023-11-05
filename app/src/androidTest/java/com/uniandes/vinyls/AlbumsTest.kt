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
class AlbumsTest {

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

    @Test
    fun goToCreateAlbum() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.create_album_name)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }
    @Test
    fun goToCreateRequiredValues() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(
            ViewActions.click()
        )

        Thread.sleep(2000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.create_album_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Thread.sleep(2000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.error_message_1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_2)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_3)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_4)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_5)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun goToCreateInvalidUrl() {

        Thread.sleep(8000L) // Espera por 5 segundos


        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.create_album_cover_image))
            .perform(
                ViewActions.typeText("Logo cover")
            );


        Espresso.onView(ViewMatchers.withId(R.id.create_album_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_5)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun goToCreateDateBefore() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Thread.sleep(5000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(
            ViewActions.click()
        )

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1  // Los meses en Calendar van de 0 a 11
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        Espresso.onView(ViewMatchers.withId(R.id.create_album_release_date)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(year, month, day))

        Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.create_album_btn_save)).perform(
            ViewActions.scrollTo()
        ).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.error_message_2)).check(
            ViewAssertions.matches(ViewMatchers.withText("La fecha seleccionada no puede ser mayor a la fecha actual"))
        )
    }

   @Test
    fun goToCreateAlbumSuccessfull() {

        Thread.sleep(8000L) // Espera por 5 segundos

        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in_as_collectors)).perform(
            ViewActions.click()
        )

        Thread.sleep(5000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.add_album)).perform(
           ViewActions.click()
       )

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) +1
        val day = calendar.get(Calendar.DAY_OF_MONTH) - 1

       Espresso.onView(ViewMatchers.withId(R.id.create_album_name))
           .perform(ViewActions.scrollTo())
           .perform(ViewActions.typeText("Nombre prueba"));

       Espresso.onView(ViewMatchers.withId(R.id.create_album_musical_genre)).perform(
           ViewActions.click()
       );

       onView(ViewMatchers.withText("Rock")).inRoot(RootMatchers.isPlatformPopup()).perform(
           ViewActions.click())

       Thread.sleep(2000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.create_album_release_date)).perform(
            ViewActions.click()
       )

       Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(year, month, day))

       Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());

       Thread.sleep(2000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.create_album_spinner_record_label)).perform(
           ViewActions.click()
       );

       onView(ViewMatchers.withText("EMI")).inRoot(RootMatchers.isPlatformPopup()).perform(
           ViewActions.click())

       Thread.sleep(2000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.create_album_cover_image))
           .perform(ViewActions.scrollTo())
           .perform(ViewActions.typeText("https://www.youtube.com"));

       Thread.sleep(2000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.create_album_review))
           .perform(ViewActions.scrollTo())
           .perform(ViewActions.typeText("REVIEW DE PRUEBA"));

       Thread.sleep(2000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.create_album_btn_save)).perform(
            ViewActions.scrollTo()
       ).perform(
            ViewActions.click()
        )

       Thread.sleep(3000L) // Espera por 5 segundos

       Espresso.onView(ViewMatchers.withId(R.id.listado_albumes_title)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

}