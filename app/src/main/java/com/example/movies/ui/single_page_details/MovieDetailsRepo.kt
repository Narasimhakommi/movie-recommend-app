package com.example.movies.ui.single_page_details

import androidx.lifecycle.LiveData
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.DetailsMovie
import com.example.movies.inf.repository.details_dataSource
import com.example.movies.inf.repository.state_network
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepo(private val apiService : dbinterface) {
    lateinit var movieDetailsNetworkDataSource: details_dataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<DetailsMovie> {

        movieDetailsNetworkDataSource = details_dataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }
    fun getMovieDetailsNetworkState(): LiveData<state_network> {
        return movieDetailsNetworkDataSource.networkState
    }
}