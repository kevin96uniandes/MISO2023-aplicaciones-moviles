package com.uniandes.vinyls.ui.fragments


import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.uniandes.vinyls.events.SingleLiveEvent
import com.uniandes.vinyls.ui.fragments.CreateAlbumFragment
import com.uniandes.vinyls.viewmodels.CreateAlbumViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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

        // Crea un SingleLiveEvent simulado.
        val eventSuccessful = SingleLiveEvent<Boolean>()

        // Configura el ViewModel simulado para devolver el SingleLiveEvent simulado
        every { viewModel.eventSuccessful } returns eventSuccessful

        // Inyecta el ViewModel simulado en el fragmento.
        createAlbumFragment.viewModel = viewModel
    }


    @Test
    fun testViewModelEventObservation() {
        val createAlbumFragment = CreateAlbumFragment()
        createAlbumFragment.viewModel = viewModel

        // Simula el comportamiento del LiveData del ViewModel.
        coEvery { viewModel.eventSuccessful.observe(any(), any()) } just Runs

        // Llama al método que se encarga de observar el LiveData.
        createAlbumFragment.onViewCreated(mockk(), mockk())

        // Verifica que la observación se haya realizado correctamente.
        coVerify { viewModel.eventSuccessful.observe(any(), any()) }
    }
}