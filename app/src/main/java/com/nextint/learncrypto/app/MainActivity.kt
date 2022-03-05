package com.nextint.learncrypto.app

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.databinding.ActivityMainBinding
import com.nextint.learncrypto.app.features.onboarding.OnBoardFragment
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.initiateDialogLoading
import timber.log.Timber

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var _viewModelMainActivity : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_LearnCrypto)
        _dialog = Dialog(this@MainActivity)
        _dialog.initiateDialogLoading()
        _viewModelMainActivity = ViewModelProvider(this)[MainActivityViewModel::class.java]
        _viewModelMainActivity.checkInternetConnection()
        setContentView(binding.root)
    }

    private fun inflateFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainActivityContainer, OnBoardFragment())
            .commitNow()
    }


    override fun onStart() {
        super.onStart()
        _viewModelMainActivity.booleanNetworkConnection.observe(this,
            {
                response ->
                if (!response)
                {
                    showDialogFromModelResponseWithRetry(
                        "Oops, No internet connection",
                        "Please check your connection")
                    {
                        _viewModelMainActivity.checkInternetConnection()
                    }
                }  else
                {
                    inflateFragment()
                }
        })
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