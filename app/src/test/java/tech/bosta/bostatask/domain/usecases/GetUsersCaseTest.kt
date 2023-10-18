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
import tech.bosta.bostatask.domain.entities.UsersResponse
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.domain.repository.IRepository
import tech.bosta.bostatask.domain.utils.NetworkResponseState

class GetUsersCaseTest{

    @Mock
    private lateinit var repository: IRepository

    private lateinit var getUsersCase: IGetUsersCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUsersCase = GetUsersCase(repository)
    }

    @Test
    fun `get user successfully`()  = runBlocking{

        val expectedUserResponse = Response.success(UsersResponse())

        `when`(repository.getUsers()).thenReturn(expectedUserResponse)

        val result = getUsersCase()

        assert(result.last() is NetworkResponseState.OnSuccess)
    }
    @Test
    fun `get user failure`() = runBlocking{
        val errorMessage = "User not found"

        `when`(repository.getUsers()).thenThrow(RuntimeException(errorMessage))

        val result = getUsersCase()

        assert(result.last() is NetworkResponseState.OnError)
    }

    @Test
    fun `get user failure network`()  = runBlocking{

        val expectedUserResponse = Response.error<UsersResponse>(500, nullable(ResponseBody::class.java))

        `when`(repository.getUsers()).thenReturn(expectedUserResponse)

        val result = getUsersCase()

        assert(result.last() is NetworkResponseState.OnNetworkError)
    }

}