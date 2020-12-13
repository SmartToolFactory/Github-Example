package com.smarttoolfactory.data.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.smarttoolfactory.data.api.GithubApi
import com.smarttoolfactory.data.constant.BASE_URL
import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val CLIENT_TIME_OUT = 120L
    }

    @Singleton
    @Provides
    fun provideGson() = Gson()


    @Singleton
    @Provides
    fun provideOkHttpClient(
        chuckInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val okHttpBuilder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
        okHttpBuilder
            .addInterceptor(chuckInterceptor)
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }


    @Singleton
    @Provides
    fun provideGithubApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): GithubApi {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
        return retrofitBuilder.build().create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChuckInterceptor(application: Application) = ChuckerInterceptor.Builder(application)
        .collector(ChuckerCollector(application))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()

}