package com.andalus.lifeachievements.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkStateTracer(cm: ConnectivityManager) {

    var isConnected = false

    init {
        val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.requestNetwork(networkRequest, object : ConnectivityManager.NetworkCallback() {

            override fun onUnavailable() {
                super.onUnavailable()
                Log.d("NETWORK", "unavailable")
                isConnected = false
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("NETWORK", "available")
                isConnected = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("NETWORK", "lost")
                isConnected = true
            }
        })
    }

}