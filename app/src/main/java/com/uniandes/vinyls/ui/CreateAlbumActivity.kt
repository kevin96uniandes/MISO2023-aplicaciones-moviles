package com.uniandes.vinyls.ui
/*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.uniandes.vinyls.R
import com.uniandes.vinyls.databinding.ActivityCreateAlbumBinding
import com.uniandes.vinyls.ui.components.CustomEditText
import java.util.Calendar


class CreateAlbumActivity {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val editTextDate: CustomEditText = findViewById(R.id.create_album_release_date)
        addOpenDatePicker(editTextDate)
        val musicalGenreSpinner = findViewById<Spinner>(R.id.create_album_musical_genre)

        val imageButton = findViewById<ImageButton>(R.id.create_album_cover_image)
        imageButton.setOnClickListener {
            openSelectImage()
        }
        val editTextReview = findViewById<CustomEditText>(R.id.create_album_review)
        val characterCountTextView = findViewById<TextView>(R.id.create_album_review_char_counter)
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

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                // Cargar la imagen en el ImageButton
                val imageButton = findViewById<ImageButton>(R.id.create_album_cover_image)
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
    */