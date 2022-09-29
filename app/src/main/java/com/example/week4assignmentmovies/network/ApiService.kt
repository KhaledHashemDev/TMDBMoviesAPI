package com.example.week4assignmentmovies.network


import com.example.week4assignmentmovies.model.movie.Movie
import com.example.week4assignmentmovies.model.movie.YoutubeTrailer.MovieTrailer
import com.example.week4assignmentmovies.model.movie.movieDetails.MovieDetails
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/"
const val API_KEY = "819950d4cf35be1fb70d8746bc0796bf"

interface ApiService {

    //Movies
    @GET("3/movie/upcoming?api_key=$API_KEY")
    suspend fun getUpcomingMovies(@Query("language") language: String,@Query("page") page : Int): Response<Movie>

    @GET("3/movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movie>

    //paging test
    @GET("3/movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies2(
        @Query("language") language: String,
        @Query("page") page: Int,
        apiKey: String
    ): Response<Movie>

    @GET("3/movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(@Query("language") language: String,@Query("page") page : Int): Response<Movie>

    @GET("3/movie/{movieId}?api_key=$API_KEY")
    suspend fun getMovieDetails(@Path("movieId") movieId: String, @Query("language") language: String): Response<MovieDetails>

   // video
    @GET("3/movie/{movieId}/videos?api_key=$API_KEY")
    suspend fun getMovieTrailer(@Path("movieId") movieId: String, @Query("language") language: String): Response<MovieTrailer>

}

class RetrofitService {
    fun getRetrofitService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}