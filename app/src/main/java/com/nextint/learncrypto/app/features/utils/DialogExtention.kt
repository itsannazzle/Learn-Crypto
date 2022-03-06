package com.nextint.learncrypto.app.features.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.nextint.learncrypto.app.R

fun Dialog.showDialog(activity: Activity)
{
    activity.runOnUiThread()
    {
        this.show()
    }
}

fun Dialog.hideDialog(activity: Activity)
{
    activity.runOnUiThread()
    {
        this.hide()
    }
}

fun Dialog.initiateDialogLoading() {
    this.setContentView(R.layout.item_loading)
    this.setCancelable(true)
    this.setCanceledOnTouchOutside(false)
    this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Dialog.dismissDialog(activity: Activity)
{
    activity.runOnUiThread()
    {
        if (this.isShowing)
        {
            this.hide()
        }
        else
        {

        }

        this.dismiss()
    }
}

fun Dialog.initiateAlertDialogResponse()
{
    this.setCancelable(false)
    this.setCanceledOnTouchOutside(false)
    this.onBackPressed()
}

fun Dialog.hideDialogForSoftDialog(activity: Activity)
{
    activity.runOnUiThread()
    {
        if (this.isShowing)
        {
            this.hide()
        }
        else
        {

        }
    }
}