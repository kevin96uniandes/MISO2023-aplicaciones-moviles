package com.uniandes.vinyls.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.adapter.CollectorAdapter
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.viewmodels.ListAlbumsViewModel
import com.uniandes.vinyls.viewmodels.ListCollectorsViewModel
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListCollectorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListCollectorsFragment : Fragment() {
    private lateinit var viewModel: ListCollectorsViewModel
    private var collectors: List<Collector> = listOf()
    private lateinit var loadingProgressBar: ProgressBar

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_collectors, container, false)
        try {



            loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingProgressBar)
            viewModel = ViewModelProvider(
                this,
                ListCollectorsViewModel.Factory(requireActivity().application)
            )[ListCollectorsViewModel::class.java]
            viewModel.findAll()

            viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
                val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewCollectors)
                recyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.VISIBLE

                this.collectors = collectors
                val collectorAdapter = CollectorAdapter(this.collectors)

                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = collectorAdapter
                loadingProgressBar.visibility = View.GONE

                if (collectors.isNotEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                }else {
                    val tvNoRecords = view.findViewById<TextView>(R.id.emptyDataTextView)
                    tvNoRecords.visibility = View.VISIBLE
                }

            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireNotNull(this.activity)
        activity.title = activity.getText(R.string.app_bar_collectors).toString()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListCollectorsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ListCollectorsFragment()
    }
}