package com.uniandes.vinyls.network.Mocks

import android.app.Application
import android.content.Context
import android.os.Parcel
import androidx.lifecycle.AndroidViewModel
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockCollectorServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockCollectorServiceAdapter? = null

        fun getInstance(context: Context): MockCollectorServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockCollectorServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<Collector> {
        return listOf()
    }

    fun findById(): Collector {
        val albums =  listOf(
            CollectorAlbum(
                id = 1,
                price = 123.0,
                album = Album(1, "Welcome to the jungle", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Rock", ""),
                collectorId = 2
            ),
            CollectorAlbum(
                id = 2,
                price = 1234.0,
                album = Album(2, "Bethoveen", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Classic", ""),
                collectorId = 3
            ),
            CollectorAlbum(
                id = 3,
                price = 12345.0,
                album = Album(2, "Mis colegas", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Salsa", ""),
                collectorId = 4
            ),
        )

        return Collector(
            collectorId = 1,
            albums = albums,
            name = "Juanito",
            telephone = "123",
            email = "j@j.com.co"
        )
    }
}