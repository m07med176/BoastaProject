package tech.bosta.bostatask.domain.repository

import tech.bosta.bostatask.data.source.remote.IRemoteDataSource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import tech.bosta.bostatask.domain.Urls
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.PhotoResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponse

interface IRepository {
    val remote: IRemoteDataSource


    suspend fun getUsers():Response<UsersResponse>

    suspend fun getAlbums(userId: Int):Response<AlbumResponse>

    suspend fun getPhotos(albumId:Int):Response<PhotoResponseItem>


}