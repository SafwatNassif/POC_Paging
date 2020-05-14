package com.example.pocpaging.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pocpaging.base.ViewState
import com.example.pocpaging.data.model.Movie
import com.example.pocpaging.ui.MainRepository


class MovieListDataSourceFactory
constructor(private val repository: MainRepository, private val state: MutableLiveData<ViewState>) :
    DataSource.Factory<Int, Movie>() {

    private val movieListDataSource = MutableLiveData<MovieListDataSource>()
    private lateinit var dataSource: MovieListDataSource

    override fun create(): DataSource<Int, Movie> {
        dataSource = MovieListDataSource(repository, viewState = state)
        movieListDataSource.postValue(dataSource)
        return dataSource
    }

    fun refresh() {
        if (::dataSource.isInitialized)
            dataSource.invalidate()
    }

    fun clear() {
        if (::dataSource.isInitialized)
            dataSource.onCleared()
    }


}