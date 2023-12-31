package com.uniandes.vinyls.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.AlbumAdapter
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel

class ListAlbumsFragment : Fragment(), AlbumAdapter.OnItemClickListener {

    private var albums: List<Album> = emptyList()
    private var userType: String = "Coleccionista"
    private lateinit var loadingProgressBar: ProgressBar
    companion object {
        @JvmStatic
        fun newInstance(userType: String) =
            ListAlbumsFragment().apply {
                arguments = Bundle().apply {
                    putString("userType", userType)
                }
            }
    }
    private lateinit var viewModel: ListAlbumsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userType = it.getString("userType")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_albums, container, false)

        viewModel = ViewModelProvider(this, ListAlbumsViewModel.Factory(requireActivity().application))[ListAlbumsViewModel::class.java]
        viewModel.getListAlbums()

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAlbums)
            recyclerView.visibility = View.GONE
            loadingProgressBar.visibility = View.VISIBLE

            this.albums = albums
            val albumAdapter = AlbumAdapter(this.albums, this)

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = albumAdapter
            loadingProgressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.menu_albums).toString()
        val genreButton = view.findViewById<Button>(R.id.order_genre_button)
        val nameButton = view.findViewById<Button>(R.id.order_name_button)
        val searchBox = view.findViewById<SearchView>(R.id.search_albums)
        val addAlbum = view.findViewById<Button>(R.id.add_album)
        loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingProgressBar)
        if(userType == "Visitante"){
            addAlbum.visibility = View.GONE
        }

        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(criterio: String?): Boolean {
                criterio?.let {
                    viewModel.filterByAlbumName(criterio)
                }
                return true
            }

            override fun onQueryTextChange(criterio: String?): Boolean {
                criterio?.let {
                    viewModel.filterByAlbumName(criterio)
                }
                return true
            }
        })

        genreButton.setOnClickListener {
            genreButton.setBackgroundResource(R.drawable.rounded_button)
            genreButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            nameButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            nameButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            viewModel.orderBy("GENERO")
        }

        nameButton.setOnClickListener {
            genreButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            genreButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            nameButton.setBackgroundResource(R.drawable.rounded_button)
            nameButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            viewModel.orderBy("NOMBRE")
        }

        addAlbum.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, CreateAlbumFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }

    override fun onItemClick(position: Int) {
        val idAlbum = albums[position].albumId

        val bundle = Bundle()
        bundle.putInt("idAlbum", idAlbum)

        val detailAlbumFragment = DetailAlbumFragment()
        detailAlbumFragment.arguments = bundle

        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, detailAlbumFragment)
        transaction?.disallowAddToBackStack()
        transaction?.commit()
    }

}