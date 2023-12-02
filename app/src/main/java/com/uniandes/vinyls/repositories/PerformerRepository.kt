package com.uniandes.vinyls.repositories

import android.app.Application
import android.util.Log
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.network.Mocks.MockPerformerServiceAdapter
import com.uniandes.vinyls.network.PerformerServiceAdapter

class PerformerRepository(private val application: Application) {
    suspend fun findAll(hasConnectivity: Boolean): List<Performer> {
        var performers = listOf<Performer>()
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        if (!isRunningTest) {
            performers = db.performerDao().findAll()
            if(performers.isNullOrEmpty()){
                if(hasConnectivity) {
                    try{
                        performers = PerformerServiceAdapter.getInstance(application).findAll()
                    }catch (ex: Exception){
                        Log.e("Error", "getting data performers")
                    }
                }
            }
        }else {
            performers = MockPerformerServiceAdapter.getInstance(application).findAll()
        }

        Log.d("Performers", performers.toString())
        return performers
    }

    suspend fun obtenerPerformerPorId(id: Int): Performer {

        var performer: Performer?
        val isRunningTest = validarTest()
        if (!isRunningTest){
            val db = VinylDB.getDatabase(application.applicationContext)
            performer = db.performerDao().getPerformerById(id)
            if(performer != null){
                return performer
            }else{
                performer = PerformerServiceAdapter.getInstance(application).getPerformerById(id)
                return performer

            }
        }else {
            performer = MockPerformerServiceAdapter.getInstance(application).getPerformerById(1)
            return performer
        }
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
    suspend fun createPerformerDB (performers: List<Performer>) {
        val db = VinylDB.getDatabase(application.applicationContext)

        Log.d("Save", "excuting...")
        for(performer in performers){
            db.performerDao().insert(performer)
        }
        Log.d("Count db", "${db.performerDao().countPerformers()}")
        Log.d("guardado db", "guardado ")
    }
}