package com.nextint.learncrypto.app.bases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.databinding.FragmentBaseBlankBinding
import com.nextint.learncrypto.app.databinding.FragmentMarketBinding
import com.nextint.learncrypto.app.features.market.MarketDetailFragment
import com.nextint.learncrypto.app.features.market.presentation.MarketViewHolder
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.util.KEY_BUNDLE_DATA
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID


class BaseBlankFragment : Fragment()
{
    private lateinit var _bitcoinMarketAdapter : BaseAdapter<MarketsByCoinIdResponseItem, MarketViewHolder>
    private var _bindingBlankFragment : FragmentBaseBlankBinding? = null
    private val _getBindingBlankFragment get() = _bindingBlankFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _bindingBlankFragment = FragmentBaseBlankBinding.inflate(layoutInflater,container,false)

        return _getBindingBlankFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupView()
        val getBundle = arguments?.getParcelableArrayList<MarketsByCoinIdResponseItem>(KEY_BUNDLE_DATA)
        getBundle
        if (getBundle != null) {
            _bitcoinMarketAdapter.safeClearAndAddAll(getBundle)
        }
    }

    private fun setupAdapter()
    {
        _bitcoinMarketAdapter = BaseAdapter(
            { parent, _ -> MarketViewHolder.inflate(parent) },
            { viewHolder, position, item -> viewHolder.bind(item)
                viewHolder.setAction()
                {
                    val bundle = Bundle()
                    bundle.putParcelable(MODEL_PARCEL_MARKET_BY_ID,item)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, MarketDetailFragment(),bundle)
                }
            }
        )
    }

    private fun setupView()
    {
        _getBindingBlankFragment?.rv?.apply()
        {
            adapter = _bitcoinMarketAdapter
        }
    }

}