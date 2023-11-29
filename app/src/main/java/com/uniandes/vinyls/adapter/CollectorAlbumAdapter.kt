package com.uniandes.vinyls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.ui.components.CustomTextView

class CollectorAlbumAdapter(
    private val collectorAlbum: List<CollectorAlbum>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CollectorAlbumAdapter.CollectorAlbumViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectorAlbumAdapter.CollectorAlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collector_detail_item_fragment, parent, false)
        return CollectorAlbumViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CollectorAlbumAdapter.CollectorAlbumViewHolder,
        position: Int
    ) {
        val album = collectorAlbum[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = collectorAlbum.size

    inner class CollectorAlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(collectorAlbum: CollectorAlbum) {
            itemView.findViewById<CustomTextView>(R.id.collector_detail_name).text = collectorAlbum.album.name
            Picasso.get()
                .load(collectorAlbum.album.cover)
                .error(R.drawable.ic_broken_image)
                .into(itemView.findViewById<ImageView>(R.id.collector_detail_image))
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