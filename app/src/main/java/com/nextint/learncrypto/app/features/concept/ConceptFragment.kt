package com.nextint.learncrypto.app.features.concept

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentConceptBinding
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewHolder
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModel
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.MODEL_PARCEL_TAG
import com.nextint.learncrypto.app.util.TAG_BOTTOM_SHEET_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG

class ConceptFragment : BaseFragment<TagsViewModel>()
{
    private var _bindingFragmentConcept : FragmentConceptBinding? = null
    private val _getBindingFragmentConcept get() = _bindingFragmentConcept

    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse, TagsViewHolder>
    private val _lazyTagsAdapter by lazy { _tagsAdapter }

    override fun setupViewModel(): Class<TagsViewModel> = TagsViewModel::class.java

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
        // Inflate the layout for this fragment
        _bindingFragmentConcept = FragmentConceptBinding.inflate(layoutInflater,container,false)
        _viewModel.getTagById(getString(R.string.id_cryptocurrency))
        _viewModel.getAllTags()
        _activityMain = activity as MainActivity
        _modelDialog = DialogModel()
        _activityMain._dialog.show()

        return _getBindingFragmentConcept?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeLiveData()

    }

    private fun observeLiveData()
    {
        _viewModel.tagById.observe(viewLifecycleOwner)
            {
                response ->
                when(response)
                {
                    is ApiResponse.InternetConnection ->
                    {
                        _modelDialog?.retryActionAlert = { _viewModel.getTagById(getString(R.string.id_cryptocurrency)) }
                        _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                        _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                        _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                    }

                    is ApiResponse.Success ->
                    {
                        _activityMain._dialog.hide()
                        with(response.data)
                        {
                            _getBindingFragmentConcept?.textViewConceptCryptoDesc?.text = getString(R.string.coin_description,
                                this?.name ?: "--", this?.description ?: "--"
                            )
                        }
                    }

                    is ApiResponse.Error ->
                    {
                        if (_dialogFragment.isAdded)
                        {
                            _viewModel.getTagById(getString(R.string.id_cryptocurrency))
                            _dialogFragment.dismiss()
                        }
                        else
                        {
                            _modelDialog?.buttonText = R.string.BUTTON_RETRY
                            _modelDialog?.httpErrorCode = response.message
                            _modelDialog?.retryActionDialog = {
                                _viewModel.getTagById(getString(R.string.id_cryptocurrency))
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
            }

        _viewModel.allTags.observe(viewLifecycleOwner)
            {
                    response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        response.data?.sortedBy { it.name  }
                            ?.let {
                                _lazyTagsAdapter.differ.submitList(it)
                            }

                        displayView()

                    }
                    is ApiResponse.Error -> Log.d("Anna","on api eror")

                    else -> Log.d("Anna","on eror")
                }
            }
    }




    private fun setupAdapter()
    {
        _tagsAdapter = BaseAdapter(
            { parent, _ -> TagsViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
                viewHolder.setTagAction()
                {
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
        _getBindingFragmentConcept?.recylerViewHelpfullDefiniton?.apply()
        {
            adapter = _lazyTagsAdapter
        }

        _getBindingFragmentConcept?.imageViewButtonBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingFragmentConcept = null
    }

}