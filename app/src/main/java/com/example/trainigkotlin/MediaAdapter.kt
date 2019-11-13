package com.example.trainigkotlin

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Juan Vivas on 2019-11-07
 * Copyright (c) 2019 Merqueo. All rights reserved.
 */


class MediaAdapter(private val list: List<MediaItem>) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.view_media_item)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.find<TextView>(R.id.txvTitle)
        private val thumbUrl = view.find<ImageView>(R.id.imvThumb)
        private val mediaVideoIndicator = view.find<ImageView>(R.id.media_video_indicator)

        fun bind(item: MediaItem) {
            title.text = item.title
            thumbUrl.loadUrl(item.urlImg)
            mediaVideoIndicator.visibility = when(item.type){
                MediaItem.Type.PHOTO -> View.GONE
                MediaItem.Type.VIDEO -> View.VISIBLE
            }
        }
    }
}



