package com.nextint.learncrypto.app

import android.app.Activity
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.nextint.learncrypto.app.model.session.SessionRepository
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sessionRepository: SessionRepository
    @Inject
    lateinit var sessionRepository2: SessionRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CryptoApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Timber.d("on create")

        sessionRepository.checkInstance()
        sessionRepository2.checkInstance()
        setTheme(R.style.Theme_LearnCrypto)
        setContentView(R.layout.activity_main)
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