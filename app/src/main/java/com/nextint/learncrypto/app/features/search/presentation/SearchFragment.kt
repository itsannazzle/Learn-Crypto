package com.nextint.learncrypto.app.features.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.nextint.learncrypto.app.bases.BaseDialogFragment
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log


class SearchFragment : BaseFragment<SearchViewModel>() {
    private var _bindingSearchFragment : FragmentSearchBinding? = null
    private val _getBindingSearchFragment get() = _bindingSearchFragment
    private var _stringKeyword : String? = null
    private var jobDebounce: Job? = null
    private lateinit var _coinAdapter : BaseAdapter<CoinsResponseItem, CoinViewHolder>
    private lateinit var _exchangesAdapter : BaseAdapter<ExchangesResponseItem, ExchangeViewHolder>
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
                            _tagsAdapter.clear()
                            _coinAdapter.clear()
                            _teamAdapter.clear()
                            _exchangesAdapter.clear()
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
                                _tagsAdapter.clear()
                                _coinAdapter.clear()
                                _teamAdapter.clear()
                                _exchangesAdapter.clear()
                                observeLiveData()
                                setupAdapter()
                                displayView()
                            } else
                            {
                                _activityMain._dialog.hide()
                                _tagsAdapter.clear()
                                _coinAdapter.clear()
                                _teamAdapter.clear()
                                _exchangesAdapter.clear()
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
                    _modelDialog?.retryActionAlert = { _viewModel.searchWithKeyword(_getBindingSearchFragment?.editTextSearch.toString() ?: "") }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)
                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                is ApiResponse.Success ->
                {
                    _activityMain._dialog.hide()
                   response.data?.let()
                   {
                       with(_getBindingSearchFragment)
                       {
                           this?.imageViewSearchDefault?.isVisible = false
                           this?.tvResult?.isVisible = false
                           val data = it
                           if (data.tags.isNotEmpty()) _tagsAdapter.safeClearAndAddAll(data.tags)
                           this?.tvTags?.isVisible = data.tags.isNotEmpty()

                           if (data.exchanges.isNotEmpty()) _exchangesAdapter.safeClearAndAddAll(data.exchanges)
                           this?.tvExchanges?.isVisible = data.exchanges.isNotEmpty()

                           if (data.currencies.isNotEmpty()) _coinAdapter.safeClearAndAddAll(data.currencies)
                           this?.tvCurrencies?.isVisible = data.currencies.isNotEmpty()

                           if (data.people.isNotEmpty()) _teamAdapter.safeClearAndAddAll(data.people)
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