package com.template.samplestickerapp.main.data.remote

import com.template.samplestickerapp.main.data.model.MoreApp
import retrofit2.Response
import retrofit2.http.GET

interface MoreAppService {

    @GET("Professional-Cipher-Apps/Android-Apps-Playstore/main/apps_json.json")
    suspend fun getMoreApps(): Response<MoreApp>


}