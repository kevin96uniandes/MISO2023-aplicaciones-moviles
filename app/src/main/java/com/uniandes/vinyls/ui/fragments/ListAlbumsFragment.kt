package com.uniandes.vinyls.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.R
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel

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
        viewModel = ViewModelProvider(this).get(ListAlbumsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genreButton = view.findViewById<Button>(R.id.order_genre_button)
        val nameButton = view.findViewById<Button>(R.id.order_name_button)
        val artistButton = view.findViewById<Button>(R.id.order_artist_button)

        genreButton.setOnClickListener {
            genreButton.setBackgroundResource(R.drawable.rounded_button)
            genreButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            nameButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            nameButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            artistButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            artistButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

        nameButton.setOnClickListener {
            genreButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            genreButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            nameButton.setBackgroundResource(R.drawable.rounded_button)
            nameButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            artistButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            artistButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

        artistButton.setOnClickListener {
            genreButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            genreButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            nameButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            nameButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            artistButton.setBackgroundResource(R.drawable.rounded_button)
            artistButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

}