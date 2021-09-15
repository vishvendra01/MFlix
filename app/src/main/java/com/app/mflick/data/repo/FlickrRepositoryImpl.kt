package com.app.mflick.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.app.mflick.data.FlickrApi
import com.app.mflick.data.FlickrPagingSource
import com.app.mflick.model.Photo
import com.app.mflick.utils.RxScheduler
import io.reactivex.Flowable
import javax.inject.Inject

class FlickrRepositoryImpl @Inject constructor(
    private val flickrApi: FlickrApi,
    private val rxScheduler: RxScheduler
) :
    FlickrRepository {

    override fun getSearchResults(query: String): Flowable<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                prefetchDistance = 10,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { FlickrPagingSource(flickrApi, rxScheduler, query) }
        ).flowable
    }
}