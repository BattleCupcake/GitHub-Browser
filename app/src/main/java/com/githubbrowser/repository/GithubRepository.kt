package com.githubbrowser.repository

import android.arch.persistence.room.EmptyResultSetException
import com.githubbrowser.api.github.GithubService
import com.githubbrowser.database.User
import com.githubbrowser.database.UserDao
import io.reactivex.Single

class GithubRepository(private val service: GithubService, private val userDao: UserDao)
    : IGithubRepository {

    override fun getUsers(since: Int): Single<List<User>> {

        return userDao.getUsers(since)
                .flatMap { users ->
                    if (users.isNotEmpty()) {
                        Single.just(users)
                    } else {
                        Single.error(EmptyResultSetException("Data is empty"))
                    }
                }
                .onErrorResumeNext {
                    service.getUsers(since)
                }
                .doOnSuccess { userDao.insert(it) }
    }

    override fun getUsersWithFilter(filter: String?): Single<List<User>> {
        return service.getUserByLogin(filter ?: "")
                .doOnSuccess { userDao.insert(it) }
                .map { arrayListOf(it) }
                .onErrorResumeNext(Single.just(arrayListOf()))
                .flatMap {
                    userDao.getUsersWithFilter(filter)
                }
                .onErrorReturn { arrayListOf() }
    }
}