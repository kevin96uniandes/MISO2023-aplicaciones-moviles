package com.uniandes.vinyls.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.AlbumAdapter
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel

class ListAlbumsFragment : Fragment() {

    private var albums: List<Album> = emptyList()
    companion object {
        fun newInstance() = ListAlbumsFragment()
    }
    private lateinit var viewModel: ListAlbumsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_albums, container, false)

        viewModel = ViewModelProvider(this, ListAlbumsViewModel.Factory(requireActivity().application)).get(ListAlbumsViewModel::class.java)
        viewModel.getListAlbums()

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            this.albums = albums
            val albumAdapter = AlbumAdapter(this.albums)
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAlbums)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = albumAdapter
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)

        val genreButton = view.findViewById<Button>(R.id.order_genre_button)
        val nameButton = view.findViewById<Button>(R.id.order_name_button)
        val searchBox = view.findViewById<SearchView>(R.id.search_albums)
        val addAlbum = view.findViewById<AppCompatButton>(R.id.add_album)

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

}