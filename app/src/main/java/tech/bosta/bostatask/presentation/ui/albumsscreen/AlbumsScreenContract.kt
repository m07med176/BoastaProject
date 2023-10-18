package tech.bosta.bostatask.presentation.ui.albumsscreen





data class AlbumsScreenActions(
    val onClick: (() -> Unit)?=null
)

sealed interface AlbumsEventUI{
    object RequestRandomUser:AlbumsEventUI
    data class RequestAlbums(val userId:Int):AlbumsEventUI
}