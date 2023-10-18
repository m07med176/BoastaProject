package tech.bosta.bostatask.domain.usecases

import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.bouncycastle.asn1.ocsp.ResponseData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.nullable
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import tech.bosta.bostatask.domain.entities.Address
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponse
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.domain.repository.IRepository
import tech.bosta.bostatask.domain.utils.NetworkResponseState

class GetAlbumsCaseTest{

    @Mock
    private lateinit var repository: IRepository

    private lateinit var usecase: IGetAlbumsCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usecase = GetAlbumsCase(repository)
    }

    @Test
    fun `get Albums successfully`()  = runBlocking{
        // Given
        val userId = 2
        val expectedUserResponse = Response.success(AlbumResponse())

        // When
        `when`(repository.getAlbums(userId)).thenReturn(expectedUserResponse)
        val result = usecase(userId)

        // Then
        assert(result.last() is NetworkResponseState.OnSuccess)
    }

    @Test
    fun `get Albums failure`() = runBlocking{
        // Given
        val errorMessage = "User not found"
        val userId = 2

        // When
        `when`(repository.getAlbums(userId)).thenThrow(RuntimeException(errorMessage))
        val result = usecase(userId)

        // Then
        assert(result.last() is NetworkResponseState.OnError)
    }

    @Test
    fun `get Albums failure network`()  = runBlocking{
        // Given
        val userId = 2
        val expectedUserResponse = Response.error<UsersResponse>(500, nullable(ResponseBody::class.java))

        // When
        `when`(repository.getUsers()).thenReturn(expectedUserResponse)
        val result = usecase(userId)

        // Then
        assert(result.last() is NetworkResponseState.OnNetworkError)
    }

}