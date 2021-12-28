package com.nextint.learncrypto.app.features.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(){
    val a = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    a.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)

}

