package com.uniandes.vinyls.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.uniandes.vinyls.Genre
import com.uniandes.vinyls.R
import com.uniandes.vinyls.RecordLabel
import com.uniandes.vinyls.databinding.CreateAlbumFragmentBinding
import com.uniandes.vinyls.viewmodels.CreateAlbumViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar


private const val ALBUM_ID_PARAM = "albumId"

class CreateAlbumFragment : Fragment() {
    private var selectedGenre: String = ""
    private var selectedRecordLabel: String = ""
    private lateinit var viewModel: CreateAlbumViewModel
    private lateinit var etName: EditText
    private lateinit var etCoverUrl: EditText
    private lateinit var etReleaseDate: EditText
    private lateinit var etReview: EditText
    private lateinit var spGenre: Spinner
    private lateinit var spRecordLabel: Spinner
    private lateinit var btnCreateAlbum: Button
    private var userType: String? = null

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateAlbumFragment().apply {
                arguments = Bundle().apply {
                    putInt(ALBUM_ID_PARAM, 0)
                }
            }
    }

    /*
        Se utiliza para inflar la vista
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_album_fragment, container, false)
    }

    /*
        Se utiliza para inicializar compoentes de la vista, este se ejecuto después de onCreateView
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName = view.findViewById(R.id.create_album_name)
        etReleaseDate = view.findViewById(R.id.create_album_release_date)
        spGenre = view.findViewById(R.id.create_album_musical_genre)
        etCoverUrl = view.findViewById(R.id.create_album_cover_image)
        spRecordLabel = view.findViewById(R.id.create_album_record_label)
        etReview = view.findViewById(R.id.create_album_review)
        btnCreateAlbum = view.findViewById(R.id.create_album_btn_save)
        var items = listOf("-- Seleccione género --") + Genre.values().map { it.genre }
        fillSpinner( spGenre, items)
        spinnerEvents(spGenre, items)
        items = listOf("-- Seleccione estudio de grabación --") + RecordLabel.values().map { it.record }
        fillSpinner( spRecordLabel, items)
        spinnerEvents(spRecordLabel, items)
        addOpenDatePicker(etReleaseDate)
        createAlbum()
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(
            this,
            CreateAlbumViewModel.Factory(activity.application)
        )[CreateAlbumViewModel::class.java]
    }


    private fun createAlbum(){
        btnCreateAlbum.setOnClickListener{
            val albumParams: ArrayMap<String, String> = ArrayMap()
            albumParams["name"] = etName.text.toString()
            albumParams["cover"] = etCoverUrl.text.toString()
            albumParams["releaseDate"] = etReleaseDate.text.toString()
            albumParams["description"] = etReview.text.toString()
            albumParams["genre"] = selectedGenre
            albumParams["recordLabel"] = selectedRecordLabel
            viewModel.createAlbum(albumParams)
        }
    }

    fun fillSpinner(spinner: Spinner, items: List<String>){

        val adapter = ArrayAdapter(requireContext().applicationContext, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = items[position]
                // Realiza acciones según la opción seleccionada
                // selectedItem contiene la opción seleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones si no se selecciona nada
            }
        }
    }

    fun spinnerEvents (spinner: Spinner, data: List<String>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = data[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun addOpenDatePicker(editTextDate: EditText) {
        editTextDate.setOnClickListener { v -> // Obtiene la fecha actual para mostrar en el DatePickerDialog
            val context = v.context
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth -> // Actualiza el TextView con la fecha seleccionada
                    val selectedDate = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year)
                    editTextDate.setText(selectedDate)
                },  // Establece la fecha actual como fecha inicial
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }
    }
}