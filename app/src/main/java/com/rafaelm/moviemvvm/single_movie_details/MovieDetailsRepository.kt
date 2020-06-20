package com.rafaelm.moviemvvm.single_movie_details

import androidx.lifecycle.LiveData
import com.rafaelm.moviemvvm.data.api.TheMovieDBInterface
import com.rafaelm.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.rafaelm.moviemvvm.data.repository.NetworkState
import com.rafaelm.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(
    private val apiService: TheMovieDBInterface
) {
    private lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMoviewDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsReponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}