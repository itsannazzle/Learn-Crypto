package com.nextint.learncrypto.app.features.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentBottomSheetDialogBinding
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModel
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModelFactory
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.ID_TAG_CONSTANT
import timber.log.Timber
import javax.inject.Inject


class BottomSheetDialog : BottomSheetDialogFragment()
{
    private var _binding : FragmentBottomSheetDialogBinding? = null
    private val _getBinding get() = _binding

    @Inject
    lateinit var _factoryTagViewModel : TagsViewModelFactory
    private val _tagViewModel : TagsViewModel by viewModels()
    {
        _factoryTagViewModel
    }

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
        _binding = FragmentBottomSheetDialogBinding.inflate(layoutInflater,container,false)
        val idTag = arguments?.getString(ID_TAG_CONSTANT)
        showLoading()
        if (idTag != null)
        {
            _tagViewModel.getTagById(idTag)
        } else
        {
            Toast.makeText(requireContext(),"Could not found tag id",Toast.LENGTH_SHORT).show()
        }
        return _getBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagDetail()
    }

    private fun getTagDetail()
    {
        _tagViewModel.tagById.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        with(_getBinding)
                        {
                            this?.textViewTagName?.text = response.data.name
                            this?.textViewTagDesc?.text = response.data.description
                        }
                    }
                    is ApiResponse.Error ->
                    {
                        with(_getBinding)
                        {
                            this?.textViewTagName?.text = response.message
                        }
                    }
                    else ->
                    {
                        with(_getBinding)
                        {
                            this?.textViewTagName?.text = response.toString()
                        }
                    }
                }
            })
    }

    private fun showLoading()
    {
        _tagViewModel.loading.observe(viewLifecycleOwner,
            {
                _getBinding?.progressBarBottomSheet?.visibility =  setVisibility(it)
            })
    }

    override fun getTheme(): Int {
        return R.style.BottomDialogTheme
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}