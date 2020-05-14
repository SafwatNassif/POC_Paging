package com.example.pocpaging.ui.paging

import androidx.lifecycle.MutableLiveData
import com.example.pocpaging.base.BasePageDataSource
import com.example.pocpaging.base.ViewState
import com.example.pocpaging.data.model.Movie
import com.example.pocpaging.ui.MainRepository
import io.reactivex.functions.Consumer

class MovieListDataSource constructor(
    private val repository: MainRepository,
    private val viewState: MutableLiveData<ViewState>
) : BasePageDataSource<Movie>(viewState) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val page = 1
        subscribe(repository.getMovieList(page = page), Consumer {
            callback.onResult(it.movies, null, page + 1)
        }, Consumer {
            viewState.value = ViewState.Error(remoteError = it.toString())
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        subscribe(repository.getMovieList(params.key), Consumer {
            callback.onResult(it.movies, params.key + 1)
        }, Consumer {
            viewState.value = ViewState.Error(remoteError = it.toString())
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //pass
    }



}