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
import com.uniandes.vinyls.models.Track

class TracksAdapter(private val tracks: List<Track>) :
    RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item_fragment, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(track: Track) {
            itemView.findViewById<TextView>(R.id.track_name).text = track.name
            itemView.findViewById<TextView>(R.id.track_duracion).text = track.duration
        }

    }

}