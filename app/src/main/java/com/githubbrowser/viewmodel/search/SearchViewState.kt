package com.githubbrowser.viewmodel.search

import com.githubbrowser.database.User
import com.githubbrowser.utils.EventsSearchState

data class SearchViewState constructor(val state: EventsSearchState,
                                       val data: List<User>? = null,
                                       val error: Throwable? = null,
                                       val reload: Boolean = false) {
    companion object {
        fun initialLoading() = SearchViewState(EventsSearchState.INITIAL_LOADING)
        fun loadingNextPage() = SearchViewState(EventsSearchState.LOADING_NEXT_PAGE)
        fun loading() = SearchViewState(EventsSearchState.LOADING)
        fun nextPageLoaded() = SearchViewState(EventsSearchState.NEXT_PAGE_LOADED)
        fun hideLoading() = SearchViewState(EventsSearchState.HIDE_LOADING)
        fun content(data: List<User>?, reload: Boolean = false) = SearchViewState(EventsSearchState.CONTENT, data, reload = reload)
        fun error(error: Throwable?) = SearchViewState(EventsSearchState.ERROR, error = error)
        fun errorNextPageLoading() = SearchViewState(EventsSearchState.ERROR_NEXT_PAGE_LOADING)
    }
}