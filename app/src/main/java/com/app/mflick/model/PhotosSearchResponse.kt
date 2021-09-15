package com.app.mflick.model

data class PhotosSearchResponse(
    val photos: Photos?,
    val stat: String,
    val code: String?,
    val message: String?
)