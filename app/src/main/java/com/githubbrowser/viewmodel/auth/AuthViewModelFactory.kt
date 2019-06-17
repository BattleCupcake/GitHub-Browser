package com.githubbrowser.viewmodel.auth

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.githubbrowser.repository.AuthRepository

class AuthViewModelFactory(private val repository: AuthRepository)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}