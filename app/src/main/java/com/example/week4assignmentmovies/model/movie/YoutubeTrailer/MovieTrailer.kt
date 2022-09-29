package com.example.week4assignmentmovies.model.movie.YoutubeTrailer

import com.google.gson.annotations.SerializedName

data class MovieTrailer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)