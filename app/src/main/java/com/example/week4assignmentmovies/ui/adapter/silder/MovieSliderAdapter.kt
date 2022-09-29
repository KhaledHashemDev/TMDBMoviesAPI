package com.example.week4assignmentmovies.ui.adapter.silder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.week4assignmentmovies.databinding.SingleMovieSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import com.example.week4assignmentmovies.model.movie.Result
import com.example.week4assignmentmovies.ui.activity.MovieDetailsActivity
import com.example.week4assignmentmovies.utils.Constants
import com.example.week4assignmentmovies.utils.Helper
import com.example.week4assignmentmovies.utils.Util


 class MovieSliderAdapter(private val ctx :Context, val movies : List<Result>):
    SliderViewAdapter<MovieSliderAdapter.MyViewHolder>() {


    override fun getCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup?): MyViewHolder =
        MyViewHolder(
            SingleMovieSliderBinding.inflate(
                LayoutInflater.from(parent!!.context),
                parent,
                false
            )
    )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: MyViewHolder?, position: Int) {
        val movie: Result = movies[position]

        Glide
            .with(ctx)
            .load(Util.posterUrlMake(movie.posterPath))
            .into(viewHolder!!.poster)

//Compare release date to decide whether the movie is "New" or "Upcoming"
        if(Helper.CompareDate(movie.releaseDate) == 1){
            viewHolder.titleBig.text = "New Movies"
        }else if(Helper.CompareDate(movie.releaseDate) == 2){
            viewHolder.titleBig.text = "Upcoming Movies"
        }


        viewHolder.movieTitle.text = movie.title
        viewHolder.releaseDate.text = movie.releaseDate
        viewHolder.genre1.text = Constants.getGenre(movie.genreIds[0])

        if(movie.genreIds.size > 1){
            viewHolder.genre2.text = Constants.getGenre(movie.genreIds[1])
            viewHolder.genre2Layout.visibility = View.VISIBLE
        }else{
            viewHolder.genre2Layout.visibility = View.INVISIBLE
        }


        viewHolder.itemView.setOnClickListener {
            val intent = Intent(ctx, MovieDetailsActivity::class.java)
            val movieId:String = movie.id.toString()
            intent.putExtra("MovieIdPass",movieId)
            ctx.startActivity(intent)
        }

    }


    class MyViewHolder(binding: SingleMovieSliderBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        val poster = binding.imageViewSingleMovieSlider
        val movieTitle = binding.titleSingleMovieSlider
        val titleBig = binding.titleBigSingleMovieSlider
        val releaseDate = binding.dateSingleMovieSlider
        val genre1 = binding.genre1MovieSlider
        val genre2 = binding.genre2MovieSlider
        val genre2Layout = binding.genre2LayoutMovieSlider
    }
}