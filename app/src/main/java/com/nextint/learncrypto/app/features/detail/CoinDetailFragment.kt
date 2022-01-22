package com.nextint.learncrypto.app.features.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextint.learncrypto.app.R
import timber.log.Timber

class CoinDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val idCoin = arguments?.getString("ID_COIN")
        Timber.d(idCoin ?: "something")
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

}