package com.uniandes.vinyls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.ui.components.CustomTextView

class CollectorArtistAdapter(
    private val collectorArtist: List<CollectorArtist>
) : RecyclerView.Adapter<CollectorArtistAdapter.CollectorArtistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectorArtistAdapter.CollectorArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collector_detail_item_fragment, parent, false)
        return CollectorArtistViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CollectorArtistAdapter.CollectorArtistViewHolder,
        position: Int
    ) {
        val artist = collectorArtist[position]
        holder.bind(artist)
    }

    override fun getItemCount(): Int = collectorArtist.size

    inner class CollectorArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(collectorArtist: CollectorArtist) {
            itemView.findViewById<CustomTextView>(R.id.collector_detail_name).text = collectorArtist.name
            Picasso.get()
                .load(collectorArtist.image)
                .error(R.drawable.ic_broken_image)
                .into(itemView.findViewById<ImageView>(R.id.collector_detail_image))
        }
    }
}