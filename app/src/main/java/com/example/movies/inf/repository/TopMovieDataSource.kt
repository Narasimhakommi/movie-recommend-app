package com.example.movies.inf.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movies.inf.api.FIRST_PAGE
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopMovieDataSource (private val apiService : dbinterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>() {
    private var page = FIRST_PAGE

    val networkState: MutableLiveData<state_network> = MutableLiveData()
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(state_network.LOADING)

        compositeDisposable.add(
            apiService.getTopRated(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.MovieList, params.key+1)
                            networkState.postValue(state_network.LOADED)
                        }
                        else{
                            networkState.postValue(state_network.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(state_network.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(state_network.LOADING)

        compositeDisposable.add(
            apiService.getTopRated(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.MovieList, null, page+1)
                        networkState.postValue(state_network.LOADED)
                    },
                    {
                        networkState.postValue(state_network.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

}