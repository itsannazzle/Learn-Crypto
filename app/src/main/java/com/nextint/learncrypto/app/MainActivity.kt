package com.nextint.learncrypto.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nextint.learncrypto.app.databinding.ActivityMainBinding
import com.nextint.learncrypto.app.features.home.HomeFragment
import com.nextint.learncrypto.app.features.utils.circleImage
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("on create")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_LearnCrypto)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        renderView()
        initListener()
        Timber.d("on start")
    }

    private fun renderView(){
        binding.topImg.circleImage("https://images.unsplash.com/photo-1621504450181-5d356f61d307?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",)
        binding.middleImg.circleImage("https://static.gatra.com/foldershared/images/2021/Pra/05-May/crypto.jpg")
        binding.bottomImg.circleImage("https://pyxis.nymag.com/v1/imgs/8f8/e12/51b54d13d65d8ee3773ce32da03e1fa220-dogecoin.rsquare.w700.jpg")
    }

    private fun initListener(){
        binding.btnLearnNow.setOnClickListener {
            supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityContainer, HomeFragment())
            .addToBackStack(null)
            .commit()
        }
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