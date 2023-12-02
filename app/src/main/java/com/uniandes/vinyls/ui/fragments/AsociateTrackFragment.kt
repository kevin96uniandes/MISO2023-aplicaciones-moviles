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
import com.uniandes.vinyls.viewmodels.AsociateTrackViewModel
import com.uniandes.vinyls.viewmodels.CreateAlbumViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar

private const val ALBUM_ID_PARAM = "albumId"
class AsociateTrackFragment : Fragment() {
    private lateinit var viewModel: AsociateTrackViewModel
    private lateinit var etNameTrack: EditText
    private lateinit var etTimeTrack: EditText
    private lateinit var twNameErrorMessage: TextView
    private lateinit var twTimeErrorMessage: TextView
    private lateinit var btnAsociateTrack: Button
    private var shouldRunValidations: Boolean = false
    private var idAlbum: Int = 0

    companion object {
        @JvmStatic
        fun newInstance() =
            AsociateTrackFragment().apply {
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
        shouldRunValidations = false
        idAlbum = arguments?.getInt("idAlbum", 1) ?: 1
        return inflater.inflate(R.layout.asociate_track_fragment, container, false)
    }

    /*
        Se utiliza para inicializar componentes de la vista, este se ejecuto después de onCreateView
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.title_fragment_asociar_track).toString()
        viewModel = ViewModelProvider(
            this,
            AsociateTrackViewModel.Factory(activity.application)
        )[AsociateTrackViewModel::class.java]
        viewModel.eventSuccessful.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    requireContext().applicationContext,
                    R.string.track_created_successful,
                    Toast.LENGTH_LONG
                ).show()
                val bundle = Bundle()
                bundle.putInt("idAlbum", idAlbum)

                val detailAlbumFragment = DetailAlbumFragment()
                detailAlbumFragment.arguments = bundle

                val transaction = this.activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, detailAlbumFragment)
                transaction?.disallowAddToBackStack()
                transaction?.commit()
            } else {
                Toast.makeText(
                    requireContext().applicationContext,
                    R.string.error_creating_track,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        etNameTrack = view.findViewById(R.id.asociate_track_name)
        etTimeTrack = view.findViewById(R.id.asociate_track_duracion)
        btnAsociateTrack = view.findViewById(R.id.asociate_track_btn_save)
        twNameErrorMessage = view.findViewById(R.id.error_message_name_track)
        twTimeErrorMessage = view.findViewById(R.id.error_message_time_track)

        val scope = CoroutineScope(Dispatchers.Main)
        val textWatchers = scope.async {
            //val components: List<Pair<EditText, TextView>> = listComponents()
            //components.map { (editText, textView) ->

            //}
            watcherFields()
        }

        scope.launch {
            textWatchers.await()
            // En este punto, todas las tareas asincrónicas se han completado.
            // Puedes continuar con tu lógica aquí.
        }


        shouldRunValidations = true
        btnAsociateTrackEvents()
    }

    private fun btnAsociateTrackEvents(){
        btnAsociateTrack.setOnClickListener {
                if (isFormSuccess(
                        listOf(
                            Pair(etNameTrack, twNameErrorMessage),
                            Pair(etTimeTrack, twTimeErrorMessage)
                        ), requireActivity()
                    )
                ) {
                    val trackParams: ArrayMap<String, String> = ArrayMap()
                    trackParams["name"] = etNameTrack.text.toString()
                    trackParams["duration"] = etTimeTrack.text.toString()
                    viewModel.asociateTrack(trackParams, idAlbum)
                }
        }
    }

    private fun listComponents(): List<Pair<EditText, TextView>> {
        return listOf(
            Pair(etNameTrack, twNameErrorMessage),
            Pair(etTimeTrack, twTimeErrorMessage)
        )
    }

    private fun watcherFields(){
        etNameTrack.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (shouldRunValidations){
                    isFormSuccess(listOf(Pair(etNameTrack, twNameErrorMessage)), requireActivity())
                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etTimeTrack.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (shouldRunValidations){
                    isFormSuccess(listOf(Pair(etTimeTrack, twTimeErrorMessage)), requireActivity())
                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

}