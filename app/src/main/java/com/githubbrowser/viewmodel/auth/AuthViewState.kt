package com.githubbrowser.viewmodel.auth

import com.githubbrowser.utils.Events
import com.githubbrowser.data.AuthData

class AuthViewState(val state: Events, val data: AuthData? = null, val error: Throwable? = null) {
    companion object {
        fun content(data: AuthData?) = AuthViewState(Events.CONTENT, data)
        fun loading() = AuthViewState(Events.LOADING)
        fun error(error: Throwable?) = AuthViewState(Events.ERROR, error = error)
    }
}