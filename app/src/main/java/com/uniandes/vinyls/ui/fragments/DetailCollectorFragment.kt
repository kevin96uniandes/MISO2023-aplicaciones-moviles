package com.uniandes.vinyls.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.CollectorAlbumAdapter
import com.uniandes.vinyls.adapter.CollectorArtistAdapter
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.ui.components.CustomTextView
import com.uniandes.vinyls.viewmodels.CollectorAlbumViewModel
import com.uniandes.vinyls.viewmodels.CollectorArtistViewModel

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
    private var albums: List<CollectorAlbum> = listOf()
    private var artists: List<CollectorArtist> = listOf()
    private lateinit var loadingAlbumsProgressBar: ProgressBar
    private lateinit var loadingArtistsProgressBar: ProgressBar
    private var param2: String? = null
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.menu_artists).toString()
        val collector = arguments?.getParcelable<Collector>("collector")
        collector?.let {
            twName.text = collector.name
            twPhone.text = collector.telephone
            twEmail.text = collector.email

            //Albums
            loadingAlbumsProgressBar =  view.findViewById(R.id.loadingAlbumsProgressBar)
            viewModelCollectorAlbum = ViewModelProvider(
                this,
                CollectorAlbumViewModel.Factory(requireActivity().application)
            )[CollectorAlbumViewModel::class.java]
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
                    val tvNoRecords = view.findViewById<TextView>(R.id.emptyDataAlbumsTextView)
                    tvNoRecords.visibility = View.VISIBLE
                }
            }

            //Artists
            loadingArtistsProgressBar =  view.findViewById(R.id.loadingArtistsProgressBar)
            viewModelCollectorArtist = ViewModelProvider(
                this,
                CollectorArtistViewModel.Factory(requireActivity().application)
            )[CollectorArtistViewModel::class.java]
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
                    val tvNoRecords = view.findViewById<TextView>(R.id.emptyDataArtistsTextView)
                    tvNoRecords.visibility = View.VISIBLE
                }
            }
        }

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