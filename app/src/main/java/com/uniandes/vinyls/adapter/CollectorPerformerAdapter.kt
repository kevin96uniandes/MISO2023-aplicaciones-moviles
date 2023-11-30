package com.uniandes.vinyls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.CollectorPerformer
import com.uniandes.vinyls.ui.components.CustomTextView

class CollectorPerformerAdapter(
    private val collectorPerformer: List<CollectorPerformer>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CollectorPerformerAdapter.CollectorPerformerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectorPerformerAdapter.CollectorPerformerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collector_detail_item_fragment, parent, false)
        return CollectorPerformerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CollectorPerformerAdapter.CollectorPerformerViewHolder,
        position: Int
    ) {
        val performer = collectorPerformer[position]
        holder.bind(performer)
    }

    override fun getItemCount(): Int = collectorPerformer.size

    inner class CollectorPerformerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(collectorPerformer: CollectorPerformer) {
            itemView.findViewById<CustomTextView>(R.id.collector_detail_name).text = collectorPerformer.name
            Picasso.get()
                .load(collectorPerformer.image)
                .error(R.drawable.ic_broken_image)
                .into(itemView.findViewById<ImageView>(R.id.collector_detail_image))
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemCollectorPerformerClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemCollectorPerformerClick(position: Int)
    }
}