package com.example.pocpaging.data.remote

import com.example.pocpaging.data.model.PageMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("3/discover/movie")
    fun getPopularMovies(
        @Query("api_key") api_Key: String = "deb5d2ebf9b2618ee42d5a131a61a498",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int
    ): Single<PageMovie>

}