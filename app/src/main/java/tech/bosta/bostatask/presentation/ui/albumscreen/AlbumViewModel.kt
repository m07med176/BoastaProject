package tech.bosta.bostatask.presentation.ui.albumscreen

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
import tech.bosta.bostatask.domain.entities.PhotoResponse
import tech.bosta.bostatask.domain.usecases.GetPhotosCase
import tech.bosta.bostatask.domain.utils.NetworkResponseState
import tech.bosta.bostatask.presentation.utils.UIScreenState
import tech.bosta.bostatask.presentation.utils.ViewModelMVI

@HiltViewModel
class AlbumViewModel @Inject constructor(val photosCase: GetPhotosCase) : ViewModelMVI<AlbumsEventUI>() {

    private val TAG = this::class.java.name

    private val _statePhotos: MutableStateFlow<UIScreenState<PhotoResponse>?> = MutableStateFlow(UIScreenState())
    val statePhotos: StateFlow<UIScreenState<PhotoResponse>?> = _statePhotos.asStateFlow()

    override fun onEvent(event: AlbumsEventUI) {
        val exception = CoroutineExceptionHandler{ _ ,throwable->
            Log.d(TAG, "Error Happened: ${throwable.message}")
        }
        when(event){
            is AlbumsEventUI.RequestPhotos -> {
                viewModelScope.launch(exception){
                    photosCase(event.albumId).collect{state->
                        when(state){
                            is NetworkResponseState.OnError -> _statePhotos.update {
                                it?.copy(error = state.error.message,loading = false)
                            }
                            is NetworkResponseState.OnLoading -> _statePhotos.update {
                                it?.copy(error = null,loading = true)
                            }
                            is NetworkResponseState.OnNetworkError -> _statePhotos.update {
                                it?.copy(error = state.data,loading = false)
                            }
                            is NetworkResponseState.OnSuccess ->  _statePhotos.update {
                                it?.copy(error = null,loading = false, data = state.data)
                            }
                        }
                    }
                }
            }
        }
    }
}