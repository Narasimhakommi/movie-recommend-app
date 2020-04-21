package com.example.movies.inf.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.DetailsMovie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class details_dataSource(private val apiService:dbinterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState  = MutableLiveData<state_network>()
    val networkState: LiveData<state_network>
        get() = _networkState
    private val _downloadedMovieDetailsResponse =  MutableLiveData<DetailsMovie>()
    val downloadedMovieResponse: LiveData<DetailsMovie>
        get() = _downloadedMovieDetailsResponse
    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(state_network.LOADING)


        try {
            compositeDisposable.add(
                apiService.getDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(state_network.LOADED)
                        },
                        {
                            _networkState.postValue(state_network.ERROR);
                             Log.e("Moviedetailsdatasource",it.message);
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message)
        }


    }
}