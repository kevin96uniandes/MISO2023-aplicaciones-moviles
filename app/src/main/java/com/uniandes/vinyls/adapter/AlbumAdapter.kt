package com.uniandes.vinyls.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinyls.R
import com.uniandes.vinyls.models.Album

class AlbumAdapter(private val albums: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item_fragment, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(album: Album) {
            itemView.findViewById<TextView>(R.id.album_name1).text = album.name
            itemView.findViewById<TextView>(R.id.album_genre1).text = album.genre

            //Url imagen
            val imageView =  itemView.findViewById<ImageView>(R.id.album_cover1)

            Picasso.get()
                .load(album.cover)
                .into(imageView)
        }
    }
}