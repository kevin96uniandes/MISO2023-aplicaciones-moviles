package com.uniandes.vinyls.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.uniandes.vinyls.R
class VisitorHomeFragment : Fragment() {
    companion object {
        fun newInstance() = VisitorHomeFragment()
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
        val gotoAlbums = view.findViewById<ImageView>(R.id.go_to_album)
        val goToCollectors = view.findViewById<ImageView>(R.id.go_to_collectors)
        val goToPerformers = view.findViewById<ImageView>(R.id.go_to_performers)
        gotoAlbums.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListAlbumsFragment.newInstance("Visitante"))
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        goToCollectors.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListCollectorsFragment.newInstance())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        goToPerformers.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListPerformersFragment.newInstance())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        goToCollectors.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListCollectorsFragment.newInstance())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        goToPerformers.setOnClickListener {
            val transaction = this.activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, ListPerformersFragment.newInstance())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}