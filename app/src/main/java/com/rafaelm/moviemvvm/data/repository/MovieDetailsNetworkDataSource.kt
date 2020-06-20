package com.rafaelm.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafaelm.moviemvvm.data.api.TheMovieDBInterface
import com.rafaelm.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
        get() = _networkState           //with this get, no need to implement get function to get networkState

    private val _downloadedMovieDetailsReponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsReponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsReponse

    fun fetchMoviewDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsReponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )
        } catch (e: Exception) {

        }
    }
}