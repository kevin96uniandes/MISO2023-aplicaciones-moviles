package com.uniandes.vinyls.network.Mocks

import android.content.Context
import android.util.Log
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track
import com.uniandes.vinyls.network.AlbumServiceAdapter
import com.uniandes.vinyls.network.NetworkServiceAdapter
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MockAlbumServiceAdapter(context: Context): NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockAlbumServiceAdapter? = null

        fun getInstance(context: Context): MockAlbumServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockAlbumServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun getAlbumsMocks() : List<Album> {
        return listOf(Album(1, "Welcome to the jungle", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Rock", ""),
            Album(2, "Bethoveen", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Classic", ""),
            Album(2, "Mis colegas", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fakamai.sscdn.co%2Fuploadfile%2Fletras%2Falbuns%2Fe%2F6%2Fa%2F5%2F992341608319508.jpg&tbnid=SEVbZbQc_uJY3M&vet=12ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS..i&imgrefurl=https%3A%2F%2Fwww.letras.com%2Fguns-n-roses%2Fdiscografia%2Fwelcome-to-the-jungle-1987%2F&docid=XpbSVEZuyrsBFM&w=463&h=464&q=welcome%20to%20the%20jungle%20guns%20and%20roses&ved=2ahUKEwjStr2-36iCAxXJmIkEHam3ACwQMygCegQIARBS" ,"", "", "Salsa", ""))
    }

    suspend fun getAlbumById(id: Int): Album{

        var tracks: List<Track> = listOf(Track(1, "Welcome to the jungle", "04:25"),
            Track(2, "Ride the lightning", "03:25"),
            Track(3, "Symphony of destruction", "05:25"))

        var album: Album = Album(1,
            "Welcome to the jungle",
            "https://albumart.publicradio.org/mb/78/7822977a-984d-358d-823f-426139c0eaba_a4bc.jpg",
            "2023-03-11T00:00:00.000Z",
            "Welcome to the Jungle es una canción de hard rock de la banda estadounidense Guns N' Roses, lanzada como sencillo el 3 de octubre de 1987.",
            "rock",
            ""
            )

        album.tracks =tracks
        return album
    }
}