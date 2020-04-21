package com.example.movies.inf.api

import com.example.movies.inf.obj.DetailsMovie
import com.example.movies.inf.obj.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface dbinterface {
   // https://api.themoviedb.org/3/movie/top_rated?api_key=42738f52c96ab37f02b80b5c6ace30b1&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/upcoming?api_key=42738f52c96ab37f02b80b5c6ace30b1
    // https://api.themoviedb.org/3/
    @GET("movie/top_rated")
    fun getTopRated(@Query("page")page:Int):Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetails(@Path("movie_id")id:Int): Single<DetailsMovie>
}