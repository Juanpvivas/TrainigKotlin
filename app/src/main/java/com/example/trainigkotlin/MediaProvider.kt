package com.example.trainigkotlin

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object MediaProvider {

    private const val thumbBase = "http://lorempixel.com/400/400/cats/"

    private var data = emptyList<MediaItem>()

    fun dataAsync(callBack: (List<MediaItem>) -> Unit) {
        doAsync {
            if (data.isEmpty()) {
                Thread.sleep(2000)
                data = (1..10).map {
                    MediaItem(
                        "Title $it",
                        thumbBase.plus(it),
                        if (it % 3 == 0) MediaItem.Type.VIDEO else MediaItem.Type.PHOTO
                    )
                }
            }
            uiThread {
                callBack(data)
            }

        }
    }
}
