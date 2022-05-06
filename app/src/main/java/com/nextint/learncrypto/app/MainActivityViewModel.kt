package com.nextint.learncrypto.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private var _booleanNetworkConnection : MutableLiveData<Boolean> = MutableLiveData()
    val booleanNetworkConnection : LiveData<Boolean> get() = _booleanNetworkConnection

    fun checkInternetConnection()
    {
        viewModelScope.launch(Dispatchers.IO)
        {

            _booleanNetworkConnection.postValue(UtilitiesFunction.checkInternetConnection())
        }
    }
}