package com.example.trainigkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var job: Job

    private val adapter = MediaAdapter { (id) -> navigateToDetail((id)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        progressBar.show()
        MediaProvider.dataAsync { loadFilter(it) }
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

            GlobalScope.launch(Dispatchers.Main) {
                progressBar.show()
                val media1 =
                    async(Dispatchers.IO, CoroutineStart.LAZY) { MediaProvider.dataSync("animals") }
                val media2 =
                    async(Dispatchers.IO, CoroutineStart.LAZY) { MediaProvider.dataSync("animals") }
                loadFilter(media1.await() + media2.await(), filter)
                progressBar.hide()
            }

            return true

        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun suspendeing(): List<MediaItem> = suspendCancellableCoroutine { continuation ->
        MediaProvider.dataAsync { media ->
            continuation.resume(media)
        }
    }

    private fun loadFilter(media: List<MediaItem>, filter: Filter = Filter.None) {
        adapter.list = when (filter) {
            is Filter.ByType -> media.filter { it.type == filter.data }
            Filter.None -> media
        }
        progressBar.hide()
    }

    private fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.ID to id)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
