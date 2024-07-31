package com.xxm.review.interceptor

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 1.自定义Interceptor，为需要验证token的接口添加header。
 * 2.获取响应，判断token是否过期。
 * 3.如果token过期，发起请求刷新token。如果多个需要验证token的接口并发执行，应该仅执行一次刷新token的操作。
 * 4.刷新token后，携带新token重新发送原请求获取响应。
 * */
class ApiInterceptor : Interceptor {

    // 需要验证token的接口的路径集合
    private val checkTokenInterface = arrayListOf(
            "/example/user/info",
            "/example/user/info/edit"
    )

    // 是否正在刷新token
    // 使用AtomicBoolean，确保多线程中获取的值一致
    private var refreshingToken = AtomicBoolean(false)

    private val authHeaderKey = "token-key"

    // 当前token
    private var currentToken = "tokenValue"

    override fun intercept(chain: Interceptor.Chain): Response {
        // 根据接口路径判断是否需要验证token

        val checkToken = checkTokenInterface.contains(chain.request().url().encodedPath())
        // 需要验证token则添加请求头，不需要则保持原样
        var request = if (checkToken) {
            chain.request().newBuilder()
                    .addHeader(authHeaderKey, currentToken)
                    .build()
        } else {
            chain.request()
        }
        // 获取响应
        var response = chain.proceed(request)
        if (checkToken) {
            // 需要验证token，处理响应，判断token是否过期
            response.body()?.let { responseBody ->
                try {
                    //判断token过期的条件
                    if (isTokenExpires()) {
                        runBlocking {
                            // 请求头中的token与currentToken仍然一样则需要刷新token
                            // 确保多线程中不会重复刷新
                            if (request.header(authHeaderKey) == currentToken) {
                                // 标记为正在刷新token
                                if (refreshingToken.compareAndSet(false, true)) {
                                    refreshToken(chain).takeIf { it.isNotEmpty() && it.isNotBlank() }?.let { newToken ->
                                        // 保存newToken
                                        currentToken = newToken
                                    }
                                    // 标记为刷新token已结束
                                    refreshingToken.set(false)
                                }
                            }
                            // async{}.await()会等待方法块内的代码执行完毕。
                            // 等到refreshingToken为false时才会执行后续代码。
                            if (async { stopRefreshingToken() }.await()) {
                                // 携带newToken执行原请求获取响应
                                response = chain.proceed(chain.request().newBuilder()
                                        .addHeader(authHeaderKey, currentToken)
                                        .build())
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        // 返回最终的响应结果。
        // 刷新token也可能失败，此时返回原响应，可以让用户重新登录。
        return response
    }

    //判断token过期的条件
    private fun isTokenExpires(): Boolean {
        //判断token过期的条件
        return true
    }

    private fun refreshToken(chain: Interceptor.Chain): String {
        // 创建刷新token的请求
        val refreshTokenRequest = Request.Builder()
                .url("refresh token url")
                .addHeader(authHeaderKey, currentToken)
                .get()
                .build()
        return try {
            // 获取响应并解析获得新token
            val refreshTokenResponse = chain.proceed(refreshTokenRequest)
            "token" //refreshTokenResponse.token
        } catch (e: IOException) {
            // 失败时返回空字符串
            ""
        }
    }

    private suspend fun stopRefreshingToken(): Boolean {
        return if (refreshingToken.get()) {
            // 仍在刷新token中，延迟1秒后再次执行此方法
            delay(1000)
            stopRefreshingToken()
        } else {
            true
        }
    }
}
