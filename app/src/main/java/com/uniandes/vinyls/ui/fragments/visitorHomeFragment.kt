package com.uniandes.vinyls.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.R

class visitorHomeFragment : Fragment() {

    companion object {
        fun newInstance() = visitorHomeFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visitor_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)

        val gotoAlbums = view.findViewById<ImageView>(R.id.home_disco)

        gotoAlbums.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListAlbumsFragment.newInstance("Visitante"))
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}

