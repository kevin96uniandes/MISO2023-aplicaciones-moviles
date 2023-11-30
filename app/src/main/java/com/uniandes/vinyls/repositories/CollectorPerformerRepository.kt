package com.uniandes.vinyls.repositories

import android.app.Application
import android.util.Log
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.CollectorPerformer
import com.uniandes.vinyls.network.CollectorPerformerServiceAdapter
import com.uniandes.vinyls.network.Mocks.MockCollectorPerformerServiceAdapter
import java.lang.Exception

class CollectorPerformerRepository (private val application: Application) {

    suspend fun findAll(collectorId: Int, hasConnectivity: Boolean): List<CollectorPerformer> {
        var collectors = listOf<CollectorPerformer>()
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        if (!isRunningTest) {
            collectors = db.collectorPerformerDao().findAll(collectorId)
            if(collectors.isNullOrEmpty()){
                if(hasConnectivity) {
                    try{
                        collectors = CollectorPerformerServiceAdapter.getInstance(application).findAll(collectorId)
                    }catch (ex: Exception){
                        Log.e("Error", "getting data collectors")
                    }
                }
            }
        }else {
            collectors = MockCollectorPerformerServiceAdapter.getInstance(application).findAll()
        }

        Log.d("Collectors", collectors.toString())
        return collectors.map { it.copy(collectorId = collectorId) }
    }

    suspend fun createCollectorPerformerDB(collectorPerformers: List<CollectorPerformer>) {
        val db = VinylDB.getDatabase(application.applicationContext)
        Log.d("Save", "excuting collector album...")
        for(collector in collectorPerformers){
            db.collectorPerformerDao().insert(collector)
        }
        Log.d("Count db", "${db.collectorDao().countCollectors()}")
        Log.d("guardado db", "guardado ")
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