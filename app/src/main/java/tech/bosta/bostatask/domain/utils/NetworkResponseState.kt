package tech.bosta.bostatask.domain.utils

sealed class NetworkResponseState<T> {
    class OnLoading<T>: NetworkResponseState<T>()
    class OnSuccess<T>(val data:T): NetworkResponseState<T>()
    class OnError<T>(val error:Throwable): NetworkResponseState<T>()
    class OnNetworkError<T>(val statusCode:Int,val data:String): NetworkResponseState<T>()
}