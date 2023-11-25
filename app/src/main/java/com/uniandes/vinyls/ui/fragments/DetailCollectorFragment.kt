package com.uniandes.vinyls.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.NoInternetException
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.CollectorAlbumAdapter
import com.uniandes.vinyls.adapter.CollectorArtistAdapter
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.ui.components.CustomTextView
import com.uniandes.vinyls.utils.EstadoServicios
import com.uniandes.vinyls.viewmodels.CollectorAlbumViewModel
import com.uniandes.vinyls.viewmodels.CollectorArtistViewModel
import com.uniandes.vinyls.viewmodels.CollectorViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DetailCollectorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailCollectorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var twName: TextView
    private lateinit var twEmail: CustomTextView
    private lateinit var twPhone: TextView
    private lateinit var tvNoAlbumsRecords: TextView
    private lateinit var tvNoArtistsRecords: TextView
    private var albums: List<CollectorAlbum> = listOf()
    private var artists: List<CollectorArtist> = listOf()
    private lateinit var loadingAlbumsProgressBar: ProgressBar
    private lateinit var loadingArtistsProgressBar: ProgressBar
    private var param2: String? = null
    private lateinit var viewModelCollector: CollectorViewModel
    private lateinit var viewModelCollectorAlbum: CollectorAlbumViewModel
    private lateinit var viewModelCollectorArtist: CollectorArtistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_collector, container, false)
        twName = view.findViewById(R.id.detail_collector_name)
        twEmail = view.findViewById(R.id.detail_collector_email)
        twPhone = view.findViewById(R.id.detail_collector_phone)
        tvNoAlbumsRecords = view.findViewById(R.id.emptyDataAlbumsTextView)
        tvNoArtistsRecords = view.findViewById(R.id.emptyDataArtistsTextView)
        loadingAlbumsProgressBar =  view.findViewById(R.id.loadingAlbumsProgressBar)
        loadingArtistsProgressBar =  view.findViewById(R.id.loadingArtistsProgressBar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.menu_detail_collector).toString()
        val collectorId = arguments?.getInt("collectorId")
        collectorId?.let {
            viewModelCollector = ViewModelProvider(
                this,
                CollectorViewModel.Factory(requireActivity().application)
            )[CollectorViewModel::class.java]

            try {
                viewModelCollector.findById(it)
            } catch (ex: NoInternetException){
                showConnectivityErrorDialog()
            }

            viewModelCollector.collector.observe(viewLifecycleOwner) { collector ->
                collector?.let {
                    tvNoArtistsRecords.visibility = View.GONE
                    tvNoAlbumsRecords.visibility = View.GONE
                    twName.text = collector.name
                    twPhone.text = collector.telephone
                    twEmail.text = collector.email
                    viewModelCollectorAlbum = ViewModelProvider(
                        this,
                        CollectorAlbumViewModel.Factory(requireActivity().application)
                    )[CollectorAlbumViewModel::class.java]
                    fillAlbums(collector, view)
                    viewModelCollectorArtist = ViewModelProvider(
                        this,
                        CollectorArtistViewModel.Factory(requireActivity().application)
                    )[CollectorArtistViewModel::class.java]
                    fillArtists(collector, view)
                } ?: run {
                    tvNoAlbumsRecords.visibility = View.VISIBLE
                    tvNoArtistsRecords.visibility = View.VISIBLE
                    val connectionStatus = EstadoServicios()
                    if (!connectionStatus.validarConexionIntenet(requireContext())){
                        showConnectivityErrorDialog()
                    }
                }
            }
        }
    }

    private fun fillArtists(collector: Collector, view: View) {
        viewModelCollectorArtist.findAll(collector.collectorId)
        viewModelCollectorArtist.collectorArtist.observe(viewLifecycleOwner) { artists ->

            val recyclerView: RecyclerView = view.findViewById(R.id.rv_collector_artist)
            recyclerView.visibility = View.GONE
            loadingArtistsProgressBar.visibility = View.VISIBLE
            this.artists = artists
            val collectorArtistAdapter = CollectorArtistAdapter(this.artists)

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = collectorArtistAdapter

            loadingArtistsProgressBar.visibility = View.GONE
            if (artists.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
            }else {
                tvNoArtistsRecords.visibility = View.VISIBLE
            }
        }
    }

    private fun fillAlbums(collector: Collector, view: View) {
        viewModelCollectorAlbum.findAll(collector.collectorId)
        viewModelCollectorAlbum.collectorAlbum.observe(viewLifecycleOwner) { albums ->

            val recyclerView: RecyclerView = view.findViewById(R.id.rv_collector_album)
            recyclerView.visibility = View.GONE
            loadingAlbumsProgressBar.visibility = View.VISIBLE
            this.albums = albums
            val collectorAlbumAdapter = CollectorAlbumAdapter(this.albums)

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = collectorAlbumAdapter

            loadingAlbumsProgressBar.visibility = View.GONE
            if (albums.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
            }else {
                tvNoAlbumsRecords.visibility = View.VISIBLE
            }
        }
    }

    private fun showConnectivityErrorDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.connectivity_error_dialog)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            Toast.makeText(requireContext(), "¡Conéctate a Internet!", Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailCollectorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailCollectorFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}