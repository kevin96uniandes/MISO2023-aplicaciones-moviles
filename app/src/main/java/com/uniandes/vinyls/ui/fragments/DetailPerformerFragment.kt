package com.uniandes.vinyls.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.viewmodels.DetailPerformerViewModel

class DetailPerformerFragment: Fragment() {
    private var performer: Performer = Performer()
    private lateinit var viewModel: DetailPerformerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val performerId = arguments?.getInt("idPerformer", 1) ?: 1
        Log.d("DetailPerformerFragment", "performerId: ${performerId}")

        val view = inflater.inflate(R.layout.detail_performer_fragment, container, false)
        viewModel = ViewModelProvider(this, DetailPerformerViewModel.Factory(requireActivity().application, performerId))[DetailPerformerViewModel::class.java]
        viewModel._performer.observe(viewLifecycleOwner) { performer ->
            this.performer = performer
            insertarDatosFragment(view)
        }
        return view
    }

    fun insertarDatosFragment(view: View) {

        Log.d("performer", "performer: ${performer}")

        view.findViewById<TextView>(R.id.detail_performer_name).text = this.performer.name

        val imagenCover = view.findViewById<ImageView>(R.id.image_detail_performer)
        insertarImagen(performer.image, imagenCover)

        view.findViewById<TextView>(R.id.detail_performer_descripcion).text = this.performer.description
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = activity?.getText(R.string.detalle_performers).toString()
        super.onViewCreated(view, savedInstanceState)
    }

    fun insertarImagen(urlCover: String, imagenCover: ImageView){
        Picasso.get()
            .load(urlCover)
            .error(R.drawable.ic_broken_image)
            .into(imagenCover)
    }
}