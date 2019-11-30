package com.example.trainigkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val adapter = MediaAdapter { (id) -> navigateToDetail((id)) }
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        MediaProvider.dataAsync { updateData(it) }
        job = Job()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val filter = when (item.itemId) {
            R.id.filter_all -> Filter.None
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> null
        }

        filter?.let {
            CoroutineScope(Dispatchers.Main).launch {
                val media1 = async(Dispatchers.IO) { MediaProvider.dataSync("cats") }
                val media2 = async(Dispatchers.IO) { MediaProvider.dataSync("nature") }
                updateData(media1.await() + media2.await())
            }
        }

        return true
    }

    private fun updateData(media: List<MediaItem>, filter: Filter = Filter.None) {
        adapter.list = when (filter) {
            is Filter.ByType -> media.filter { it.type == filter.data }
            Filter.None -> media
        }
    }

    private fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.ID to id)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+ job

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
