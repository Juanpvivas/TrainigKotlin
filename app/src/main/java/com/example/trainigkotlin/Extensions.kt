package com.example.trainigkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


/**
 * Created by Juan Vivas on 2019-11-10
 * Copyright (c) 2019 Merqueo. All rights reserved.
 */

fun Context.toast(msg: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, length).show()
}

fun RecyclerView.ViewHolder.toast(msg: String, length: Int = Toast.LENGTH_LONG) =
    itemView.context.toast(msg, length)

fun ViewGroup.inflate(@LayoutRes layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

inline fun <reified T: View> RecyclerView.ViewHolder.find(id: Int): T {
    return this.itemView.findViewById(id)
}
