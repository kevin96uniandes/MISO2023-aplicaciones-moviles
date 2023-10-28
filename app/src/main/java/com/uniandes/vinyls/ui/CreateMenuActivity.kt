package com.uniandes.vinyls.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.uniandes.vinyls.R
import com.uniandes.vinyls.databinding.ActivityCreateMenuBinding
import com.uniandes.vinyls.models.Genre
import com.uniandes.vinyls.ui.components.ButtonsActions
import com.uniandes.vinyls.ui.components.CustomButton
import com.uniandes.vinyls.ui.components.CustomEditText
import com.uniandes.vinyls.ui.components.CustomTextView
import java.util.Calendar


class CreateMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val editTextDate: CustomEditText = findViewById(R.id.create_menu_release_date)
        addOpenDatePicker(editTextDate)
        val musicalGenreSpinner = findViewById<Spinner>(R.id.create_menu_musical_genre)
        fillMusicalGenre(musicalGenreSpinner)
        val imageButton = findViewById<ImageButton>(R.id.create_menu_cover_image)
        imageButton.setOnClickListener {
            openSelectImage()
        }
        val editTextReview = findViewById<CustomEditText>(R.id.create_menu_review)
        val characterCountTextView = findViewById<TextView>(R.id.create_menu_review_char_counter)
        characterCounter(editTextReview, characterCountTextView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    fun fillMusicalGenre(musicalGenreSpinner: Spinner){
        val items = listOf("Seleccione género") + Genre.values().map { it.genre }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        musicalGenreSpinner.adapter = adapter

        musicalGenreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                // Cargar la imagen en el ImageButton
                val imageButton = findViewById<ImageButton>(R.id.create_menu_cover_image)
                imageButton.setImageURI(selectedImageUri)
            }
        }
    }

    @SuppressLint("IntentReset")
    fun openSelectImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type="image/*"
        pickImage.launch(intent)
    }

    fun characterCounter(editTextReview: CustomEditText, characterCountTextView: TextView) {


        val maxCharacters = 200 // Cambia este valor al límite de caracteres deseado


        editTextReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Antes de que el texto cambie
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Durante el cambio de texto
            }

            override fun afterTextChanged(s: Editable?) {
                val remainingCharacters = maxCharacters - s.toString().length
                val characterCountText = getString(R.string.character_count, remainingCharacters, maxCharacters)
                characterCountTextView.text = characterCountText
            }
        })
    }
}