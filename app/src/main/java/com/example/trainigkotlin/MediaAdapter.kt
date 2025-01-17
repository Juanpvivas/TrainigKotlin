package com.example.trainigkotlin

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_media_item.view.*
import kotlin.properties.Delegates


/**
 * Created by Juan Vivas on 2019-11-07
 * Copyright (c) 2019 Merqueo. All rights reserved.
 */

typealias Listener = (MediaItem) -> Unit

class MediaAdapter(private val listener: Listener) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    var list: List<MediaItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.view_media_item)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaItem = list[position]
        holder.bind(mediaItem)
        holder.itemView.setOnClickListener { listener(mediaItem) }
    }

    override fun getItemCount(): Int = list.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MediaItem) {
            itemView.apply {
                txvTitle.text = item.title
                imvThumb.loadUrl(item.urlImg)
                imgMediaIndicator.visibility = when (item.type) {
                    MediaItem.Type.PHOTO -> View.GONE
                    MediaItem.Type.VIDEO -> View.VISIBLE
                }
            }
        }
    }
}



