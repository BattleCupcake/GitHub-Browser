package com.githubbrowser.api.fb

import com.githubbrowser.BuildConfig
import com.githubbrowser.Injection
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FBService {

    @GET("${BuildConfig.FB_ENDPOINT}me")
    fun me(@Query("fields") fields: String,
           @Query("access_token") accessToken: String): Single<FBMeResponse>

    companion object {
        val instance: FBService by lazy {
            val retrofit = Injection.provideRetrofit()
            retrofit.create(FBService::class.java)
        }
    }
}