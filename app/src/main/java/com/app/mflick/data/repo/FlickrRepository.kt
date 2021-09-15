package com.app.mflick.data.repo

import androidx.paging.PagingData
import com.app.mflick.model.Photo
import io.reactivex.Flowable

interface FlickrRepository {

    fun getSearchResults(query : String): Flowable<PagingData<Photo>>
}