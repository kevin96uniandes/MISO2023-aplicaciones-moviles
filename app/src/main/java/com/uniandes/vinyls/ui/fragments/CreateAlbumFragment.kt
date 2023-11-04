package com.uniandes.vinyls.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uniandes.vinyls.Genre
import com.uniandes.vinyls.R
import com.uniandes.vinyls.RecordLabel
import com.uniandes.vinyls.isFormSuccess
import com.uniandes.vinyls.ui.components.CustomSpinnerAdapter
import com.uniandes.vinyls.viewmodels.CreateAlbumViewModel
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
    private lateinit var twNameErrorMessage: TextView
    private lateinit var twReleaseDateErrorMessage: TextView
    private lateinit var twGenreErrorMessage: TextView
    private lateinit var twRecordLabelErrorMessage: TextView
    private lateinit var twCoverErrorMessage: TextView
    private lateinit var twReviewErrorMessage: TextView
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
        Se utiliza para inicializar componentes de la vista, este se ejecuto después de onCreateView
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.title_fragment_create_album).toString()
        viewModel = ViewModelProvider(this, CreateAlbumViewModel.Factory(activity.application))[CreateAlbumViewModel::class.java]
        viewModel.eventSuccessful.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    requireContext().applicationContext,
                    R.string.album_created_successful,
                    Toast.LENGTH_LONG
                ).show()

                val transaction = this.activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, ListAlbumsFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()
            } else {
                Toast.makeText(
                    requireContext().applicationContext,
                    R.string.error_creating_album,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        etName = view.findViewById(R.id.create_album_name)
        etReleaseDate = view.findViewById(R.id.create_album_release_date)
        spGenre = view.findViewById(R.id.create_album_musical_genre)
        etCoverUrl = view.findViewById(R.id.create_album_cover_image)
        spRecordLabel = view.findViewById(R.id.create_album_spinner_record_label)
        etReview = view.findViewById(R.id.create_album_review)
        btnCreateAlbum = view.findViewById(R.id.create_album_btn_save)
        twNameErrorMessage = view.findViewById(R.id.error_message_1)
        twReleaseDateErrorMessage = view.findViewById(R.id.error_message_2)
        twGenreErrorMessage = view.findViewById(R.id.error_message_3)
        twRecordLabelErrorMessage = view.findViewById(R.id.error_message_4)
        twCoverErrorMessage = view.findViewById(R.id.error_message_5)
        twReviewErrorMessage = view.findViewById(R.id.error_message_6)
        var items = listOf("-- Seleccione género --") + Genre.values().map { it.genre }
        fillSpinner( spGenre, items)
        spinnerEvents(spGenre, items)
        items = listOf("-- Seleccione estudio de grabación --") + RecordLabel.values().map { it.record }
        fillSpinner( spRecordLabel, items)
        spinnerEvents(spRecordLabel, items)
        addOpenDatePicker(etReleaseDate)
        watcherFields()

        btnCreateAlbumEvents()

    }

    private fun btnCreateAlbumEvents(){
        btnCreateAlbum.setOnClickListener {
                if (isFormSuccess(
                        listOf(
                            Pair(etName, twNameErrorMessage),
                            Pair(etCoverUrl, twCoverErrorMessage),
                            Pair(etReview, twReviewErrorMessage),
                            Pair(etReleaseDate, twReleaseDateErrorMessage),
                            Pair(spGenre, twGenreErrorMessage),
                            Pair(spRecordLabel, twRecordLabelErrorMessage),
                        ), requireActivity()
                    )
                ) {
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
    }



    fun fillSpinner(spinner: Spinner, items: List<String>){
        val adapter = CustomSpinnerAdapter(requireContext().applicationContext, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun spinnerEvents (spinner: Spinner, data: List<String>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = data[position]
                when(spinner.id) {
                    R.id.create_album_spinner_record_label -> {

                        if(position == 0 && selectedRecordLabel.isNotEmpty()) {
                            twRecordLabelErrorMessage.text = requireActivity().getText(R.string.error_spinner_required)
                            twRecordLabelErrorMessage.visibility = View.VISIBLE
                        }else{
                            twRecordLabelErrorMessage.visibility = View.GONE
                            selectedRecordLabel = selectedItem
                        }
                    }
                    else -> {

                        if(position == 0 && selectedGenre.isNotEmpty()) {
                            twGenreErrorMessage.text = requireActivity().getText(R.string.error_spinner_required)
                            twGenreErrorMessage.visibility = View.VISIBLE
                        }else{
                            twGenreErrorMessage.visibility = View.GONE
                            selectedGenre = selectedItem
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun addOpenDatePicker(editTextDate: EditText) {
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

    private fun watcherFields(){
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isFormSuccess(listOf(Pair(etName, twNameErrorMessage)), requireActivity())

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etCoverUrl.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isFormSuccess(listOf(Pair(etCoverUrl, twCoverErrorMessage)), requireActivity())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        etReleaseDate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isFormSuccess(listOf(Pair(etReleaseDate, twReleaseDateErrorMessage)), requireActivity())
            }
        })

        etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isFormSuccess(listOf(Pair(etReview, twReviewErrorMessage)), requireActivity())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }
}