package com.example.movies.inf.obj


import com.google.gson.annotations.SerializedName

data class DetailsMovie(
    val adult: Boolean,
    val budget: Int,
    val homepage: String,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double
)