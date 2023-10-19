package tech.bosta.bostatask.presentation.ui.albumscreen

sealed interface AlbumsEventUI{
    data class RequestPhotos(val albumId:Int):AlbumsEventUI
    data class RequestSearchPhoto(val title:String):AlbumsEventUI
}