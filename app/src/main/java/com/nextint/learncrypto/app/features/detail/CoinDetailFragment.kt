package com.nextint.learncrypto.app.features.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.databinding.FragmentCoinDetailBinding
import timber.log.Timber

class CoinDetailFragment : Fragment() {
    private var _binding : FragmentCoinDetailBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val idCoin = arguments?.getString("ID_COIN")
        Timber.d(idCoin ?: "something")
        _binding = FragmentCoinDetailBinding.inflate(inflater,container,false)
        binding?.textViewIdCoin?.text = idCoin
        return binding?.root
    }

}