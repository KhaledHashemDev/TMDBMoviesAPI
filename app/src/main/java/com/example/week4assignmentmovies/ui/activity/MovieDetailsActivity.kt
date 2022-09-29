package com.example.week4assignmentmovies.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.week4assignmentmovies.databinding.ActivityMovieDetailsBinding
import com.example.week4assignmentmovies.utils.Util
import com.example.week4assignmentmovies.viewModel.MovieViewModel
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private var movieId: String = ""
    lateinit var movieViewModel: MovieViewModel
    lateinit var title_single_movie_Details: TextView
    lateinit var adultCheck_movie_slider: TextView
    lateinit var date_single_movie_Details: TextView
    lateinit var genre1_movie_Details: TextView
    lateinit var genre2_movie_Details: TextView
    lateinit var movieOverview_MovieDetails: TextView
    lateinit var popularity_movieDetails: TextView
    lateinit var imageView_single_movie_Details: ImageView
    lateinit var trailer_movieDetails: ImageView
    lateinit var genre2Layout_movie_Details: LinearLayout
    lateinit var progress_bar_MovieDetails: ProgressBar
    lateinit var spin_kit_movieDetails: ProgressBar
    lateinit var linearLayout_movieTrailer: LinearLayout
    lateinit var adultCheckLayout_movieDetails: LinearLayout
    lateinit var linearLayout2_title_movieDetails: LinearLayout
    lateinit var descLayout: LinearLayout

    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val backBtn_movie_Details = binding.backBtnMovieDetails
        title_single_movie_Details = binding.titleSingleMovieDetails
        imageView_single_movie_Details = binding.imageViewSingleMovieDetails
        adultCheck_movie_slider = binding.adultCheckMovieSlider
        date_single_movie_Details = binding.dateSingleMovieDetails
        genre1_movie_Details = binding.genre1MovieDetails
        genre2_movie_Details = binding.genre2MovieDetails
        genre2Layout_movie_Details = binding.genre2LayoutMovieDetails
        movieOverview_MovieDetails = binding.movieOverviewMovieDetails
        progress_bar_MovieDetails = binding.progressBarMovieDetails
        popularity_movieDetails = binding.popularityMovieDetails
        spin_kit_movieDetails = binding.spinKitMovieDetails
        linearLayout_movieTrailer = binding.linearLayoutMovieTrailer
        adultCheckLayout_movieDetails = binding.adultCheckLayoutMovieDetails
        linearLayout2_title_movieDetails = binding.linearLayout2TitleMovieDetails
        descLayout = binding.descLayout
        trailer_movieDetails = binding.trailerMovieDetails

        val doubleBounce: Sprite = Wave()
        spin_kit_movieDetails.indeterminateDrawable = doubleBounce
        hideAllLayouts()

        movieId = intent.getStringExtra("MovieIdPass").toString()

        backBtn_movie_Details.setOnClickListener {
            onBackPressed()
        }

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getMovieDetails(movieId, "en-US")
        observeViewModel()

        trailer_movieDetails.setOnClickListener {
            val intent = Intent(this, YoutubeVideoPlayerActivity::class.java)
            intent.putExtra("MovieIdPass",movieId)
            startActivity(intent)
        }

        Handler().postDelayed({

            showAllLayouts()

        }, 2000)


    }

    private fun hideAllLayouts() {
        spin_kit_movieDetails.visibility = View.VISIBLE
        imageView_single_movie_Details.visibility = View.INVISIBLE
        linearLayout_movieTrailer.visibility = View.INVISIBLE
        descLayout.visibility = View.INVISIBLE
        adultCheckLayout_movieDetails.visibility = View.INVISIBLE
        linearLayout2_title_movieDetails.visibility = View.INVISIBLE
    }

    private fun showAllLayouts() {
        spin_kit_movieDetails.visibility = View.INVISIBLE
        imageView_single_movie_Details.visibility = View.VISIBLE
        linearLayout_movieTrailer.visibility = View.VISIBLE
        descLayout.visibility = View.VISIBLE
        linearLayout2_title_movieDetails.visibility = View.VISIBLE
    }



    private fun observeViewModel() {

        movieViewModel.MovieDetails.observe(this, Observer { movie ->
            movie?.let {

                Log.d("APP", "Success to get $it")
                title_single_movie_Details.text = it.title

                Glide
                    .with(this)
                    .load(Util.posterUrlMake(it.posterPath))
                    .into(imageView_single_movie_Details)

                if(it.adult){
                    adultCheck_movie_slider.text = "18+"
                }
                else{
                    adultCheck_movie_slider.text = "13+"
                }

                date_single_movie_Details.text = it.releaseDate
                movieOverview_MovieDetails.text = it.overview

                genre1_movie_Details.text = it.genres[0].name
                if(it.genres.size > 1){
                    genre2_movie_Details.text = it.genres[1].name
                    genre2Layout_movie_Details.visibility = View.VISIBLE
                }else{
                    genre2Layout_movie_Details.visibility = View.INVISIBLE
                }


                progress_bar_MovieDetails.progress = (it.voteAverage * 10).toInt()
                popularity_movieDetails.text = "${it.voteAverage} Rating"
            }

        })


        movieViewModel.movieLoadError.observe(this, Observer { isError ->

        })
        movieViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
            }
        })
    }


}