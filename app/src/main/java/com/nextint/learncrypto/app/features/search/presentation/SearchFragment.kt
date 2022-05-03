package com.nextint.learncrypto.app.features.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.*
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
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
import com.nextint.learncrypto.app.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<SearchViewModel>() {
    private var _bindingSearchFragment : FragmentSearchBinding? = null
    private val _getBindingSearchFragment get() = _bindingSearchFragment
    private var _stringKeyword : String? = null
    private var jobDebounce: Job? = null
    private lateinit var _coinAdapter : BaseAdapter<CoinsResponseItem, CoinViewHolder>
    private lateinit var _exchangesAdapter : BaseAdapter<ExchangesResponseItem, ExchangeViewHolder>
    private lateinit var _icosAdapter : BaseAdapter<TagByIdResponse, TagsViewHolder>
    private lateinit var _teamAdapter : BaseAdapter<TeamItem, TeamViewHolder>
    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse, TagsViewHolder>

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
        with(_getBindingSearchFragment)
        {
            this?.tvCurrencies?.isVisible = false
            this?.tvExchanges?.isVisible = false
            this?.tvICOS?.isVisible = false
            this?.tvPeople?.isVisible = false
            this?.tvTags?.isVisible = false
        }
        return _getBindingSearchFragment?.root
    }

    override fun onResume() {
        super.onResume()
        setupAdapter()
        displayView()
        with(_getBindingSearchFragment!!)
        {
            val textWatcher = object : TextWatcher
            {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
                {
                    _stringKeyword = p0.toString()
//                    jobDebounce?.cancel()
//                    jobDebounce = lifecycleScope.launch(Dispatchers.Main)
                    if (!_stringKeyword.isNullOrBlank() || !_stringKeyword.isNullOrEmpty())
                    {
                        _viewModel.searchWithKeyword(_stringKeyword ?: "")
                    }



                }

                override fun afterTextChanged(p0: Editable?) { }
            }
            editTextSearch.addTextChangedListener(textWatcher)
        }
    }

    override fun setupViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun setObserver(): Fragment = this


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()

    }


    private fun observeLiveData()
    {
        _viewModel.searchResponse.observe(viewLifecycleOwner)
        { response ->
            when(response)
            {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.searchWithKeyword(_getBindingSearchFragment?.editTextSearch.toString() ?: "") }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = R.string.dialog_no_internet_message
                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                is ApiResponse.Success ->
                {
                   response.data?.let()
                   {
                       with(_getBindingSearchFragment)
                       {
                           this?.tvCurrencies?.isVisible = true
                           this?.tvExchanges?.isVisible = true
                           this?.tvICOS?.isVisible = true
                           this?.tvPeople?.isVisible = true
                           this?.tvTags?.isVisible = true
                       }
                       val data = response.data
                       _tagsAdapter.safeClearAndAddAll(data.tags)
                       _coinAdapter.safeClearAndAddAll(data.currencies)
                       _teamAdapter.safeClearAndAddAll(data.people)
                       _exchangesAdapter.safeClearAndAddAll(data.exchanges)
                   }
                }
                is ApiResponse.Error ->
                {
                    response.message
                }
            }
        }

        _viewModel.message.observe(viewLifecycleOwner)
        {
            it
        }
    }
    
    private fun setupAdapter()
    {
       _coinAdapter = BaseAdapter(
           { parent: ViewGroup, _: Int -> CoinViewHolder.inflate(parent)},
           {viewHolder, position, item -> viewHolder.bind(item)
               viewHolder.setAction {
                   val bundle = Bundle()
                   bundle.putString(ID_COIN_CONSTANT,item.id)
                   UtilitiesFunction.replaceFragment(parentFragmentManager, CoinDetailFragment(),bundle)
               }
           }
       )

        _exchangesAdapter = BaseAdapter(
            {parent, viewType -> ExchangeViewHolder.inflate(parent) },
            {viewHolder, position, item -> viewHolder.bind(item)
                viewHolder.setAction {
                    val bundle = Bundle()
                    bundle.putString(ID_EXCHANGE_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, ExchangeDetailFragment(),bundle)
                }
            }
        )

        _teamAdapter = BaseAdapter(
            {parent, viewType -> TeamViewHolder.inflate(parent) },
            {viewHolder, position, item -> viewHolder.bind(item)
                viewHolder.setTeamAction {
                    val bundle = Bundle()
                    bundle.putString(ID_TEAM_CONSTANT,item.id)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, PeopleFragment(),bundle)
                }
            }
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
            }
        )
    }

    private fun displayView()
    {
        with(_getBindingSearchFragment)
        {
            this?.rvSearchCurrencies.apply()
            {
               this?.setHorizontal()
               this?.adapter = _coinAdapter
            }

            this?.rvSearchExchanges.apply()
            {
               this?.setHorizontal()
               this?.adapter = _exchangesAdapter
            }
            this?.rvSearchPeople.apply()
            {
               this?.setHorizontal()
               this?.adapter = _teamAdapter
            }

            this?.rvSearchTags.apply()
            {
               this?.setHorizontal()
               this?.adapter = _tagsAdapter
            }
        }
    }
}