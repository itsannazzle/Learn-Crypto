package com.nextint.learncrypto.app.bases

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.dismissDialog
import com.nextint.learncrypto.app.features.utils.hideDialog
import com.nextint.learncrypto.app.features.utils.hideDialogForSoftDialog
import com.nextint.learncrypto.app.features.utils.initiateAlertDialogResponse
import com.nextint.learncrypto.app.features.utils.initiateDialogLoading
import com.nextint.learncrypto.app.features.utils.showDialog
import com.nextint.learncrypto.app.features.utils.showLoading


open class BaseActivity : AppCompatActivity() {
    lateinit var _dialog : Dialog
    var _dialogAlert : AlertDialog? = null
    val _remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    val _configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 36000
    }
//    protected lateinit var _dialogFragment : BaseDialogFragment
    protected var _modelDialog : DialogModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _dialog = Dialog(this)
        _dialog.initiateDialogLoading()

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        val windowInsetsController =
//            ViewCompat.getWindowInsetsController(window.decorView)
//
//        windowInsetsController?.isAppearanceLightNavigationBars = true

    }

    private fun checkAlertDialog() : Boolean
    {
        var isShowing = true

        if (_dialogAlert?.isShowing == true) isShowing = false

        if (isShowing)
        {
            _dialogAlert = AlertDialog.Builder(this).create()
//            _dialogAlert?.setView(layoutInflater.inflate(R.layout.fragment_base_dialog))
            _dialogAlert!!.initiateAlertDialogResponse()
        }
        return isShowing
    }

    fun showDialogFromModelResponseWithRetry(modelDialog : DialogModel)
    {
        runOnUiThread()
        {
            try
            {
                if (checkAlertDialog())
                {
                    modelDialog.apply {
                        _dialogAlert?.setTitle(getString(dialogTitle ?: R.string.dialog_default_title))
                        _dialogAlert?.setMessage(dialogMessage ?: getString( R.string.dialog_default_title))

                        _dialogAlert!!.setButton(DialogInterface.BUTTON_NEUTRAL, resources.getString(R.string.BUTTON_CANCEL))
                        {
                                dialog, _ ->
                            dialog.dismiss()
                            _dialog.hideDialogForSoftDialog(this@BaseActivity)

                        }
                        _dialogAlert!!.setButton(DialogInterface.BUTTON_POSITIVE, resources.getString(R.string.BUTTON_RETRY))
                        {
                                dialog, _ ->
                            dialog.dismiss()
                            _dialog.hideDialogForSoftDialog(this@BaseActivity)
                            retryActionAlert?.invoke()
                            _dialog.show()
                        }


                        _dialog.hideDialog(this@BaseActivity)
                        _dialogAlert!!.showDialog(this@BaseActivity)
                        _dialogAlert!!.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(
                            ContextCompat.getColor(this@BaseActivity, R.color.primary))
                        _dialogAlert!!.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(
                            ContextCompat.getColor(this@BaseActivity, R.color.green))
                    }

                }
            }
            catch (exception: WindowManager.BadTokenException)
            {
                //_dialog.hideDialog(this)
                _dialogAlert?.hideDialog(this)
            }
            catch (exception: Exception)
            {
                //_dialog.hideDialog(this)
                _dialogAlert?.hideDialog(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _dialog.hide()
        _dialog.dismissDialog(this)
        _dialogAlert?.dismissDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        _dialog.hide()
        _dialog.dismissDialog(this)
        _dialogAlert?.dismissDialog(this)

    }
}