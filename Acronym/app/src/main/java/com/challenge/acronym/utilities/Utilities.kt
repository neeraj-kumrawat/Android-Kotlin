package com.challenge.acronym.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Utilities {
    companion object {
        const val BUNDLE_KEY = "acronym"

        fun isInternetAvailable(context: Context): Boolean {
            // register activity with the connectivity manager service
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Returns a Network object corresponding to the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        }
    }
}