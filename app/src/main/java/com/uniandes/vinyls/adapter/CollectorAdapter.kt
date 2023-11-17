package com.uniandes.vinyls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.Collector

class CollectorAdapter(private val collectors: List<Collector>) :
        RecyclerView.Adapter<CollectorAdapter.CollectorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collector_item_fragment, parent, false)
        return CollectorsViewHolder(view)
    }

    override fun getItemCount(): Int = collectors.size

    override fun onBindViewHolder(holder: CollectorsViewHolder, position: Int) {
        val collector = collectors[position]
        holder.bind(collector)
    }

    inner class CollectorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(collector: Collector) {
            itemView.findViewById<TextView>(R.id.collector_item_name).text = collector.name

        }
    }
}