package com.app.mflick.data

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.app.mflick.model.Photo
import com.app.mflick.utils.RxScheduler
import io.reactivex.Single

class FlickrPagingSource constructor(
    private val flickrApi: FlickrApi,
    private val rxScheduler: RxScheduler,
    private val query : String
) :
    RxPagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Photo>> {
        val page = params.key ?: 1

        return flickrApi.fetchImages(query, params.key ?: 1)
            .subscribeOn(rxScheduler.io())
            .map {
                LoadResult.Page(
                    data = it.photos?.photo ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == it.photos?.pages ?: -1) null else page + 1
                )
            }
    }
}