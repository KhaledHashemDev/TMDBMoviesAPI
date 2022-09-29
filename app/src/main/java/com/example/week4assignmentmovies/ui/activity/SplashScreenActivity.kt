package com.example.week4assignmentmovies.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.week4assignmentmovies.databinding.ActivitySplashScreenBinding


/**
 * Splash screen for application
 */
class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3500)
    }
}