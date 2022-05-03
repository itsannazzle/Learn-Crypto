package com.nextint.learncrypto.app.features.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.databinding.FragmentMarketDetailBinding
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID

class MarketDetailFragment : Fragment() {
    private var _bindingFragmentMarketDetail : FragmentMarketDetailBinding? = null
    private val _getbindingFragmentMarketDetail get() = _bindingFragmentMarketDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val model = arguments?.getParcelable<MarketsByCoinIdResponseItem>(MODEL_PARCEL_MARKET_BY_ID)
        _bindingFragmentMarketDetail= FragmentMarketDetailBinding.inflate(layoutInflater,container,false)
        if (model!= null)
        {
            _getbindingFragmentMarketDetail?.textViewMarketExchangeName?.text = model.exchangeName
        }
        return _getbindingFragmentMarketDetail?.root
    }

}