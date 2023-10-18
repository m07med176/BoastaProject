package tech.bosta.bostatask.data.source.remote

import tech.bosta.bostatask.data.source.remote.retrofit.CallApi
import retrofit2.Response
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.PhotoResponse
import tech.bosta.bostatask.domain.entities.UsersResponse

class RemoteDataSource(
    private val api: CallApi
) : IRemoteDataSource {
    override suspend fun getUsers(): Response<UsersResponse> = api.getUsers()
    override suspend fun getAlbums(userId: Int): Response<AlbumResponse> = api.getAlbums(userId)
    override suspend fun getPhotos(albumId: Int): Response<PhotoResponse> = api.getPhotos(albumId)


}