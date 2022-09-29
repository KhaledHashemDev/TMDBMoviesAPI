package com.example.week4assignmentmovies.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignmentmovies.databinding.FragmentNowPlayingBinding
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import dagger.hilt.android.AndroidEntryPoint
import com.example.week4assignmentmovies.ui.activity.SeeAllMovieActivity
import com.example.week4assignmentmovies.ui.adapter.PopularMovieAdapter1
import com.example.week4assignmentmovies.viewModel.MovieViewModel

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {

    private lateinit var popularMovieAdapter1: PopularMovieAdapter1
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var nowPlayingMovieRecView_moviesFragment1: RecyclerView
    private lateinit var nowPlaying_MovieLayout_movieFrag1: LinearLayout
    private lateinit var spin_kit_movieFrag1: SpinKitView

    private val binding by lazy {
        FragmentNowPlayingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        nowPlayingMovieRecView_moviesFragment1 = binding.nowPlayingMovieRecViewMoviesFragment1
        nowPlaying_MovieLayout_movieFrag1 = binding.nowPlayingMovieLayoutMovieFrag1
        spin_kit_movieFrag1 = binding.spinKitMovieFrag1
        val nowPlaying_MovieSeeAll_movieFrag1 = binding.nowPlayingMovieSeeAllMovieFrag1

        val doubleBounce: Sprite = Wave()
        spin_kit_movieFrag1.setIndeterminateDrawable(doubleBounce)
        hideLayout()

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.refresh()
        movieViewModel.getPopularMovies("",1)

        observeViewModel()

        nowPlaying_MovieSeeAll_movieFrag1.setOnClickListener {
            val intent = Intent(context, SeeAllMovieActivity::class.java)
            intent.putExtra("ComeFrom","PopularMovies")
            startActivity(intent)
        }

        showLayout()

        return binding.root
    }


    private fun hideLayout() {

        nowPlaying_MovieLayout_movieFrag1.visibility = View.GONE
        spin_kit_movieFrag1.visibility = View.VISIBLE
    }

    private fun showLayout(){

        spin_kit_movieFrag1.visibility = View.GONE
        nowPlaying_MovieLayout_movieFrag1.visibility = View.VISIBLE
    }

    private fun observeViewModel() {


        movieViewModel.TopRatedMovies.observe(requireActivity()) { countries ->
            countries?.let {

                popularMovieAdapter1 = PopularMovieAdapter1(requireActivity(), it)
                nowPlayingMovieRecView_moviesFragment1.apply {
                    adapter = popularMovieAdapter1  //
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL, false
                    )
                    setHasFixedSize(false)

                }

            }

        }


        movieViewModel.movieLoadError.observe(requireActivity()) { isError ->

            if (isError == "" || isError == null) {
                nowPlaying_MovieLayout_movieFrag1.visibility = View.VISIBLE
            } else {
                nowPlaying_MovieLayout_movieFrag1.visibility = View.INVISIBLE
            }

        }
        movieViewModel.loading.observe(requireActivity()) { isLoading ->
            isLoading?.let {


                spin_kit_movieFrag1.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    nowPlaying_MovieLayout_movieFrag1.visibility = View.GONE
                }

            }
        }
    }

}