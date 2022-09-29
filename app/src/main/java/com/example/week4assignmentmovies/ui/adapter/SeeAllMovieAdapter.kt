package com.example.week4assignmentmovies.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week4assignmentmovies.databinding.SingleMovieSeeAllBinding
import com.example.week4assignmentmovies.model.movie.Result
import com.example.week4assignmentmovies.ui.activity.MovieDetailsActivity
import com.example.week4assignmentmovies.utils.Util


class SeeAllMovieAdapter(private val ctx: Context ) :
    RecyclerView.Adapter<SeeAllMovieAdapter.MyViewHolder>() {

    var movies: ArrayList<Result> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            SingleMovieSeeAllBinding.inflate(
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

    fun addMovie(movie : List<Result>){
        movies.addAll(movie)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    class MyViewHolder(binding: SingleMovieSeeAllBinding) : RecyclerView.ViewHolder(binding.root) {
        val poster = binding.imageViewSingleMovieSeeAll
    }
}