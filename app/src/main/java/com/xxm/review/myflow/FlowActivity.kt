package com.xxm.review.myflow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xxm.review.R
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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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
        setTheme(R.style.FlowActivityTheme)
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
                        Toast.makeText(this@FlowActivity, "setupClicks: $value, threadId=${Thread.currentThread().id}", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }

        mBinding.buttonDialog.setOnClickListener {
            lifecycleScope.launch {
                showDialog("签到活动", "签到领10000币") // 直到dialog被关闭, 才会继续运行下一行
                showDialog("新手任务", "做任务领20000币") // 直到dialog被关闭, 才会继续运行下一行
                showDialog("首充奖励", "首充6元送神装")
            }

        }
    }

    suspend fun showDialog(title: String, content: String) = suspendCancellableCoroutine<Unit> { continuation ->
        MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("我知道了") { dialog, which ->
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    continuation.resume(Unit)
                }
                .show()
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
