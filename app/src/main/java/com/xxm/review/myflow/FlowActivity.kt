package com.xxm.review.myflow

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FlowActivity : AppCompatActivity() {
    private val TAG = "FlowActivity-"
    private lateinit var flow: Flow<Int>
    private lateinit var button: Button

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
        button = findViewById(R.id.button)
        Log.d(TAG, " main threadId=${Thread.currentThread().id}")
        setupFlow()
        setupClicks()
    }

    private fun setupFlow() {
        flow = flow {
            Log.d(TAG, "Start flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                Log.d(TAG, "Emitting $it, threadId=${Thread.currentThread().id}")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)
    }


    @InternalCoroutinesApi
    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                flow.collect(object : FlowCollector<Int> {
                    override suspend fun emit(value: Int) {
                        Log.d(TAG, "setupClicks: $value, threadId=${Thread.currentThread().id}")
                    }

                })
            }
        }
    }


}


class NewsRemoteDataSource(
        private val newsApi: NewsApi,
        private val refreshIntervalMs: Long = 5000
) {
    val latestNews: Flow<List<ArticleHeadline>> = flow {
        while (true) {
            val latestNews = newsApi.fetchLatestNews()
            emit(latestNews) // Emits the result of the request to the flow
            delay(refreshIntervalMs) // Suspends the coroutine for some time
        }
    }
}

// Interface that provides a way to make network requests with suspend functions
interface NewsApi {
    suspend fun fetchLatestNews(): List<ArticleHeadline>
}
