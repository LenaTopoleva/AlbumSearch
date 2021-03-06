package com.lenatopoleva.albumsearch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.albumsearch.databinding.ItemSongBinding
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.data.entity.Track
import com.lenatopoleva.albumsearch.utils.TRACK
import com.lenatopoleva.albumsearch.utils.mapToTrack

class TrackListAdapter(private var data: List<Media>) : RecyclerView.Adapter<TrackListAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<Media>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackListAdapter.RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater, parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackListAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Media) {
            val track: Track
            if(data.wrapperType == TRACK) track = data.mapToTrack()
            else return
            track.let {
                with(binding) {
                    trackNumberTv.text = it.trackNumber.toString()
                    trackNameTv.text = it.trackName
                }
            }
        }
    }

}