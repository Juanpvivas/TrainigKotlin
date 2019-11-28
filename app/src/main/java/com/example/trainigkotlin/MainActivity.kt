package com.example.trainigkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private val adapter = MediaAdapter { (id) -> navigateToDetail((id)) }

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

        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }

        loadFilter(filter)
        return true
    }

    private fun loadFilter(filter: Filter) {
        MediaProvider.dataAsync { media ->
            adapter.list = when (filter) {
                is Filter.ByType -> media.filter { it.type == filter.data }
                Filter.None -> media
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.ID to id)
    }

}
