package com.template.samplestickerapp.main.data

import com.template.samplestickerapp.main.data.remote.MoreAppService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MyRepository @Inject constructor(
    private val moreAppService: MoreAppService
) {

    suspend fun getMoreApps() = moreAppService.getMoreApps()

}