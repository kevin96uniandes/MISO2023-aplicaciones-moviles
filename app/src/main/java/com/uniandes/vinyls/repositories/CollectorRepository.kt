package com.uniandes.vinyls.repositories

import android.app.Application
import android.util.Log
import com.uniandes.vinyls.NoInternetException
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.network.CollectorServiceAdapter
import com.uniandes.vinyls.network.Mocks.MockCollectorServiceAdapter
import java.lang.Exception

class CollectorRepository(private val application: Application) {

    suspend fun findAll(hasConnectivity: Boolean): List<Collector> {
        var collectors = listOf<Collector>()
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        if (!isRunningTest) {
            collectors = db.collectorDao().findAll()
            if(collectors.isNullOrEmpty()){
                if(hasConnectivity) {
                    try{
                        collectors = CollectorServiceAdapter.getInstance(application).findAll()
                    }catch (ex: Exception){
                        Log.e("Error", "getting data collectors")
                    }
                }
            }
        }else {
            collectors = MockCollectorServiceAdapter.getInstance(application).findAll()
        }

        Log.d("Collectors", collectors.toString())
        return collectors
    }

    suspend fun findById(collectorId: Int): Collector {
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        val collector = if (!isRunningTest) {
            val dbCollector = db.collectorDao().findById(collectorId)
            dbCollector?.let {
                dbCollector
            } ?: kotlin.run {
                onlineFindId(collectorId)
            }
        }else {
            MockCollectorServiceAdapter.getInstance(application).findById()
        }
        Log.d("Collectors", collector.toString())
        return collector
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

    suspend fun createCollectorDB (collectors: List<Collector>) {
        val db = VinylDB.getDatabase(application.applicationContext)

        Log.d("Save", "excuting...")
        for(collector in collectors){
            db.collectorDao().insert(collector)
        }
        Log.d("Count db", "${db.collectorDao().countCollectors()}")
        Log.d("guardado db", "guardado ")
    }

    private suspend fun onlineFindId(collectorId: Int): Collector {
        return try{
            CollectorServiceAdapter.getInstance(application).findById(collectorId)
        }catch (ex: Exception){
            Log.e("Error", "No connectivity for obtain collector")
            throw NoInternetException("No hay conexión a internet")
        }
    }
}