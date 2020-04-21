package com.example.movies.ui.single_page_details.topRatedMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movies.inf.api.POST_PER_PAGE
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.Movie
import com.example.movies.inf.repository.MovieDataSourceFactory
import com.example.movies.inf.repository.TopMovieDataSource
import com.example.movies.inf.repository.state_network
import io.reactivex.disposables.CompositeDisposable

class moviepagelistRepo (private val apiService : dbinterface) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<state_network> {
        return Transformations.switchMap<TopMovieDataSource, state_network>(
            moviesDataSourceFactory.moviesLiveDataSource, TopMovieDataSource::networkState)
    }
}