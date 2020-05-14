package com.example.pocpaging.ui

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pocpaging.base.BaseViewModel
import com.example.pocpaging.data.model.Movie
import com.example.pocpaging.ui.paging.MovieListDataSourceFactory
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    var movieList: LiveData<PagedList<Movie>>
    private var factory: MovieListDataSourceFactory

    init {
        factory = MovieListDataSourceFactory(mainRepository, internalState)
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()
        movieList = LivePagedListBuilder<Int, Movie>(factory, config).build()
    }

    fun refresh() {
        factory.refresh()
    }

    override fun onCleared() {
        factory.clear()
        super.onCleared()
    }


}