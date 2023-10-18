package tech.bosta.bostatask.domain.usecases

import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.nullable
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import tech.bosta.bostatask.domain.entities.PhotoResponse
import tech.bosta.bostatask.domain.entities.UsersResponse
import tech.bosta.bostatask.domain.repository.IRepository
import tech.bosta.bostatask.domain.utils.NetworkResponseState

class GetPhotosCaseTest{

    @Mock
    private lateinit var repository: IRepository

    private lateinit var usecase: IGetPhotosCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usecase = GetPhotosCase(repository)
    }

    @Test
    fun `get Albums successfully`()  = runBlocking{
        // Given
        val albumId = 2
        val expectedUserResponse = Response.success(PhotoResponse(
            albumId = albumId,
            id = 5,
            thumbnailUrl = "",
            title = "",
            url = ""
        ))

        // When
        `when`(repository.getPhotos(albumId)).thenReturn(expectedUserResponse)
        val result = usecase(albumId)

        // Then
        assert(result.last() is NetworkResponseState.OnSuccess)
    }

    @Test
    fun `get Albums failure`() = runBlocking{
        // Given
        val errorMessage = "User not found"
        val albumId = 2

        // When
        `when`(repository.getPhotos(albumId)).thenThrow(RuntimeException(errorMessage))
        val result = usecase(albumId)

        // Then
        assert(result.last() is NetworkResponseState.OnError)
    }

    @Test
    fun `get Albums failure network`()  = runBlocking{
        // Given
        val albumId = 2
        val expectedUserResponse = Response.error<UsersResponse>(500, nullable(ResponseBody::class.java))

        // When
        `when`(repository.getUsers()).thenReturn(expectedUserResponse)
        val result = usecase(albumId)

        // Then
        assert(result.last() is NetworkResponseState.OnNetworkError)
    }

}