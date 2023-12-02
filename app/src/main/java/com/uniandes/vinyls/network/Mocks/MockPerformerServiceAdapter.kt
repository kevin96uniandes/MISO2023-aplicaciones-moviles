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
        return listOf(Performer(1,"Taylor Alison Swift","https://es.wikipedia.org/wiki/Archivo:Taylor_Swift_at_the_2023_MTV_Video_Music_Awards_(1).png",
                "es una cantautora, productora, directora, actriz y empresaria estadounidense.","1989-12-13T00:00:00.000Z"),
            Performer(2,"Aubrey Drake Graham","https://es.wikipedia.org/wiki/Drake_(m%C3%BAsico)#/media/Archivo:Drake_en_2021_con_su_peinado_de_la_epoca.jpg",
                "conocido simplemente como Drake, es un rapero, cantante, compositor, productor discográfico y actor canadiense.","1986-10-24T00:00:00.000Z"),
            Performer(3,"Abel Makkonen Tesfaye","https://es.wikipedia.org/wiki/The_Weeknd#/media/Archivo:The_Weeknd_Portrait_by_Brian_Ziff.jpg",
                "conocido artísticamente como The Weeknd, es un cantante, rapero, compositor y productor discográfico canadiense.","1990-02-16T00:00:00.000Z")
        )
    }

    suspend fun getPerformerById(id: Int): Performer {

        var performer: Performer = Performer(1,
            "Taylor Alison Swift",
            "https://es.wikipedia.org/wiki/Archivo:Taylor_Swift_at_the_2023_MTV_Video_Music_Awards_(1).png",
            "es una cantautora, productora, directora, actriz y empresaria estadounidense.",
            "1989-12-13T00:00:00.000Z"
        )

        return performer
    }
}