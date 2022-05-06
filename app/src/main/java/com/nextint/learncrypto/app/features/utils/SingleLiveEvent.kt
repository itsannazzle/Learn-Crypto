package com.nextint.learncrypto.app.features.utils

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()){
            Timber.d(
                this.javaClass.canonicalName,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        //Observe internal mutable livedata
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(@Nullable value: T?) {
        mPending.set(true)
        super.setValue(value)
    }
    /*
    * komen
    * */

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    
    @MainThread
    fun call() {
        value = null
    }
}