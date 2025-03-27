package com.xxm.review.webview

import android.app.Application
import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import android.util.Log
import android.webkit.WebView
import java.util.Stack

//预加载 WebView
object WebViewCacheHolder {

    private val TAG = "WebViewCacheHolder"

    private val webViewCacheStack = Stack<WebView>()

    private const val CACHED_WEB_VIEW_MAX_NUM = 4

    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
        prepareWebView()
    }

    fun prepareWebView() {
        if (webViewCacheStack.size < CACHED_WEB_VIEW_MAX_NUM) {
            Looper.myQueue().addIdleHandler {
                Log.d(TAG, "WebViewCacheStack Size: " + webViewCacheStack.size)
                if (webViewCacheStack.size < CACHED_WEB_VIEW_MAX_NUM) {
                    webViewCacheStack.push(createWebView(MutableContextWrapper(application)))
                }
                false
            }
        }
    }


    fun acquireWebViewInternal(context: Context): WebView {
        if (webViewCacheStack.isEmpty()) {
            return createWebView(context)
        }
        val webView = webViewCacheStack.pop()
        val contextWrapper = webView.context as MutableContextWrapper
        contextWrapper.baseContext = context
        return webView
    }

    private fun createWebView(context: Context): WebView {
        return WebView(context)
    }


}