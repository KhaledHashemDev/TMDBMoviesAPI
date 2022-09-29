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
import com.example.week4assignmentmovies.databinding.FragmentMovieBinding
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import com.example.week4assignmentmovies.ui.activity.SeeAllMovieActivity
import com.example.week4assignmentmovies.ui.adapter.PopularMovieAdapter
import com.example.week4assignmentmovies.ui.adapter.silder.MovieSliderAdapter
import com.example.week4assignmentmovies.viewModel.MovieViewModel

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var image_slider_movieFragment: SliderView
    private lateinit var movieSliderAdapter: MovieSliderAdapter
    private lateinit var popularMovieAdapter: PopularMovieAdapter
    private lateinit var topRatedMovieAdapter: PopularMovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var popularMovieRecView_moviesFragment: RecyclerView
    private lateinit var topRated_MovieLayout_movieFrag: LinearLayout
    private lateinit var topRatedMovieRecView_moviesFragment: RecyclerView

    private lateinit var noInternet_Layout_movieFragment: LinearLayout
    private lateinit var popular_MovieLayout_movieFrag: LinearLayout

    private lateinit var spin_kit_movieFrag: SpinKitView

    private val binding by lazy {
        FragmentMovieBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        image_slider_movieFragment = binding.imageSliderMovieFragment
        popularMovieRecView_moviesFragment =  binding.popularMovieRecViewMoviesFragment
        noInternet_Layout_movieFragment = binding.noInternetLayoutMovieFragment
        popular_MovieLayout_movieFrag = binding.popularMovieLayoutMovieFrag
        topRated_MovieLayout_movieFrag = binding.topRatedMovieLayoutMovieFrag
        topRatedMovieRecView_moviesFragment = binding.topRatedMovieRecViewMoviesFragment
        spin_kit_movieFrag = binding.spinKitMovieFrag
        val popular_MovieSeeAll_movieFrag = binding.popularMovieSeeAllMovieFrag
        val topRated_MovieSeeAll_movieFrag = binding.topRatedMovieSeeAllMovieFrag

        val doubleBounce: Sprite = Wave()
        spin_kit_movieFrag.setIndeterminateDrawable(doubleBounce)
        hideLayout()

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.refresh()
        movieViewModel.getPopularMovies("",1)

        observeViewModel()


        popular_MovieSeeAll_movieFrag.setOnClickListener {
            val intent = Intent(context, SeeAllMovieActivity::class.java)
            intent.putExtra("ComeFrom","PopularMovies")
            startActivity(intent)
        }

        topRated_MovieSeeAll_movieFrag.setOnClickListener {
            val intent = Intent(context, SeeAllMovieActivity::class.java)
            intent.putExtra("ComeFrom","TopRatedMovies")
            startActivity(intent)
        }

        showLayout()

        return binding.root
    }

    private fun hideLayout() {

        image_slider_movieFragment.visibility = View.GONE
        popular_MovieLayout_movieFrag.visibility = View.GONE
        topRated_MovieLayout_movieFrag.visibility = View.GONE
        spin_kit_movieFrag.visibility = View.VISIBLE
    }

    private fun showLayout(){

        spin_kit_movieFrag.visibility = View.GONE
        image_slider_movieFragment.visibility = View.VISIBLE
        popular_MovieLayout_movieFrag.visibility = View.VISIBLE
        topRated_MovieLayout_movieFrag.visibility = View.VISIBLE
    }

    private fun observeViewModel() {

        movieViewModel.upComingMovies.observe(requireActivity()) { countries ->
            countries?.let {

                movieSliderAdapter = MovieSliderAdapter(requireActivity(), it)
                image_slider_movieFragment.setSliderAdapter(movieSliderAdapter)
                image_slider_movieFragment.setIndicatorAnimation(IndicatorAnimationType.WORM)
                image_slider_movieFragment.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
                image_slider_movieFragment.startAutoCycle()


            }

        }

        movieViewModel.PopularMovies.observe(requireActivity()) { countries ->
            countries?.let {

                popularMovieAdapter = PopularMovieAdapter(requireActivity(), it)
                popularMovieRecView_moviesFragment.apply {
                    adapter = popularMovieAdapter  //
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(false)

                }

            }

        }

        movieViewModel.TopRatedMovies.observe(requireActivity()) { countries ->
            countries?.let {

                topRatedMovieAdapter = PopularMovieAdapter(requireActivity(), it)
                topRatedMovieRecView_moviesFragment.apply {
                    adapter = topRatedMovieAdapter //
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(false)
                }

            }

        }

        movieViewModel.movieLoadError.observe(requireActivity()) { isError ->


            if (isError == "" || isError == null) {
                noInternet_Layout_movieFragment.visibility = View.GONE
                image_slider_movieFragment.visibility = View.VISIBLE
                popular_MovieLayout_movieFrag.visibility = View.VISIBLE
                topRated_MovieLayout_movieFrag.visibility = View.VISIBLE
            } else {
                noInternet_Layout_movieFragment.visibility = View.VISIBLE
                image_slider_movieFragment.visibility = View.INVISIBLE
                popular_MovieLayout_movieFrag.visibility = View.INVISIBLE
                topRated_MovieLayout_movieFrag.visibility = View.INVISIBLE
            }

        }
        movieViewModel.loading.observe(requireActivity()) { isLoading ->
            isLoading?.let {


                spin_kit_movieFrag.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    image_slider_movieFragment.visibility = View.GONE
                    popular_MovieLayout_movieFrag.visibility = View.GONE
                    topRated_MovieLayout_movieFrag.visibility = View.GONE
                }

            }
        }
    }


}