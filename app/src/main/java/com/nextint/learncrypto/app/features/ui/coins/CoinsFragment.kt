package com.nextint.learncrypto.app.features.ui.coins

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentCoinsBinding
import com.nextint.learncrypto.app.features.coins.adapter.CoinViewHolder
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModel
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModelFactory
import com.nextint.learncrypto.app.features.detail.CoinDetailFragment
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVertical
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.BaseAdapter
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import timber.log.Timber
import javax.inject.Inject


class CoinsFragment : Fragment() {
    private lateinit var _binding : FragmentCoinsBinding
    private lateinit var _coinAdapter : BaseAdapter<CoinsResponseItem, CoinViewHolder>

    @Inject
    lateinit var _factoryCoinViewModel : CoinsViewModelFactory
    private val _coinsViewModel : CoinsViewModel by viewModels()
    {
        _factoryCoinViewModel
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
        _binding = FragmentCoinsBinding.inflate(layoutInflater,container,false)
        _coinsViewModel.getCoins()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        displayView()
        setupCoins()
    }

    private fun setupAdapter()
    {
        _coinAdapter = BaseAdapter({ parent, _ -> CoinViewHolder.inflate(parent) }, {
                viewHolder, _, item ->
            viewHolder.bind(item)
            viewHolder.setCoinAction {
                val bundle = Bundle()
                bundle.putString(ID_COIN_CONSTANT,item.id)
                replaceFragment(parentFragmentManager, CoinDetailFragment(),bundle)
            }
        })
    }

    private fun setupCoins()
    {
                _coinsViewModel.coins.observe(viewLifecycleOwner,{ response ->
            when(response){
                is ApiResponse.Success -> {
                    _coinAdapter.safeAddAll(response.data)
                }
                is ApiResponse.Error -> {
                    Timber.d(response.message)

                }
                is ApiResponse.Empty -> {
                    Timber.d("empty")
                }
                else -> {
                    Timber.d("else")
                }
            }
        })

        _coinsViewModel.message.observe(viewLifecycleOwner,{
            Timber.d("msg $it")
        })

        _coinsViewModel.loading.observe(viewLifecycleOwner,{
            _binding.progressBar.visibility = setVisibility(it)
            Timber.d(it.toString())
        })
    }

    private fun displayView()
    {
        _binding.recylerViewCoins.apply {
            setVertical()
            adapter = _coinAdapter
        }
    }





}