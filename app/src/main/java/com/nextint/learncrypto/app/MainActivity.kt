package com.nextint.learncrypto.app

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.nextint.learncrypto.app.bases.BaseActivity
import com.nextint.learncrypto.app.databinding.ActivityMainBinding
import com.nextint.learncrypto.app.features.onboarding.OnBoardFragment
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.initiateDialogLoading
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELNAME
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_ID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_STATE
import timber.log.Timber

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _viewModelMainActivity : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_LearnCrypto)
        Timber.d("on create")
        val stringChannelName = intent.getStringExtra(STRING_NOTIFICATION_CHANNELNAME)
        val stringChannelId = intent.getStringExtra(STRING_NOTIFICATION_CHANNELID)
        val stringNotificationId = intent.getIntExtra(STRING_NOTIFICATION_ID,1)
        val stringNotificationState = intent.getStringExtra(STRING_NOTIFICATION_STATE)

        if (stringChannelName != null)
        {
            when(stringNotificationState)
            {
                "MV" -> Toast.makeText(this,"state value : $stringNotificationState",Toast.LENGTH_LONG).show()
                "NONMV" -> Toast.makeText(this,"state value : $stringNotificationState",Toast.LENGTH_LONG).show()
                else -> Toast.makeText(this,"state value : ${stringNotificationState ?: "nothing"}",Toast.LENGTH_LONG).show()
            }
        }
        _dialog = Dialog(this@MainActivity)
        _dialog.initiateDialogLoading()

        FirebaseMessaging.getInstance().token.addOnSuccessListener()
        {
            deviceToken ->
            Timber.d("device token :  $deviceToken")
        }

        _modelDialog = DialogModel()
        _viewModelMainActivity = ViewModelProvider(this)[MainActivityViewModel::class.java]
        _viewModelMainActivity.checkInternetConnection()
        _viewModelMainActivity.booleanNetworkConnection.observe(this
        ) { response ->
            if (!response) {
                _modelDialog?.retryActionAlert =
                    { _viewModelMainActivity.checkInternetConnection() }
                _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                _modelDialog?.let { showDialogFromModelResponseWithRetry(it) }
            } else {
                this._dialog.hide()
                inflateFragment()
            }
        }
        setContentView(binding.root)
    }

    private fun inflateFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainActivityContainer, OnBoardFragment())
            .commitNow()
    }


    override fun onStart()
    {
        super.onStart()
        Timber.d("on Start")

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
        supportFragmentManager.beginTransaction().remove(OnBoardFragment())
    }
}