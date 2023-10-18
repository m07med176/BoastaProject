package tech.bosta.bostatask.presentation.utils

import androidx.lifecycle.ViewModel


abstract class ViewModelMVI<Event>(): ViewModel() {
    abstract fun  onEvent(event: Event)
}