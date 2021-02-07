package com.template.samplestickerapp.main.data.model

import com.squareup.moshi.Json


data class Advertisement(
    @Json(name = "ad_image")
    var ad_image: String?,

    @Json(name = "ad_link")
    var ad_link: String?
)