package com.xxm.review.webview

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import kotlinx.coroutines.runBlocking
import java.io.File
import java.net.URLEncoder
import java.security.MessageDigest

/**
 * WebView初始化，与html交互，下载文件优化
 */
object WebViewManager {
    private const val TAG = "WebViewManager"


    class JsInterface {

        @JavascriptInterface
        fun onLoadSvgStart() {
            Log.d(TAG, "onLoadSvgStart")

        }

        @JavascriptInterface
        fun onLoadSvgEnd() {
            Log.d(TAG, "onLoadSvgEnd")

        }
    }

    private fun getLogTag(): String {
        return "WebViewManager"
    }

    fun initWebView(webView: WebView) {
        Logger.t(getLogTag()).d("initWebView")
        // 这行代码一定加上否则效果不会出现
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true //设置适应Html5的一些方法

        //设置可以访问文件
        webView.settings.allowFileAccess = true
        webView.settings.setAllowFileAccessFromFileURLs(true) //必须设置为true，否则加载不了
        webView.settings.setAllowUniversalAccessFromFileURLs(true)


        // 设置支持屏幕适配
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        // 是否保存密码
        webView.settings.savePassword = false

        webView.addJavascriptInterface(JsInterface(), "AndroidApp")
        webView.loadUrl("file:///android_asset/svg_construction_monitoring.html")//加载本地的html
        /*webView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                Logger.t(TAG).d("onGlobalLayout")
                webView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                webView.loadUrl("file:///android_asset/svg_construction_monitoring.html")//加载本地的html
            }
        })*/

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url!!)
                Logger.t(TAG).d("shouldOverrideUrlLoading")
                return super.shouldOverrideUrlLoading(view, url)
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                Logger.t(TAG).d("onPageFinished: 本地网页加载完成")
                //TODO 更新HTML

            }


            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                //Logger.t(TAG).d("shouldInterceptRequest host=${request?.url?.host}")
                Logger.t(TAG)
                    .d("shouldInterceptRequest method=${request.method},isForMainFrame=${request.isForMainFrame},url=${request.url}")
                var webResourceResponse: WebResourceResponse? = null

                // 如果是 assets 目录下的文件
                if (isAssetsResource(request)) {
                    webResourceResponse = assetsResourceRequest(view.context, request)
                }


                if (isCacheResource(request)) {
                    //Logger.t(TAG)
                    //    .d("shouldInterceptRequest method=${request.method},url=${request.url}")
                    webResourceResponse = cacheResourceRequest(view.context, request)
                }

                if (webResourceResponse == null) {
                    webResourceResponse = super.shouldInterceptRequest(view, request)
                }

                return webResourceResponse
            }
        }
    }


    private fun isAssetsResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        return url.startsWith("file:///android_asset/")
    }


    /**
     * assets 文件请求
     */
    private fun assetsResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        val url = webRequest.url.toString()
        try {
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse(
                getMimeTypeFromUrl(url),
                "UTF-8",
                context.assets.open("$suffix/$filename")
            )
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据 url 获取文件类型
     */
    private fun getMimeTypeFromUrl(url: String): String {
        try {
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }


    /**
     * 判断是否是可以被缓存等资源
     */
    private fun isCacheResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        //Logger.t(TAG).d("isCacheResource extension=$extension")
        return extension == "jpeg" || extension == "jpg" || extension == "png" || extension == "svg"
    }


    /**
     * 缓存资源
     */
    private fun cacheResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        val url = webRequest.url.toString()
        val mimeType = getMimeTypeFromUrl(url)

        // WebView 中的图片利用 Glide 加载(能够和App其他页面共用缓存)
        if (isImageResource(webRequest)) {
            return try {
                val file = Glide.with(context).download(url).submit().get()
                val webResourceResponse = WebResourceResponse(mimeType, "UTF-8", file.inputStream())
                webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
                webResourceResponse
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 其他文件缓存逻辑
         * 1.寻找缓存文件，本地有缓存直接返回缓存文件
         * 2.无缓存，下载到本地后返回
         * 注意！！！
         * 一定要确保文件下载完整，我这里采用下载完成后给文件加 "success-" 前缀的方法
         */
        val webCachePath = getWebCachePath(context)
        val cacheFilePath =
            webCachePath + File.separator + "success-" + commonEncodeUtf8ThenMd5Hex(url) // 自定义文件命名规则
        val cacheFile = File(cacheFilePath)
        //Logger.t(TAG)
        //    .d("cacheResourceRequest cacheFilePath=$cacheFilePath exists=${cacheFile.exists()}, isFile=${cacheFile.isFile}")
        if (!cacheFile.exists() || !cacheFile.isFile) { // 本地不存在 则开始下载
            // 下载文件
            val sourceFilePath = webCachePath + File.separator + commonEncodeUtf8ThenMd5Hex(url)
            val sourceFile = File(sourceFilePath)
            runBlocking {
                try {
                    //TODO 下载文件
                    /*val response: Response = if ("POST".equals(webRequest.method, true)) {
                        OkHttpUtils.post().headers(webRequest.requestHeaders).url(url).build()
                            .execute()
                    } else {
                        OkHttpUtils.get().headers(webRequest.requestHeaders).url(url).build()
                            .connTimeOut(15000L)
                            .readTimeOut(15000L)
                            .execute()
                    }
                    response.use {

                        it.body?.byteStream().use { inputStream ->
                            if (inputStream != null) {
                                sourceFile.writeBytes(inputStream.readBytes())
                            }
                        }
                    }*/
                    // 下载完成后增加 "success-" 前缀 代表文件无损 【防止io流被异常中断导致文件损坏 无法判断】
                    sourceFile.renameTo(cacheFile)
                } catch (e: Exception) {
                    Logger.t(TAG).e("url=$url \n" + Log.getStackTraceString(e))
                    // 发生异常删除文件
                    sourceFile.deleteOnExit()
                    cacheFile.deleteOnExit()
                }
            }
        }

        // 缓存文件存在则返回
        if (cacheFile.exists() && cacheFile.isFile) {
            val webResourceResponse =
                WebResourceResponse(mimeType, "UTF-8", cacheFile.inputStream())
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        }

        return null
    }


    private fun commonEncodeUtf8ThenMd5Hex(input: String): String {
        val encoded = URLEncoder.encode(input, "UTF-8")
        val md5Bytes = MessageDigest.getInstance("MD5").digest(encoded.toByteArray(Charsets.UTF_8))
        return md5Bytes.joinToString("") { "%02x".format(it) }
    }


    /**
     * 判断是否是图片
     * 有些文件存储没有后缀，也可以根据自家服务器域名等等
     */
    private fun isImageResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif"
                || extension == "jpeg" || extension == "jpg" || extension == "png"
                || extension == "svg" || extension == "webp"
    }


    private fun getWebCachePath(context: Context): String {
        val path = context.cacheDir.path + File.separator + "svg"
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }

    private fun loadSvg(fileUrl: String, objInfo: String) {
        //getWebView().evaluateJavascript("javascript:loadSvg('$fileUrl','$objInfo');", null)
    }


}