package tech.bosta.bostatask.domain.usecases

import tech.bosta.bostatask.domain.repository.IRepository
import tech.bosta.bostatask.domain.utils.NetworkResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import tech.bosta.bostatask.domain.entities.UsersResponse
import javax.inject.Inject

interface IGetUsersCase {
    operator fun invoke(): Flow<NetworkResponseState<UsersResponse?>>
}

class GetUsersCase @Inject constructor(private val repository: IRepository) : IGetUsersCase {

    override operator fun invoke() = flow {
        val response = coroutineScope{
            val response = async(Dispatchers.IO){ repository.getUsers() }
            emit(NetworkResponseState.OnLoading<UsersResponse?>())
            response.await()
        }

        if (response.isSuccessful) {
            emit(NetworkResponseState.OnSuccess(response.body()))
        } else {
            emit(
                NetworkResponseState.OnNetworkError(
                    statusCode = response.code(),
                    data = response.errorBody().toString()
                )
            )
        }
    }.catch { emit(NetworkResponseState.OnError(it)) }
}