package com.xxm.review.test

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import okhttp3.Dns

class NetTest {

    fun cleanDns(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    // 网络断开时主动清理 DNS
                    runCatching { Dns.SYSTEM.lookup("video.zoomtechina.com") }
                }
            }
        )
    }

    fun listenerDns(context: Context) {
        /*  val resolver = context.getSystemService<ConnectivityManager>()
              ?.activeNetwork
              ?.let { ConnectivityManager.NetworkCallback() }
          resolver?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
              override fun onCapabilitiesChanged(network: Network, nc: NetworkCapabilities) {
                  Log.d("DNS", "Current DNS: ${nc.linkProperties?.dnsServers}")
              }
          })*/

        Intent.ACTION_BOOT_COMPLETED
    }
}