package com.nextint.learncrypto.app.core.session

import android.content.Context
import com.nextint.learncrypto.app.util.KEY_ONBOARD
import com.nextint.learncrypto.app.util.SESSION

class SessionManager(context: Context) {

    private val sharedPref = context.getSharedPreferences(SESSION,Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun createOnBoardSession(){
        editor.putBoolean(KEY_ONBOARD,true)
            .apply()
    }

    val isOnBoardFinish = sharedPref.getBoolean(KEY_ONBOARD,false)

}