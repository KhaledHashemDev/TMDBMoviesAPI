package com.example.week4assignmentmovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignmentmovies.databinding.SingleMoviePopularBinding
import com.example.week4assignmentmovies.model.movie.Result


/**
 * paging still needs still to be implemented
 */

class MoviePagingAdapter : PagingDataAdapter<Result, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Result>(){

             override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                 return oldItem.id == newItem.id
             }

             override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                 return oldItem==newItem
             }
         }
     }

    inner class MyViewHolder ( val single: SingleMoviePopularBinding) : RecyclerView.ViewHolder(single.root)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
val binding = SingleMoviePopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }
}

private fun imageViewSingleMovie1() {

}

private fun movieName1() {

}
