package com.app.mflick.data

import com.app.mflick.BuildConfig
import com.app.mflick.model.PhotosSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET(FETCH_IMAGE_URL)
    fun fetchImages(
        @Query("tags") query: String,
        @Query("page") page: Int
    ): Single<PhotosSearchResponse>

    companion object {
        private const val API_KEY = BuildConfig.UNSPLASH_API_KEY
        private const val FETCH_IMAGE_URL =
            "services/rest?method=flickr.photos.search&api_key=$API_KEY&format=json&nojsoncallback=1&per_page=20"
    }
}