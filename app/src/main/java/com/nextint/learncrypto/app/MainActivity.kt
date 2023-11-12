package com.nextint.learncrypto.app

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.nextint.learncrypto.app.bases.BaseActivity
import com.nextint.learncrypto.app.databinding.ActivityMainBinding
import com.nextint.learncrypto.app.features.coins.CoinsFragment
import com.nextint.learncrypto.app.features.onboarding.OnBoardFragment
import com.nextint.learncrypto.app.features.overview.HomeFragment
import com.nextint.learncrypto.app.features.search.presentation.SearchFragment
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.initiateDialogLoading
import com.nextint.learncrypto.app.util.STRING_DATASTORE_NAME
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_CHANNELNAME
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_ID
import com.nextint.learncrypto.app.util.STRING_NOTIFICATION_STATE
import com.nextint.learncrypto.app.util.STRING_ONBOARDING_SESSION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MainActivity : BaseActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var _viewModelMainActivity : MainActivityViewModel
    private var booleanOnBoarding : Boolean = false
    private var stringFragmentNavigation : String? = null

    companion object
    {
        private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = STRING_DATASTORE_NAME)
        val keyOnBoardingSession = booleanPreferencesKey(STRING_ONBOARDING_SESSION)
    }

    //region LIFECYCLE
    override fun onCreate(savedInstanceState: Bundle?)
    {
        //region INITIALIZATION

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_LearnCrypto)
        _dialog = Dialog(this@MainActivity)
        _dialog.initiateDialogLoading()
        _modelDialog = DialogModel()
        _viewModelMainActivity = ViewModelProvider(this,)[MainActivityViewModel::class.java]
        AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO))
        setContentView(binding.root)

        //endregion

        //region DEEPLINK

        val action: String? = intent?.action
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

        FirebaseMessaging.getInstance().token.addOnSuccessListener()
        {
                deviceToken ->
            Timber.d("device token :  $deviceToken")
        }

        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data

        //endregion

        //region SESSION

        lifecycleScope.launch(Dispatchers.IO) {
            booleanOnBoarding = readValue(keyOnBoardingSession) ?: false
        }

        //endregion
    }
    override fun onStart()
    {
        super.onStart()
        _viewModelMainActivity.checkInternetConnection()

        val data: Uri? = intent?.data
        stringFragmentNavigation = data?.lastPathSegment.toString()
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
                inflateFragment(stringFragmentNavigation)
            }
        }
        Timber.d("on Start")

    }
    override fun onResume() {
        super.onResume()
        Timber.d("on Resume")
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


    //endregion

    //region FUNCTION
    suspend fun <T> saveValue(key : Preferences.Key<T>, value: T)
    {
        preferencesDataStore.edit { setting ->
            setting[key] = value
        }
    }

    suspend fun <T> readValue(key: Preferences.Key<T>) : T?
    {
        val result = preferencesDataStore.data.catch { exception ->
            if (exception is IOException)
            {
                emit(emptyPreferences())
            }
            else
            {
                throw  exception
            }
        }.first()[key]

        return result
    }

    private fun inflateFragment(stringPath : String?) {
        when(stringPath)
        {
            "coins" ->
            {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, CoinsFragment())
                    .commitNow()
            }
            "SearchFragment" ->
            {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, SearchFragment())
                    .commitNow()
            }
            else ->
            {
                if (booleanOnBoarding)
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainActivityContainer, HomeFragment())
                        .commitNow()
                }
                else
                {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.mainActivityContainer, OnBoardFragment())
                        .commitNow()
                }
            }
        }
    }

    //endregion
}