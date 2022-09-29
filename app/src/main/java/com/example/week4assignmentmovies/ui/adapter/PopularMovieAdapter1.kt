package com.example.week4assignmentmovies.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week4assignmentmovies.databinding.SingleMoviePopularBinding
import com.example.week4assignmentmovies.model.movie.Result
import com.example.week4assignmentmovies.ui.activity.MovieDetailsActivity
import com.example.week4assignmentmovies.utils.Util


//adapter for popular fragment
class PopularMovieAdapter1(private val ctx: Context, val movies: List<Result>) :
    RecyclerView.Adapter<PopularMovieAdapter1.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            SingleMoviePopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val movie: Result = movies[position]

        viewHolder.name.text = movie.title
        viewHolder.date.text = movie.releaseDate


        Glide
            .with(ctx)
            .load(Util.posterUrlMake(movie.posterPath))
            .into(viewHolder.poster)


        viewHolder.itemView.setOnClickListener {
            val intent = Intent(ctx, MovieDetailsActivity::class.java)
            val movieId:String = movie.id.toString()
            intent.putExtra("MovieIdPass",movieId)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    class MyViewHolder(binding: SingleMoviePopularBinding) : RecyclerView.ViewHolder(binding.root) {

        val poster = binding.imageViewSingleMovie1
        val name = binding.movieName1
        val date = binding.dateSingleMoviePopular
    }
}