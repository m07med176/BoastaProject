package tech.bosta.bostatask.presentation.utils

data class UIScreenState<T>(val error:String?=null,val loading:Boolean = false,val data:T?=null)
