package com.example.pocpaging.data.model


import com.google.gson.annotations.SerializedName

data class PageMovie(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movies: List<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)