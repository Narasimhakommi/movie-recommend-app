package com.example.movies.inf.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : dbinterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {
    val moviesLiveDataSource =  MutableLiveData<TopMovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = TopMovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}