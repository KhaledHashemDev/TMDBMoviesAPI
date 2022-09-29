package com.example.week4assignmentmovies.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignmentmovies.R
import com.example.week4assignmentmovies.databinding.ActivitySeeAllMovieBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import com.example.week4assignmentmovies.ui.adapter.SeeAllMovieAdapter
import com.example.week4assignmentmovies.viewModel.MovieViewModel



@AndroidEntryPoint
class SeeAllMovieActivity : AppCompatActivity() {

    private var comeFrom = ""

    lateinit var toolbar_seeAllMovies: MaterialToolbar

    lateinit var recyclerView_seeAllMovies: RecyclerView

    lateinit var seeAllMovieAdapter: SeeAllMovieAdapter
    lateinit var movieViewModel: MovieViewModel
    lateinit var layoutManagerGrid: GridLayoutManager

    var page = 1

    private val binding by lazy {
        ActivitySeeAllMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        comeFrom = intent.getStringExtra("ComeFrom").toString()

       // toolbar_seeAllMovies = findViewById(R.id.toolbar_seeAllMovies)
        toolbar_seeAllMovies = binding.toolbarSeeAllMovies

        setSupportActionBar(toolbar_seeAllMovies)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_seeAllMovies.setTitleTextColor(resources.getColor(R.color.white))
        toolbar_seeAllMovies.setNavigationIconTint(resources.getColor(R.color.white))

        layoutManagerGrid = GridLayoutManager(this@SeeAllMovieActivity,3)
        recyclerView_seeAllMovies = binding.recyclerViewSeeAllMovies

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        seeAllMovieAdapter = SeeAllMovieAdapter(this)
        recyclerView_seeAllMovies.apply {
            adapter = seeAllMovieAdapter
            layoutManager = layoutManagerGrid
            setHasFixedSize(false)
        }

        when(comeFrom){

            "PopularMovies" -> {

                toolbar_seeAllMovies.title =  "Popular Movies"
                movieViewModel.getPopularMovies("",page)
                observeViewModel()
            }
            "TopRatedMovies" -> {

                toolbar_seeAllMovies.title =  "Top Rated Movies"
                movieViewModel.getTopRatedMovies("",page)
                observeViewModel()
            }
            else -> {
                toolbar_seeAllMovies.title =  ""
            }

        }



    }

    private fun getPagedData(){
        when(comeFrom){
            "PopularMovies" -> {

                toolbar_seeAllMovies.title =  "Popular Movies"
                movieViewModel.getPopularMovies("",page)
                observeViewModel()
            }
            else -> {
                toolbar_seeAllMovies.title =  ""
            }

        }
    }
    private fun observeViewModel() {


        if(comeFrom == "PopularMovies"){
            getPopularMovies()
        }
        else if(comeFrom == "TopRatedMovies"){
            getTopRatedMovies()
        }

        movieViewModel.movieLoadError.observe(this, Observer { isError ->

        })
        movieViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {


            }
        })
    }

    private fun getTopRatedMovies() {

        movieViewModel.TopRatedMovies.observe(this, Observer {movies ->
            movies?.let {
                seeAllMovieAdapter.addMovie(it)
            }

        })
    }

    private fun getPopularMovies() {
        movieViewModel.PopularMovies.observe(this, Observer {movies ->
            movies?.let {
                seeAllMovieAdapter.addMovie(it)
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}