package com.nextint.learncrypto.app.features.price_converter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.databinding.FragmentPriceConverterBinding
import com.nextint.learncrypto.app.databinding.FragmentSearchBinding
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModel
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModelFactory
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModel_Factory
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModel
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModelFactory
import com.nextint.learncrypto.app.features.price_converter.presentation.PriceConverterViewModel
import javax.inject.Inject

class PriceConverterFragment : BaseFragment<PriceConverterViewModel>()
{
    private var _bindingPriceConverter : FragmentPriceConverterBinding? = null
    private val _getBindingPriceConverter get() = _bindingPriceConverter

    @Inject
    lateinit var _factoryCoinsViewModel : CoinsViewModelFactory
    private val _coinsViewModel : CoinsViewModel by viewModels()
    {
        _factoryCoinsViewModel
    }
    override fun setObserver(): Fragment = this

    override fun setupViewModel(): Class<PriceConverterViewModel> = PriceConverterViewModel::class.java

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _bindingPriceConverter = FragmentPriceConverterBinding.inflate(layoutInflater,container,false)
        return _getBindingPriceConverter?.root
    }



}