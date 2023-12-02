package com.uniandes.vinyls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.Performer

class PerformerAdapter(private val performers: List<Performer>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<PerformerAdapter.PerformersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.performer_item_fragment, parent, false)
        return PerformersViewHolder(view)
    }

    override fun getItemCount(): Int = performers.size

    override fun onBindViewHolder(holder: PerformersViewHolder, position: Int) {
        val performer = performers[position]
        holder.bind(performer)
    }

    inner class PerformersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(performer: Performer) {
            itemView.findViewById<TextView>(R.id.performer_item_name).text = performer.name
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}