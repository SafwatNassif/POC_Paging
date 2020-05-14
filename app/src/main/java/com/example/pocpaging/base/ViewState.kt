package com.example.pocpaging.base

import androidx.annotation.StringRes

sealed class ViewState {
    object LOADING : ViewState()
    object LOADED : ViewState()
    object EMPTY_LIST : ViewState()
    object NO_INTERNET : ViewState()
    object UN_EXPECTED : ViewState()
    object UNKNOWN : ViewState()

    open class Error(var remoteError: String? = null, @StringRes var localError: Int? = null):ViewState()

}