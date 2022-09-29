package com.example.week4assignmentmovies.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.week4assignmentmovies.R
import com.example.week4assignmentmovies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.week4assignmentmovies.ui.fragments.MovieFragment
import com.example.week4assignmentmovies.ui.fragments.NowPlayingFragment
import com.example.week4assignmentmovies.ui.fragments.PopularFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer_activityMain, PopularFragment())
            .commit()



        val fragmentHome = MovieFragment()
        val fragmentProfile = PopularFragment()
        val fragmentNowPlaying = NowPlayingFragment()
        val myBottomNavigationView = binding.bottomNavMenu


myBottomNavigationView.setOnItemSelectedListener { item ->
    when(item.itemId){
        R.id.homeMenu -> {
            replaceCurrentFragment(fragmentHome)
            true
        }
        R.id.popularFragment -> {
            replaceCurrentFragment(fragmentProfile)
            true
        }
        R.id.nowPlayingFragment -> {
        replaceCurrentFragment(fragmentNowPlaying)
        true
    }
else -> {
    false
          }

    }
}

        //allow window to extend outside the screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    private fun replaceCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer_activityMain, fragment)
            commit()
        }

}