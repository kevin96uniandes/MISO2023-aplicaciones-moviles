package com.uniandes.vinyls.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.AlbumAdapter
import com.uniandes.vinyls.adapter.TracksAdapter
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track
import com.uniandes.vinyls.viewmodels.DetailAlbumViewModel
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [detail_album_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailAlbumFragment : Fragment() {
    private var album: Album = Album()
    private lateinit var viewModel: DetailAlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val albumId = arguments?.getInt("idAlbum", 1) ?: 1
        Log.d("DetailAlbumFragment", "albumId: ${albumId}")

        val view = inflater.inflate(R.layout.detail_album_fragment, container, false)
        viewModel = ViewModelProvider(this, DetailAlbumViewModel.Factory(requireActivity().application, albumId))[DetailAlbumViewModel::class.java]
        viewModel._album.observe(viewLifecycleOwner) { album ->
            this.album = album
            insertarDatosFragment(view)
        }
        return view
    }
     fun insertarDatosFragment(view: View) {

         activity?.title = album.name.toString()

         Log.d("album", "album: ${album}")
         Log.d("tracks", "tracks: ${album.tracks}")

         val imagenCover = view.findViewById<ImageView>(R.id.image_detail_cover)
         insertarImagen(album.cover, imagenCover)

         view.findViewById<TextView>(R.id.detail_album_descripcion).text = this.album.description

         view.findViewById<TextView>(R.id.detail_album_fecha_lanzamiento).text =
             "fecha de lanzamiento: ${this.album.formatDateReelaseDate()}"
         view.findViewById<TextView>(R.id.detail_album_genero).text = this.album.genre

        inicializarRecicleViewTracks(view)

     }

    fun inicializarRecicleViewTracks(view: View){
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewTracks)
        val tracksAdapter = TracksAdapter(this.album.tracks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tracksAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addTrack = view.findViewById<Button>(R.id.add_track)

        addTrack.setOnClickListener {

            val bundle = Bundle()
            bundle.putInt("idAlbum", album.albumId)

            val asociateTrackFragment = AsociateTrackFragment()
            asociateTrackFragment.arguments = bundle

            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, asociateTrackFragment)
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun insertarImagen(urlCover: String, imagenCover: ImageView){
        Picasso.get()
            .load(urlCover)
            .error(R.drawable.ic_broken_image)
            .into(imagenCover)
    }

}