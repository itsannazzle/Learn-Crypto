package com.nextint.learncrypto.app.features.ui.exchanges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentExchangesBinding
import com.nextint.learncrypto.app.features.exchanges.adapter.ExchangeViewHolder
import com.nextint.learncrypto.app.features.exchanges.viewmodel.ExchangeViewModel
import com.nextint.learncrypto.app.features.exchanges.viewmodel.ExchangeViewModelFactory
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModel
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModelFactory
import com.nextint.learncrypto.app.features.ui.coins.CoinDetailFragment
import com.nextint.learncrypto.app.features.ui.home.HomeFragment
import com.nextint.learncrypto.app.features.utils.*
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import com.nextint.learncrypto.app.util.ID_EXCHANGE_CONSTANT
import timber.log.Timber
import javax.inject.Inject


class ExchangesFragment : Fragment() {

    private var _bindingExchangeFragment : FragmentExchangesBinding? = null
    private val _getBindingExchangeFragment get() = _bindingExchangeFragment
    private lateinit var _exchangeAdapter : BaseAdapter<ExchangesResponseItem, ExchangeViewHolder>
    private val _fragmentHome = HomeFragment()

    @Inject
    lateinit var _factoryExchangeViewModel : ExchangeViewModelFactory
    private val _exchangeViewModel : ExchangeViewModel by viewModels()
    {
        _factoryExchangeViewModel
    }

    @Inject
    lateinit var _factoryTagsViewModel : TagsViewModelFactory
    private val _tagsViewModel : TagsViewModel by viewModels()
    {
        _factoryTagsViewModel
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
        _bindingExchangeFragment = FragmentExchangesBinding.inflate(inflater,container,false)
        _exchangeViewModel.requestGetAllExchange()
        _tagsViewModel.getTagById("exchange")
        setupAdapter()
        observeLoading()
        return _getBindingExchangeFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeExchange()
        observeTag()



    }

    private fun observeExchange()
    {
        _exchangeViewModel.getAllExchange.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                       _exchangeAdapter.safeClearAndAddAll(response.data)
                        setupView()
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
    }

    private fun observeTag()
    {
        _tagsViewModel.tagById.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success -> {
                        _getBindingExchangeFragment?.textViewExchangeDesc?.text = response.data.description
                        _getBindingExchangeFragment?.textViewWhatIs?.text = getString(R.string.what_is, response.data.name)
                    }
                    is ApiResponse.Error ->
                    {
                        Timber.d(response.message)
                    }
                    is ApiResponse.Empty ->
                    {
                        Timber.d("empty")
                    }
                }
            })
    }

    private fun observeLoading()
    {
        _exchangeViewModel.loading.observe(viewLifecycleOwner,
            {

            })
    }

    private fun setupAdapter()
    {
        _exchangeAdapter = BaseAdapter({
                parent, _ -> ExchangeViewHolder.inflate(parent)
        },{
                viewHolder, _, item ->
            viewHolder.bind(item)
            viewHolder.setAction {
                val bundle = Bundle()
                bundle.putString(ID_EXCHANGE_CONSTANT,item.id)
                UtilitiesFunction.replaceFragment(parentFragmentManager, ExchangeDetailFragment(),bundle)
            }
        })
    }

    private fun setupView()
    {
        _getBindingExchangeFragment?.recyclerViewExchange?.apply {
            setVertical()
            adapter = _exchangeAdapter
        }
    }







}