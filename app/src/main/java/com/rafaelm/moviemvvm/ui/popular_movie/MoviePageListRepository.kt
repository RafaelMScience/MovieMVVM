package com.rafaelm.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rafaelm.moviemvvm.data.api.POST_PER_PAGE
import com.rafaelm.moviemvvm.data.api.TheMovieDBInterface
import com.rafaelm.moviemvvm.data.repository.MovieDataSource
import com.rafaelm.moviemvvm.data.repository.MovieDataSourceFactory
import com.rafaelm.moviemvvm.data.repository.NetworkState
import com.rafaelm.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: TheMovieDBInterface) {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}