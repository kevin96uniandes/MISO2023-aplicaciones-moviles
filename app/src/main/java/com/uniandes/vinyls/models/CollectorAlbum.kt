package com.uniandes.vinyls.models

data class CollectorAlbum(
    val id: Int,
    val price: Float,
    val status: CollectorAlbumStatus,
    val album: Album
){
    enum class CollectorAlbumStatus(status: String) {
        ACTIVE("Active"),
        INACTIVE("Inactive")
    }
}