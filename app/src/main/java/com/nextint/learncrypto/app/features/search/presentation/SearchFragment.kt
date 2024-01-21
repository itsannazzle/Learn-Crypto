package com.nextint.learncrypto.app.features.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseDialogFragment
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.TeamItem
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentSearchBinding
import com.nextint.learncrypto.app.features.coins.CoinDetailFragment
import com.nextint.learncrypto.app.features.coins.presentation.CoinViewHolder
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewHolder
import com.nextint.learncrypto.app.features.exchanges.ExchangeDetailFragment
import com.nextint.learncrypto.app.features.exchanges.presentation.ExchangeViewHolder
import com.nextint.learncrypto.app.features.person.PeopleFragment
import com.nextint.learncrypto.app.features.person.presentation.TeamViewHolder
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.setHorizontal
import com.nextint.learncrypto.app.util.EnumConstants
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import com.nextint.learncrypto.app.util.ID_EXCHANGE_CONSTANT
import com.nextint.learncrypto.app.util.ID_TEAM_CONSTANT
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.MODEL_PARCEL_TAG
import com.nextint.learncrypto.app.util.TAG_BOTTOM_SHEET_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<SearchViewModel>() {
    private var _bindingSearchFragment : FragmentSearchBinding? = null
    private val _getBindingSearchFragment get() = _bindingSearchFragment
    private var _stringKeyword : String? = null
    private var jobDebounce: Job? = null
    private lateinit var _coinAdapter : BaseAdapter<CoinsResponseItem, CoinViewHolder>
    private lateinit var _exchangesAdapter : BaseAdapter<ExchangesResponseItem, ExchangeViewHolder>
    private lateinit var _teamAdapter : BaseAdapter<TeamItem, TeamViewHolder>
    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse, TagsViewHolder>
    private val _lazyCoinAdapter by lazy { _coinAdapter }
    private val _lazyExchangeAdapter by lazy { _exchangesAdapter }
    private val _lazyTeamAdapter by lazy { _teamAdapter }
    private val _lazyTagsAdapter by lazy { _tagsAdapter }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingSearchFragment = FragmentSearchBinding.inflate(layoutInflater,container,false)
        _activityMain = activity as MainActivity
        _modelDialog = DialogModel()
        _dialogFragment = BaseDialogFragment()
        with(_getBindingSearchFragment)
        {
            this?.imageViewButtonBack?.setOnClickListener()
            {
                parentFragmentManager.popBackStack()
            }
        }
        return _getBindingSearchFragment?.root
    }


    override fun onResume() {
        super.onResume()
        val keyword = _getBindingSearchFragment?.editTextSearch?.text.toString()
        if (!keyword.isNullOrEmpty())
        {
            keyword.let { _viewModel.searchWithKeyword(it) }
            observeLiveData()
            setupAdapter()
            displayView()
        } else
        {
            with(_getBindingSearchFragment!!)
            {
                val textWatcher = object : TextWatcher
                {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
                    {
                        if (p3 > 3)
                        {

                        }
                        _stringKeyword = p0.toString()

                        jobDebounce?.cancel()
                        jobDebounce = lifecycleScope.launch(Dispatchers.Main)
                        {
                            delay(1000)
                            if (!_stringKeyword.isNullOrBlank() || !_stringKeyword.isNullOrEmpty())
                            {
                                _viewModel.searchWithKeyword(_stringKeyword!!)
                                _activityMain._dialog.show()
                                observeLiveData()
                                setupAdapter()
                                displayView()
                            } else
                            {
                                _activityMain._dialog.hide()
                            }
                        }
                    }

                    override fun afterTextChanged(p0: Editable?) { }
                }

                editTextSearch.addTextChangedListener(textWatcher)
            }
        }

    }

    override fun setupViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun setObserver(): Fragment = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }


    private fun observeLiveData()
    {
        _viewModel.searchResponse.observe(viewLifecycleOwner)
        { response ->
            when(response)
            {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.searchWithKeyword(
                        _getBindingSearchFragment?.editTextSearch.toString()
                    ) }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)
                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                is ApiResponse.Success ->
                {
                    _tagsAdapter.notifyDataSetChanged()

                    _activityMain._dialog.hide()
                   response.data?.let()
                   {
                       with(_getBindingSearchFragment)
                       {
                           this?.imageViewSearchDefault?.isVisible = false
                           this?.tvResult?.isVisible = false
                           val data = it
                            _lazyTagsAdapter.differ.submitList(data.tags)
                           this?.tvTags?.isVisible = data.tags.isNotEmpty()

                           _lazyExchangeAdapter.differ.submitList(data.exchanges)
                           this?.tvExchanges?.isVisible = data.exchanges.isNotEmpty()

                           _lazyCoinAdapter.differ.submitList(data.currencies)
                           this?.tvCurrencies?.isVisible = data.currencies.isNotEmpty()

                           _lazyTeamAdapter.differ.submitList(data.people)
                           this?.tvPeople?.isVisible = data.people.isNotEmpty()

                           data.let {
                               if (it.people.isEmpty() && it.exchanges.isEmpty() && it.icos.isEmpty() && it.currencies.isEmpty() && it.tags.isEmpty())
                               {
                                   this?.imageViewSearchDefault?.isVisible = true
                                   this?.tvResult?.isVisible = true
                                   this?.imageViewSearchDefault?.setImageResource(R.drawable.ic_search_not_found)
                                   this?.tvResult?.text = getString(R.string.search_notfound)
                               }
                           }
                       }
                   }
                }
                is ApiResponse.Error ->
                {
                    _activityMain._dialog.hide()
                    _modelDialog?.httpErrorCode = response.message
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)

                }
            }
        }

        _viewModel.message.observe(viewLifecycleOwner)
        {
            _activityMain._dialog.hide()
            _modelDialog?.dialogMessage = it
            _modelDialog?.httpErrorCode = 422
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
            _dialogFragment.arguments = bundle
            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
        }
    }
    
    private fun setupAdapter()
    {
       _coinAdapter = BaseAdapter(
           { parent: ViewGroup, _: Int -> CoinViewHolder.inflate(parent)},
           {viewHolder, position, item -> viewHolder.bind(item,EnumConstants.ENUM_SOURCE_VIEW.SEARCH)
               viewHolder.setAction {
                   val bundle = Bundle()
                   bundle.putString(ID_COIN_CONSTANT,item.id)
                   UtilitiesFunction.replaceFragment(parentFragmentManager, CoinDetailFragment(),bundle)
               }
           },
           CoinViewHolder.differCallback
       )

        _exchangesAdapter = BaseAdapter(
            {parent, viewType -> ExchangeViewHolder.inflate(parent) },
            {viewHolder, position, item -> viewHolder.bind(item,EnumConstants.ENUM_SOURCE_VIEW.SEARCH)
                viewHolder.setAction {
                    val bundle = Bundle()
                    bundle.putString(ID_EXCHANGE_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, ExchangeDetailFragment(),bundle)
                }
            },
            ExchangeViewHolder.differCallback
        )

        _teamAdapter = BaseAdapter(
            {parent, viewType -> TeamViewHolder.inflate(parent) },
            {viewHolder, position, item -> viewHolder.bind(item)
                viewHolder.setTeamAction {
                    val bundle = Bundle()
                    bundle.putString(ID_TEAM_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, PeopleFragment(),bundle)
                }
            },
            TeamViewHolder.differCallback
        )

        _tagsAdapter = BaseAdapter(
            {parent, viewType -> TagsViewHolder.inflate(parent) },
            {viewHolder, position, item -> viewHolder.bind(item)
                viewHolder.setTagAction {
                    val bundle = Bundle()
                    bundle.putParcelable(MODEL_PARCEL_TAG,item)
                    val bottomSheetDialog = BottomSheetDialog()
                    bottomSheetDialog.arguments = bundle
                    bottomSheetDialog.show(parentFragmentManager, TAG_BOTTOM_SHEET_DIALOG)
                }
            },
            TagsViewHolder.differCallback
        )
    }

    private fun displayView()
    {

        with(_getBindingSearchFragment)
        {
            this?.rvSearchCurrencies.apply()
            {
               this?.setHorizontal()
               this?.adapter = _lazyCoinAdapter
            }

            this?.rvSearchExchanges.apply()
            {
               this?.setHorizontal()
               this?.adapter = _lazyExchangeAdapter
            }

            this?.rvSearchPeople.apply()
            {
               this?.setHorizontal()
               this?.adapter = _lazyTeamAdapter
            }

            this?.rvSearchTags.apply()
            {
               this?.setHorizontal()
               this?.adapter = _lazyTagsAdapter
            }
        }
    }

}