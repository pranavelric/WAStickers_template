package com.template.samplestickerapp.main.data.model

import com.squareup.moshi.Json

data class App(
        @Json(name = "name")
    val name: String,
        @Json(name = "more_apps")
    val more_apps: List<MoreApps>?,
        @Json(name = "advertisement")
    val advertisement: Advertisement
)