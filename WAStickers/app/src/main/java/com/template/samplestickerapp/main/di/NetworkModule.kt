package com.template.samplestickerapp.main.di


import android.content.Context
import com.template.samplestickerapp.main.data.remote.MoreAppService
import com.template.samplestickerapp.main.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .build()
    }


    @Singleton
    @Provides
    fun providesMoreAppRetrofitService(okHttpClient: OkHttpClient): MoreAppService {
        return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build().create(MoreAppService::class.java)
    }

    @Singleton
    @Provides
    fun providesNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }


}