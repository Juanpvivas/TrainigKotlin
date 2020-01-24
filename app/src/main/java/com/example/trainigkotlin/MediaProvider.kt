package com.example.trainigkotlin

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object MediaProvider {

    private const val thumbBase = "http://placeimg.com/640/480/"

    private var data = emptyList<MediaItem>()

    fun dataAsync(type: String = "animals",callBack: (List<MediaItem>) -> Unit) {
        doAsync {
            if (data.isEmpty()) {
                dataSync(type)
            }
            uiThread {
                callBack(data)
            }
        }
    }

    fun dataSync(type: String): List<MediaItem>{
        Thread.sleep(4000)
        return (1..10).map {
            MediaItem(
                it,
                "Title $it",
                thumbBase.plus(it),
                if (it % 3 == 0) MediaItem.Type.VIDEO else MediaItem.Type.PHOTO
            )
        }
    }
}
