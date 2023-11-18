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
import com.uniandes.vinyls.adapter.PerformerAdapter
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.viewmodels.ListPerformersViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListPerformersFragment : Fragment() {
    private lateinit var viewModel: ListPerformersViewModel
    private var performers: List<Performer> = listOf()
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
        val view = inflater.inflate(R.layout.fragment_list_performers, container, false)
        try {

            loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingProgressBar)
            viewModel = ViewModelProvider(
                this,
                ListPerformersViewModel.Factory(requireActivity().application)
            )[ListPerformersViewModel::class.java]
            viewModel.findAll()

            viewModel.performers.observe(viewLifecycleOwner) { performers ->
                val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPerformers)
                recyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.VISIBLE

                this.performers = performers
                val performerAdapter = PerformerAdapter(this.performers)

                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = performerAdapter
                loadingProgressBar.visibility = View.GONE

                if (performers.isNotEmpty()) {
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
        activity.title = activity.getText(R.string.app_bar_performers).toString()
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
            ListPerformersFragment()
    }
}