package com.uniandes.vinyls.ui.fragments


import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.uniandes.vinyls.ui.fragments.CreateAlbumFragment
import com.uniandes.vinyls.viewmodels.CreateAlbumViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CreateAlbumFragmentTest {

    // Crea un fragmento simulado para las pruebas.
    private lateinit var createAlbumFragment: CreateAlbumFragment

    // Crea un ViewModel simulado para las pruebas.
    private val viewModel: CreateAlbumViewModel = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        createAlbumFragment = CreateAlbumFragment()
        // Inyecta el ViewModel simulado en el fragmento.
        createAlbumFragment.viewModel = viewModel
    }

    @Test
    fun testViewModelEventObservation() {
        val lifecycleOwner = mockk<LifecycleOwner>(relaxed = true)

        // Configura el ViewModel simulado para que responda como se esperaba.
        coEvery { viewModel.eventSuccessful.observe(lifecycleOwner, any()) } just Runs

        // Llama a onViewCreated con los parámetros simulados.
        createAlbumFragment.onViewCreated(mockk(), mockk())

        // Verifica que el ViewModel observe se haya llamado.
        coVerify { viewModel.eventSuccessful.observe(lifecycleOwner, any()) }
    }


    @Test
    fun addition_isCorrect() {
        Assertions.assertEquals(4, 2 + 2)
    }
}