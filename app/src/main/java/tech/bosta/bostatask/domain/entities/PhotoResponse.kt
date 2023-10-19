package tech.bosta.bostatask.domain.entities

import java.io.Serializable


class PhotoResponse : ArrayList<PhotoResponseItem>()
data class PhotoResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
): Serializable