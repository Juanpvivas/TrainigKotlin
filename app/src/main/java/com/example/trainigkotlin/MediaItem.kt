package com.example.trainigkotlin


/**
 * Created by Juan Vivas on 2019-11-10
 * Copyright (c) 2019 Merqueo. All rights reserved.
 */
data class MediaItem(val id: Int, val title: String, val urlImg: String, val type: Type) {
    enum class Type { PHOTO, VIDEO }
}