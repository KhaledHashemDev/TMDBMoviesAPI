package com.example.week4assignmentmovies.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week4assignmentmovies.databinding.SingleMovieBinding
import com.example.week4assignmentmovies.ui.activity.MovieDetailsActivity
import com.example.week4assignmentmovies.utils.Util
import com.example.week4assignmentmovies.model.movie.Result


class PopularMovieAdapter(private val ctx: Context, val movies: List<Result>) :
    RecyclerView.Adapter<PopularMovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            SingleMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val movie: Result = movies[position]

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

    class MyViewHolder(binding: SingleMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val poster = binding.imageViewSingleMovie
    }
}