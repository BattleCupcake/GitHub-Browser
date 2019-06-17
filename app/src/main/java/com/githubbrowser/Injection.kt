package com.githubbrowser

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.githubbrowser.api.LoggingInterceptor
import com.githubbrowser.api.fb.FBService
import com.githubbrowser.api.github.GithubService
import com.githubbrowser.api.vk.VKService
import com.githubbrowser.database.AppDatabase
import com.githubbrowser.database.UserDao
import com.githubbrowser.repository.AuthRepository
import com.githubbrowser.repository.GithubRepository
import com.githubbrowser.repository.IGithubRepository
import com.githubbrowser.viewmodel.auth.AuthViewModelFactory
import com.githubbrowser.viewmodel.search.SearchViewModelFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

object Injection {

    fun provideSocketFactory() : SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, null, null)
        return sslContext.socketFactory
    }

    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            clientBuilder.sslSocketFactory(provideSocketFactory())

        clientBuilder.apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addInterceptor(LoggingInterceptor())
        }

        return clientBuilder.build()
    }

    fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .client(provideOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.GITHUB_ENDPOINT)
            .build()

    fun provideVKService() = VKService.instance

    fun provideFBService() = FBService.instance

    fun provideGithubService() = GithubService.instance

    fun provideAuthRepository(): AuthRepository {
        val vkService = provideVKService()
        val fbService = provideFBService()
        return AuthRepository(vkService, fbService)
    }

    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideUserDao(context: Context): UserDao {
        val database = provideDatabase(context)
        return database.userDao()
    }

    fun provideGithubRepository(context: Context): IGithubRepository {
        val service = provideGithubService()
        val dao = provideUserDao(context)
        return GithubRepository(service, dao)
    }

    fun provideAuthViewModelFactory(): ViewModelProvider.Factory {
        val repository = provideAuthRepository()
        return AuthViewModelFactory(repository)
    }

    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory {
        val githubRepository = provideGithubRepository(context)
        return SearchViewModelFactory(githubRepository)
    }
}