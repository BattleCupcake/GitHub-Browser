package com.githubbrowser.viewmodel.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.githubbrowser.repository.IGithubRepository

class SearchViewModelFactory(private val githubRepo: IGithubRepository)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(githubRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}