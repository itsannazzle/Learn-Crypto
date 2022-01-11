package com.nextint.learncrypto.app.features.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModel
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModelFactory
import timber.log.Timber
import java.sql.Time
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var factory : HomeViewModelFactory
    private val homeViewModel : HomeViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Anna","on vc")
        homeViewModel.coins.observe(viewLifecycleOwner,{ response ->
            when(response){
                is ApiResponse.Success -> {
                    Timber.d(response.data[1].name)
                    Log.d("Anna",response.data[1].name)
                }
                is ApiResponse.Error -> {
                    Timber.d(response.message)
                    Log.d("Anna","error ${response.message}")
                }
                is ApiResponse.Empty -> {
                    Timber.d("empty")
                    Log.d("Anna","empty")
                }
                else -> {
                    Timber.d("else")
                    Log.d("Anna","else")
                }
            }
        })
    }

}