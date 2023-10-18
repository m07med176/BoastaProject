package tech.bosta.bostatask.data.source.remote

import retrofit2.Response
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.PhotoResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponse

interface IRemoteDataSource{

    suspend fun getUsers():Response<UsersResponse>

    suspend fun getAlbums(userId: Int):Response<AlbumResponse>

    suspend fun getPhotos(albumId:Int):Response<PhotoResponseItem>

}