package com.template.samplestickerapp.main.data.model

import com.squareup.moshi.Json


data class MoreApps (
    @Json(name = "app_name")
    var app_name: String?,

    @Json(name = "app_desc")
    var app_desc: String?,

    @Json(name = "app_link")
    var app_link: String?,

    @Json(name = "app_image")
    var app_image: String?,

    @Json(name = "app_rating")
    var app_rating: Int?
)