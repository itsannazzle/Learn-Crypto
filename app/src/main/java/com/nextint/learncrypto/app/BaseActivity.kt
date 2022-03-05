package com.nextint.learncrypto.app

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nextint.learncrypto.app.features.utils.*
import com.nextint.learncrypto.app.features.utils.showDialog

open class BaseActivity : AppCompatActivity() {
    lateinit var _dialog : Dialog
    var _dialogAlert : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _dialog = Dialog(this)
        _dialog.initiateDialogLoading()
    }

    private fun checkAlertDialog() : Boolean
    {
        var isShowing = true

        if (_dialogAlert?.isShowing == true) isShowing = false

        if (isShowing)
        {
            _dialogAlert = AlertDialog.Builder(this).create()
            _dialogAlert!!.initiateAlertDialogResponse()
        }
        return isShowing
    }

    fun showDialogFromModelResponseWithRetry(dialogTitle : String, dialogMessage : String, retryAction: () -> Unit?)
    {
        runOnUiThread()
        {
            try
            {
                if (checkAlertDialog())
                {
                    _dialogAlert?.setTitle(dialogTitle)
                    _dialogAlert?.setMessage(dialogMessage)

                    _dialogAlert!!.setButton(DialogInterface.BUTTON_NEUTRAL, resources.getString(R.string.BUTTON_CANCEL))
                    {
                            dialog, _ ->
                        dialog.dismiss()
                        _dialog.hideDialogForSoftDialog(this)

                    }
                    _dialogAlert!!.setButton(DialogInterface.BUTTON_POSITIVE, resources.getString(R.string.BUTTON_RETRY))
                    {
                            dialog, _ ->
                        dialog.dismiss()
                        _dialog.hideDialogForSoftDialog(this)
                        retryAction()
                    }

                    _dialog.hideDialog(this)
                    _dialogAlert!!.showDialog(this)
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


    override fun onDestroy() {
        super.onDestroy()

        _dialog.hide()
        _dialog.dismissDialog(this)
        _dialogAlert?.dismissDialog(this)

    }
}