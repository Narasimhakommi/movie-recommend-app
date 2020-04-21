package com.example.movies.ui.single_page_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movies.inf.obj.DetailsMovie
import com.example.movies.inf.repository.state_network
import io.reactivex.disposables.CompositeDisposable

class singleviewmodel(private val movieRepository : MovieDetailsRepo, movieId: Int)  : ViewModel()  {
    private val compositeDisposable = CompositeDisposable()
    val  movieDetails : LiveData<DetailsMovie> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<state_network> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}