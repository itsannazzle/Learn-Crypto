package com.nextint.learncrypto.app.model.session

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(private val sessionManager: SessionManager) {

    fun onBoardingFinish(){
        sessionManager.createOnBoardSession()
    }

    fun isOnBoardingFinsih() : Boolean = sessionManager.isOnBoardFinish

    fun checkInstance() = Timber.d("check instance $this")
}