package com.nextint.learncrypto.app.features.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.databinding.FragmentMarketDetailBinding
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.convertDateToStingPreviewSimple
import com.nextint.learncrypto.app.features.utils.convertStringToDate
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID

class MarketDetailFragment : Fragment() {
    private var _bindingFragmentMarketDetail : FragmentMarketDetailBinding? = null
    private val _getBindingFragmentMarketDetail get() = _bindingFragmentMarketDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val model = arguments?.getParcelable<MarketsByCoinIdResponseItem>(MODEL_PARCEL_MARKET_BY_ID)
        _bindingFragmentMarketDetail= FragmentMarketDetailBinding.inflate(layoutInflater,container,false)
        if (model!= null)
        {
            with(_getBindingFragmentMarketDetail)
            {
                this?.textViewMarketDetailPair?.text = model.pair
                this?.textViewMarketDetailMarket?.text = model.exchangeName
                this?.textViewCategory?.text = model.category
                this?.textViewDetailFeeType?.text = model.feeType
                this?.textViewOutlier?.text = UtilitiesFunction.convertBooleanToYesOrNo(model.outlier ?: false).toString()
                this?.textViewDetailTrustScore?.text = model.trustScore.uppercase()
                this?.textViewDetailVol24?.text = UtilitiesFunction.convertToUSD(model.quotes.baseKeyPrice.volume24h.toLong())
                this?.textViewDetailPrice?.text = UtilitiesFunction.convertToUSD(model.quotes.baseKeyPrice.price.toLong())
                this?.textViewDetailLastUpdate?.text = model.lastUpdated.convertStringToDate()?.convertDateToStingPreviewSimple() ?: getString(
                    R.string.dash)
                this?.textViewConverter?.text = getString(R.string.convert_a_to_b,model.baseCurrencyName, model.quoteCurrencyName)


            }

        }
        return _getBindingFragmentMarketDetail?.root
    }

}