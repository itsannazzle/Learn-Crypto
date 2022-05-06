package com.nextint.learncrypto.app.features.ui.dialog

import android.os.Parcelable
import com.nextint.learncrypto.app.R
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogModel(
    var httpErrorCode : Int? = null,
    var retryActionAlert : (() -> Unit?)? = null,
    var retryActionDialog : (() -> Unit?)? = null,
    var buttonText : Int? = R.string.BUTTON_OK,
    var dialogTitle : Int? = R.string.dialog_default_title,
    var dialogMessage : String? = null
) : Parcelable
{

}