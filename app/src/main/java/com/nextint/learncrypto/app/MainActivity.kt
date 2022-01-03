package com.nextint.learncrypto.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nextint.learncrypto.app.databinding.ActivityMainBinding
import com.nextint.learncrypto.app.features.home.HomeFragment
import com.nextint.learncrypto.app.features.onboarding.OnBoardFragment
import com.nextint.learncrypto.app.features.utils.circleImage
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("on create")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_LearnCrypto)
        inflateFragment(savedInstanceState)
        setContentView(binding.root)
    }

    private fun inflateFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainActivityContainer, OnBoardFragment())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("on start")
    }


    override fun onResume() {
        super.onResume()
        Timber.d("on Resume")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("on Restart")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("on Pause")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Timber.d("on Post created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("on Destroy")
    }
}