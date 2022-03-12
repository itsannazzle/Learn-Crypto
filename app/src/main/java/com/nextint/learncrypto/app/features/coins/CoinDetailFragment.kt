package com.nextint.learncrypto.app.features.coins

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.TeamItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentCoinDetailBinding
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModel
import com.nextint.learncrypto.app.features.person.presentation.TeamViewHolder
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewHolder
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.person.PeopleFragment
import com.nextint.learncrypto.app.features.ui.webview.WebViewFragment
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.loadImage
import com.nextint.learncrypto.app.util.*

class CoinDetailFragment : BaseFragment<CoinsViewModel>()
{
    private var _bindingCoinDetailFragment : FragmentCoinDetailBinding? = null
    private val _getBindingCoinDetailFragment get() = _bindingCoinDetailFragment
    private var _coinId : String? = null

    private lateinit var _teamAdapter : BaseAdapter<TeamItem, TeamViewHolder>
    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse, TagsViewHolder>

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
        _coinId = arguments?.getString(ID_COIN_CONSTANT)
        _bindingCoinDetailFragment= FragmentCoinDetailBinding.inflate(inflater,container,false)
        _modelDialog = DialogModel()
        _activityMain = activity as MainActivity
        _viewModel.getCoinById(_coinId ?: "")
        _activityMain._dialog.show()
        return _getBindingCoinDetailFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeLiveData()

    }

    private fun observeLiveData()
    {
        _viewModel.coinById.observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                is ApiResponse.InternetConnection -> {
                    _modelDialog?.retryActionAlert = { _viewModel.getCoinById(_coinId ?: "") }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = R.string.dialog_no_internet_message

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }

                is ApiResponse.Success -> {
                    _activityMain._dialog.hide()

                    with(response.data)
                    {
                        _teamAdapter.safeAddAll(team)
                        _tagsAdapter.safeAddAll(tags)
                        displayView(this)
                    }
                }

                is ApiResponse.Empty -> {
                    _modelDialog?.httpErrorCode = 1404
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }

                is ApiResponse.Error -> {
                    if (_dialogFragment.isAdded) {
                        _viewModel.getCoinById(_coinId ?: "")
                        _dialogFragment.dismiss()
                    } else {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog = {
                            _viewModel.getCoinById(_coinId ?: "")
                        }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                }

                else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            }
        }



        _viewModel.message.observe(viewLifecycleOwner
        ) {
            _modelDialog?.dialogMessage = id
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
            _dialogFragment.arguments = bundle
            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
        }
    }


    private fun setupAdapter()
    {
        _teamAdapter = BaseAdapter(
            { parent, _ -> TeamViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
                viewHolder.setTeamAction()
                {
                    val bundle = Bundle()
                    bundle.putString(ID_TEAM_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, PeopleFragment(),bundle)
                }
            }
        )

        _tagsAdapter = BaseAdapter(
            {
                    parent, _ -> TagsViewHolder.inflate(parent)
            },
            {
                    viewHolder, position, item ->
                viewHolder.bind(item)
                viewHolder.setTagAction {
                    val bundle = Bundle()
                    bundle.putString(STRING_TAG_ID_CONSTANT,item.id)
                    val bottomSheetDialog = BottomSheetDialog()
                    bottomSheetDialog.arguments = bundle
                    bottomSheetDialog.show(parentFragmentManager,"TAG")
                }
            }
        )
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupIndicatorColor()
    {
        _getBindingCoinDetailFragment?.indicatorNew?.cardStatus?.setCardBackgroundColor(Color.GREEN)
        _getBindingCoinDetailFragment?.indicatorOpenSource?.cardStatus?.setCardBackgroundColor(Color.parseColor("#F09204"))
    }


    private fun displayView(coinByIdResponse: CoinByIdResponse)
    {
        setupIndicatorColor()
        _getBindingCoinDetailFragment?.recylerViewTeam?.apply()
        {
            adapter = _teamAdapter
        }
        _getBindingCoinDetailFragment?.recyclerViewTags?.apply()
        {
            adapter = _tagsAdapter
        }

        with(_getBindingCoinDetailFragment!!)
        {
            coinByIdResponse.apply()
            {
                textViewCoinName.text = name
                textViewCoinSocMed.text = getString(R.string.find_out_more_about_1_s_on,name)
                indicatorActive.textViewStatus.text = getString(UtilitiesFunction.convertBooleanToActiveOrNotActive(isActive))
                with(_getBindingCoinDetailFragment?.indicatorNew?.textViewStatus)
                {
                    this?.visibility = UtilitiesFunction.setVisibility(isNew)
                    this?.text = getString(UtilitiesFunction.convertBooleanToNew(isNew))
                }

                indicatorOpenSource.textViewStatus.text = getString(UtilitiesFunction.convertBooleanToOpenSource(isOpenSource))
                textViewSymbol.text = getString(R.string.symbol, symbol)
                textViewType.text = getString(R.string.type,type)
                textViewAboutCoin.text = if (description.isEmpty()) getString(R.string.desc_not_found) else description
                textViewStarted.text = startedAt
                textViewFirstData.text = firstDataAt
                textViewLastData.text = lastDataAt
                textViewDevStats.text = developmentStatus
                textViewHardWallet.text = getString(UtilitiesFunction.convertBooleanToYesOrNo(isHardwareWallet))
                textViewProofType.text = proofType
                textViewOrganizationStatus.text = orgStructure
                textViewHasiAlgo.text = hashAlgorithm
                imageViewWhitePaper.loadImage(whitepaper.thumbnail.toString())
                imageViewWhitePaper.setOnClickListener()
                {

                }
                textViewWhitePaperSrc.text = whitepaper.link?.takeLast(10)

                imageViewWhitePaper.setOnClickListener()
                {
                    //need pdf viewer
                    val bundle = Bundle()
                    bundle.putString(BUNDLE_WEB_URL,whitepaper.link)
                    UtilitiesFunction.replaceFragment(parentFragmentManager,
                        WebViewFragment(),bundle)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingCoinDetailFragment = null
    }
}