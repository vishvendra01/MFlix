package com.app.mflick.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mflick.R
import com.app.mflick.databinding.ActivityMainBinding
import com.app.mflick.model.Photo
import com.app.mflick.utils.RxScheduler
import com.app.mflick.utils.State
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rxScheduler: RxScheduler

    private val imagesViewModel: ImagesViewModel by viewModels()

    private lateinit var viewBinding: ActivityMainBinding
    private val searchSubject = PublishSubject.create<String>()

    private val imagesAdapter = FlickrImagesAdapter()
    private var searchQuery: String = "sample" // by default show some images at first

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupImagesRecyclerView()
        setToolbarAndSearchView()
        observeSearchQueries()

        bindViews()
        imagesViewModel.searchImages(searchQuery)
    }

    private fun setToolbarAndSearchView() {
        setSupportActionBar(viewBinding.toolBar)
    }

    private fun setupImagesRecyclerView() {
        viewBinding.imagesRecyclerView.adapter =
            imagesAdapter.withLoadStateFooter(ProgressLoadAdapter())
        viewBinding.imagesRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun observeSearchQueries() {
        searchSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .subscribeOn(rxScheduler.io())
            .observeOn(rxScheduler.ui())
            .subscribe({
                searchQuery = it
                imagesViewModel.searchImages(query = it)
            }, {

            }).run { disposeBag.add(this) }
    }

    private fun bindViews() {
        imagesViewModel.imagesLiveData.observe(this, Observer {
            renderImagesState(it)
        })
    }

    private fun renderImagesState(viewState: State<PagingData<Photo>>) {
        when (viewState) {
            is State.Loading -> {
                viewBinding.progressBar.visibility = View.VISIBLE
            }

            is State.Success -> {
                viewBinding.progressBar.visibility = View.GONE
                imagesAdapter.submitData(lifecycle, viewState.data)
            }

            is State.Error -> {
                viewBinding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu!!.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { searchSubject.onNext(it) }
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { searchSubject.onNext(it) }
                return true
            }
        })
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}