package com.githubbrowser.repository

import com.githubbrowser.database.User
import io.reactivex.Single

interface IGithubRepository {
    fun getUsers(since: Int = 0): Single<List<User>>

    fun getUsersWithFilter(filter: String?): Single<List<User>>
}