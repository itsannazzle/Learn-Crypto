package com.nextint.learncrypto.app.bases

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.*
import com.nextint.learncrypto.app.features.utils.showDialog

open class BaseActivity : AppCompatActivity() {
    lateinit var _dialog : Dialog
    var _dialogAlert : AlertDialog? = null
    protected lateinit var _dialogFragment : BaseDialogFragment
    protected var _modelDialog : DialogModel? = null

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

    fun showDialogFromModelResponseWithRetry(modelDialog : DialogModel)
    {
        runOnUiThread()
        {
            try
            {
                if (checkAlertDialog())
                {
                    modelDialog.apply {
                        _dialogAlert?.setTitle(getString(dialogTitle))
                        _dialogAlert?.setMessage(getString(dialogMessage))

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


    override fun onDestroy() {
        super.onDestroy()

        _dialog.hide()
        _dialog.dismissDialog(this)
        _dialogAlert?.dismissDialog(this)

    }
}