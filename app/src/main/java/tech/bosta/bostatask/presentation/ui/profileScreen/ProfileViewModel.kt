package tech.bosta.bostatask.presentation.ui.profileScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.bosta.bostatask.domain.entities.AlbumResponse
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.domain.usecases.GetAlbumsCase
import tech.bosta.bostatask.domain.usecases.GetUsersCase
import tech.bosta.bostatask.domain.utils.NetworkResponseState
import tech.bosta.bostatask.presentation.utils.UIScreenState
import tech.bosta.bostatask.presentation.utils.ViewModelMVI


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUsersCase: GetUsersCase,
    private val getAlbumsCase: GetAlbumsCase
) : ViewModelMVI<ProfileEventUI>() {

    private  val TAG =this::class.java.name

    private val _stateUser: MutableStateFlow<UIScreenState<UsersResponseItem>?> = MutableStateFlow(UIScreenState())
    val stateUser: StateFlow<UIScreenState<UsersResponseItem>?> = _stateUser.asStateFlow()

    private val _stateAlbums: MutableStateFlow<UIScreenState<AlbumResponse>?> = MutableStateFlow(UIScreenState())
    val stateAlbums: StateFlow<UIScreenState<AlbumResponse>?> = _stateAlbums.asStateFlow()


    override fun onEvent(event: ProfileEventUI) {
        val exception = CoroutineExceptionHandler{_,throwable->
            Log.d(TAG, "Error Happened: ${throwable.message}")
        }
        when(event){
            is ProfileEventUI.RequestProfile -> {
                viewModelScope.launch(exception){
                    getAlbumsCase(event.userId).collect{state->
                        when(state){
                            is NetworkResponseState.OnError -> _stateAlbums.update {
                                it?.copy(error = state.error.message,loading = false)
                            }
                            is NetworkResponseState.OnLoading -> _stateAlbums.update {
                                it?.copy(error = null,loading = true)
                            }
                            is NetworkResponseState.OnNetworkError -> _stateAlbums.update {
                                it?.copy(error = state.data,loading = false)
                            }
                            is NetworkResponseState.OnSuccess ->  _stateAlbums.update {
                                it?.copy(error = null,loading = false, data = state.data)
                            }
                        }
                    }
                }
            }
            is ProfileEventUI.RequestRandomUser -> {
                viewModelScope.launch(exception){
                    getUsersCase().collect{state->
                        when(state){
                            is NetworkResponseState.OnError -> _stateUser.update {
                                it?.copy(error = state.error.message,loading = false)
                            }
                            is NetworkResponseState.OnLoading -> _stateUser.update {
                                it?.copy(error = null,loading = true)
                            }
                            is NetworkResponseState.OnNetworkError -> _stateUser.update {
                                it?.copy(error = state.data,loading = false)
                            }
                            is NetworkResponseState.OnSuccess ->  _stateUser.update {
                                val user = state.data?.random()
                                user?.id?.let { id-> onEvent(ProfileEventUI.RequestProfile(id)) }
                                it?.copy(error = null,loading = false, data = user)
                            }
                        }
                    }
                }
            }
        }
    }


}