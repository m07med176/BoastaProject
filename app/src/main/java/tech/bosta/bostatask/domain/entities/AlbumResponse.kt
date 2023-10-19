package tech.bosta.bostatask.domain.entities

import java.io.Serializable

class AlbumResponse : ArrayList<AlbumResponseItem>()
data class AlbumResponseItem(
    val id: Int,
    val title: String,
    val userId: Int
): Serializable