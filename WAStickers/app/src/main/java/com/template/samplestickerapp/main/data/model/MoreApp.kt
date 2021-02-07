package com.template.samplestickerapp.main.data.model

import com.squareup.moshi.Json
import com.template.samplestickerapp.main.data.model.App


data class MoreApp(
    @Json(name = "apps")
    val apps: List<App>?



)