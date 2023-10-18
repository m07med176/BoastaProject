package tech.bosta.bostatask.domain.entities

class AlbumResponse : ArrayList<AlbumResponseItem>()
data class AlbumResponseItem(
    val id: Int,
    val title: String,
    val userId: Int
)