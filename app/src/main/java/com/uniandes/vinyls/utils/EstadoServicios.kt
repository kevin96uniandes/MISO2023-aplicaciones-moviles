package com.uniandes.vinyls.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class EstadoServicios {

    fun validarConexionIntenet(context:Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetwork = connectivityManager?.activeNetwork
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)

            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }


}