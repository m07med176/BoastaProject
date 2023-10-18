package tech.bosta.bostatask.data

import tech.bosta.bostatask.data.source.remote.IRemoteDataSource
import tech.bosta.bostatask.domain.repository.IRepository
import retrofit2.Response
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.PhotoResponse
import tech.bosta.bostatask.domain.entities.UsersResponse

class RepositoryImpl(
    override val remote: IRemoteDataSource,
    ) : IRepository {
    override suspend fun getUsers(): Response<UsersResponse>
     = remote.getUsers()

    override suspend fun getAlbums(userId: Int): Response<AlbumResponse>
     = remote.getAlbums(userId)

    override suspend fun getPhotos(albumId: Int): Response<PhotoResponse>
     = remote.getPhotos(albumId)


}