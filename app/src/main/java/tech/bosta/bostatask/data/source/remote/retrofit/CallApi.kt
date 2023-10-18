package tech.bosta.bostatask.data.source.remote.retrofit


import tech.bosta.bostatask.domain.Urls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.PhotoResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponse

interface CallApi {

    @GET(Urls.Users)
    suspend fun getUsers():Response<UsersResponse>

    @GET(Urls.Albums)
    suspend fun getAlbums(
        @Query("userId") userId: Int,
    ):Response<AlbumResponse>

    @GET(Urls.Photos)
    suspend fun getPhotos(
        @Path("albumId") albumId:Int
    ):Response<PhotoResponseItem>


}