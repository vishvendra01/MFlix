package com.app.mflick.utils

import com.app.mflick.model.Photo

fun buildImageUrlFromPhoto(photo: Photo): String {
    return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
}