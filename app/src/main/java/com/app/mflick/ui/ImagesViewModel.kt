package com.app.mflick.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.app.mflick.model.Photo
import com.app.mflick.data.repo.FlickrRepository
import com.app.mflick.utils.RxScheduler
import com.app.mflick.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    val repository: FlickrRepository,
    val rxScheduler: RxScheduler
) :
    ViewModel() {
    private val disposeBag = CompositeDisposable()
    val imagesLiveData = MutableLiveData<State<PagingData<Photo>>>()

    fun searchImages(query: String = "dogs") {
        imagesLiveData.value = State.loading()

        repository.getSearchResults(query)
            .cachedIn(viewModelScope)
            .observeOn(rxScheduler.ui())
            .subscribe({
                imagesLiveData.value = State.success(it)
            }, {
                imagesLiveData.value = State.error(it.message, null)
            })
            .run { disposeBag.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

}