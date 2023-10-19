package tech.bosta.bostatask.data.source.remote.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CallApiTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var callApi: CallApi


    @Before
    fun setUp() {
        callApi = RetrofitInstance().api

    }



    @Test
    fun `When call GetUsers Then return list of users and not equal zero`()  = runBlocking {
        // When
        val result = callApi.getUsers()

        // Then
        if (result.isSuccessful){
            assertThat(result.body(),`is`(result.body()))
            assertNotEquals(result.body()?.size,0)
        }
    }

    @Test
    fun `Given get id of user When call getAlbums Then return album of user`()  = runBlocking {
        // Given
        val userId = callApi.getUsers().body()?.random()?.id

        // When
        val result = userId?.let{callApi.getAlbums(it)}

        // Then
        if (result != null && result.isSuccessful) {
                assertThat(result.body(),`is`(result.body()))
            }

    }

    @Test
    fun `Given UserId and AlbumId When call getPhotos Then return Item of photos`() = runBlocking {
        // Given
        val userId = callApi.getUsers().body()?.random()?.id
        val albumId = userId?.let{callApi.getAlbums(it).body()?.random()?.id}

        // When
        val result = albumId?.let{callApi.getPhotos(it)}

        // Then
        if (result != null && result.isSuccessful) {
            assertThat(result.body(),`is`(result.body()))
        }

    }

}