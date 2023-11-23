package com.uniandes.vinyls.repositories

import android.app.Application
import android.util.Log
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.network.CollectorArtistServiceAdapter
import com.uniandes.vinyls.network.Mocks.MockCollectorArtistServiceAdapter
import java.lang.Exception

class CollectorArtistRepository (private val application: Application) {

    suspend fun findAll(collectorId: Int, hasConnectivity: Boolean): List<CollectorArtist> {
        var collectors = listOf<CollectorArtist>()
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        if (!isRunningTest) {
            //collectors = db.collectorDao().findAll()
            if(collectors.isNullOrEmpty()){
                if(hasConnectivity) {
                    try{
                        collectors = CollectorArtistServiceAdapter.getInstance(application).findAll(collectorId)
                    }catch (ex: Exception){
                        Log.e("Error", "getting data collectors")
                    }
                }
            }
        }else {
            collectors = MockCollectorArtistServiceAdapter.getInstance(application).findAll()
        }

        Log.d("Collectors", collectors.toString())
        return collectors
    }

    fun validarTest(): Boolean {

        val isRunningTest : Boolean by lazy {
            try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        return isRunningTest
    }
}