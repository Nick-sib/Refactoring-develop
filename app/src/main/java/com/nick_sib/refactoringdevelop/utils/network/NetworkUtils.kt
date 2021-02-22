package com.nick_sib.refactoringdevelop.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

fun isOnlineFlow(context: Context): ReceiveChannel<Boolean> =
    Channel<Boolean>(Channel.CONFLATED).apply {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        offer(connectivityManager.isDefaultNetworkActive)
        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                offer(true)
            }
            override fun onUnavailable() {
                offer(false)
            }
            override fun onLost(network: Network) {
                 offer(false)
            }
    })
}


