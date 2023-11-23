package com.uniandes.vinyls.network.Mocks

import android.content.Context
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockPerformerServiceAdapter (context: Context) : NetworkServiceAdapter(context) {
    companion object {
        @Volatile
        private var instance: MockPerformerServiceAdapter? = null

        fun getInstance(context: Context): MockPerformerServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockPerformerServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<Performer> {
        return listOf()
    }

    suspend fun getPerformerById(id: Int): Performer {

        var performer: Performer = Performer(1,
            "Adele",
            "https://albumart.publicradio.org/mb/78/7822977a-984d-358d-823f-426139c0eaba_a4bc.jpg",
            "Adele Laurie Blue Adkins, conocida simplemente como Adele, es una cantautora y multinstrumentista británica.",
            "2023-03-11T00:00:00.000Z"
        )

        return performer
    }
}