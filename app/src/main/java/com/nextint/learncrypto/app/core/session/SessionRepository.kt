package com.nextint.learncrypto.app.core.session

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


class SessionRepository (private val sessionManager: SessionManager) {

    fun onBoardingFinish(){
        sessionManager.createOnBoardSession()
    }

    fun isOnBoardingFinsih() : Boolean = sessionManager.isOnBoardFinish

    fun checkInstance() = Timber.d("check instance $this")
}