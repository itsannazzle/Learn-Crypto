package com.nextint.learncrypto.app.features.coins

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseDialogFragment
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentCoinsBinding
import com.nextint.learncrypto.app.features.coins.presentation.CoinViewHolder
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.setVertical
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG


class CoinsFragment : BaseFragment<CoinsViewModel>() 
{
    private var _bindingCoinsFragment : FragmentCoinsBinding? = null
    private val _getbindingCoinsFragment get() = _bindingCoinsFragment


    private lateinit var _coinAdapter : BaseAdapter<CoinsResponseItem, CoinViewHolder>

    override fun setupViewModel(): Class<CoinsViewModel> = CoinsViewModel::class.java

    override fun setObserver(): Fragment = this

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
        _bindingCoinsFragment = FragmentCoinsBinding.inflate(layoutInflater,container,false)
        _activityMain = activity as MainActivity
        _viewModel.getCoins()
        _modelDialog = DialogModel()
        _dialogFragment = BaseDialogFragment()
        _activityMain._dialog.show()
        return _getbindingCoinsFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        displayView()
        observeLiveData()
    }

    private fun setupAdapter()
    {
        _coinAdapter = BaseAdapter(
            { parent, _ -> CoinViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
                viewHolder.setAction {
                    val bundle = Bundle()
                    bundle.putString(ID_COIN_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, CoinDetailFragment(),bundle)
                }
            }
        )
    }

    private fun observeLiveData()
    {
        _viewModel.coins.observe(viewLifecycleOwner,
            { response ->
                when(response)
                {
                    is ApiResponse.InternetConnection ->
                    {
                        _modelDialog?.retryActionAlert = { _viewModel.getCoins() }
                        _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                        _modelDialog?.dialogMessage = R.string.dialog_no_internet_message

                        _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                    }

                    is ApiResponse.Success ->
                    {
                        _activityMain._dialog.hide()
                        _coinAdapter.safeClearAndAddAll(response.data)
                    }

                    is ApiResponse.Error ->
                    {
                        if (_dialogFragment.isAdded)
                        {
                            _viewModel.getCoins()
                            _dialogFragment.dismiss()
                        }
                        else
                        {
                            _modelDialog?.buttonText = R.string.BUTTON_RETRY
                            _modelDialog?.httpErrorCode = response.message
                            _modelDialog?.retryActionDialog = {
                                _viewModel.getCoins()
                            }
                            val bundle = Bundle()
                            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                            _dialogFragment.arguments = bundle
                            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                        }
                    }
                    is ApiResponse.Empty ->
                    {
                        _modelDialog?.httpErrorCode = 1404
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                    else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)

                }
            })

        _viewModel.message.observe(viewLifecycleOwner,
            {
            _modelDialog?.dialogMessage = id
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
            _dialogFragment.arguments = bundle
            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
        })

//        _viewModel.loading.observe(viewLifecycleOwner,
//            {
//            _bindingCoinsFragment?.progressBar?.visibility = UtilitiesFunction.setVisibility(it)
//        })
    }

    private fun displayView()
    {
        _bindingCoinsFragment?.recylerViewCoins.apply {
            this?.setVertical()
            this?.adapter = _coinAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingCoinsFragment = null
    }


}