package com.example.trainigkotlin


/**
 * Created by Juan Vivas on 2019-11-28
 * Copyright (c) 2019 Merqueo. All rights reserved.
 */
sealed class Filter {
    class ByType(val data: MediaItem.Type) : Filter()
    object None: Filter()
}