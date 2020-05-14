package com.example.pocpaging.ui

import com.example.pocpaging.base.BaseRepository
import com.example.pocpaging.data.remote.HomeService
import javax.inject.Inject

class MainRepository @Inject constructor(private val homeService: HomeService) : BaseRepository() {

    fun getMovieList(page: Int) = homeService.getPopularMovies(page = 1)

}

