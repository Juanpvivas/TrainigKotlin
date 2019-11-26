package com.example.trainigkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.trainigkotlin.MediaProvider.dataAsync
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = MediaAdapter { (title) -> toast(title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dataAsync { data ->
            adapter.list = data.let { media ->
                when (item.itemId) {
                    R.id.filter_all -> media
                    R.id.filter_photos -> media.filter { it.type == MediaItem.Type.PHOTO }
                    R.id.filter_videos -> media.filter { it.type == MediaItem.Type.VIDEO }
                    else -> emptyList()
                }
            }
        }


        return true
    }


}
