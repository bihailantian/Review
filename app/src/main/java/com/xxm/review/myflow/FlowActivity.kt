package com.xxm.review.myflow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xxm.review.databinding.ActivityFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    private val TAG = "FlowActivity-"
    private lateinit var flow: Flow<Int>

    private lateinit var mBinding: ActivityFlowBinding
    private val curCount = MutableStateFlow<Int?>(null)
    private var count = 0

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
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


        lifecycleScope.launch {
            //收集curCount的变化之处
            curCount.collect {
                mBinding.tvCount.text = "count:$it"
            }
        }

        mBinding.btnMutableStateFlow.setOnClickListener {
            count++
            curCount.tryEmit(count) //往外通知
        }


    }


    @InternalCoroutinesApi
    private fun setupClicks() {
        mBinding.button.setOnClickListener {
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
