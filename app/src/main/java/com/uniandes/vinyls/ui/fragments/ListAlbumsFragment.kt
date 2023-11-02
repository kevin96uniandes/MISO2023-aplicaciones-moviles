package com.uniandes.vinyls.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel
import kotlin.math.log

class ListAlbumsFragment : Fragment() {

    companion object {
        fun newInstance() = ListAlbumsFragment()
    }

    private lateinit var viewModel: ListAlbumsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_albums, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireNotNull(this.activity)

        viewModel = ViewModelProvider(this, ListAlbumsViewModel.Factory(activity.application)).get(ListAlbumsViewModel::class.java)
        viewModel.getListAlbums()

        viewModel.albums.observe(viewLifecycleOwner) {albums ->
            Log.d("onActivityCreated", albums.toString())
        }
        // TODO: Use the ViewModel
    }

}