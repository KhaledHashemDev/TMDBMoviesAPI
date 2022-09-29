package com.example.week4assignmentmovies.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week4assignmentmovies.model.movie.Result
import com.example.week4assignmentmovies.model.movie.YoutubeTrailer.MovieTrailer
import com.example.week4assignmentmovies.model.movie.movieDetails.MovieDetails
import com.example.week4assignmentmovies.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val upComingMovies = MutableLiveData<List<Result>>()
    val PopularMovies = MutableLiveData<List<Result>>()
    private var popularMoviesTotalResults = -1
    val TopRatedMovies = MutableLiveData<List<Result>>()
    val MovieDetails = MutableLiveData<MovieDetails>()
    val MovieTrailer = MutableLiveData<MovieTrailer>()
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchUpcomingMovies()
        fetchTopRatedMovies("",1)
    }

    fun getPopularMovies(language: String,page : Int){
        fetchPopularMovies(language,page)
    }
    fun getTopRatedMovies(language: String,page : Int){
        fetchTopRatedMovies(language,page)
    }

    fun getMovieDetails(movieId : String,language: String) {
        fetchMovieDetails(movieId,language)
    }

    fun getMovieTrailer(movieId : String,language: String) {
        fetchMovieTrailer(movieId,language)
    }

    private fun fetchMovieTrailer(movieId: String, language: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getMovieTrailer(movieId,language)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    MovieTrailer.value = response.body()
                    movieLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }



    private fun fetchMovieDetails(movieId: String,language: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getMovieDetails(movieId,language)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    MovieDetails.value = response.body()
                    movieLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }

    private fun fetchUpcomingMovies() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getUpcomingMovies("",1)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    upComingMovies.value = response.body()?.results
                    movieLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }

    private fun fetchPopularMovies(language: String,page : Int){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getPopularMovies(language, page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    PopularMovies.value = response.body()?.results
                    popularMoviesTotalResults = response.body()!!.totalResults
                    movieLoadError.value = null
                    loading.value = false

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }

    private fun fetchTopRatedMovies(language: String,page : Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getNowPlayingMovies(language, page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    TopRatedMovies.value = response.body()?.results
                    movieLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }

    private fun onError(message: String) {

        GlobalScope.launch {
            withContext(Dispatchers.Main){
                movieLoadError.value = message
                loading.value = false
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}