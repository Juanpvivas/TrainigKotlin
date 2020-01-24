package com.example.trainigkotlin

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object MediaProvider {

    private const val thumbBase = "http://placeimg.com/640/480/animals/"

    private var data = emptyList<MediaItem>()

    fun dataAsync(callBack: (List<MediaItem>) -> Unit) {
        doAsync {
            if (data.isEmpty()) {
                Thread.sleep(4000)
                data = (1..10).map {
                    MediaItem(
                        it,
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
