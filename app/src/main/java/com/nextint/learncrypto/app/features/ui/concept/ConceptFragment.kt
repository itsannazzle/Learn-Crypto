package com.nextint.learncrypto.app.features.ui.concept

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentConceptBinding
import com.nextint.learncrypto.app.features.tags.adapter.TagsViewHolder
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModel
import com.nextint.learncrypto.app.features.tags.viewmodel.TagsViewModelFactory
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.utils.BaseAdapter
import com.nextint.learncrypto.app.util.MODEL_PARCEL_TAG
import javax.inject.Inject

class ConceptFragment : Fragment()
{
    private var _binding : FragmentConceptBinding? = null
    private val _getBinding get() = _binding
    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse,TagsViewHolder>

    @Inject
    lateinit var _factoryTagsViewModel : TagsViewModelFactory
    private val _tagsViewModel : TagsViewModel by viewModels()
    {
        _factoryTagsViewModel
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
        _binding = FragmentConceptBinding.inflate(layoutInflater,container,false)
        _tagsViewModel.getTagById("cryptocurrency")
        _tagsViewModel.getAllTags()

        return _getBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCryptoDefinition()
        showHelpfullDefinition()
        setupTagAdapter()

    }

    private fun showCryptoDefinition()
    {
        _tagsViewModel.tagById.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        with(response.data)
                        {
                            _getBinding?.textViewWhatIs?.text = getString(R.string.what_is,name)
                            _getBinding?.textViewConceptCryptoDesc?.text = getString(R.string.coin_description,name,description)
                        }
                    }
                    is ApiResponse.Error -> _getBinding?.textViewConceptCryptoDesc?.text = response.message

                    else -> _getBinding?.textViewConceptCryptoDesc?.text = response.toString()
                }
            })
    }


    private fun showHelpfullDefinition()
    {
        _tagsViewModel.allTags.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        _tagsAdapter.safeClearAndAddAll(response.data.sortedBy { it.name  })

                        displayView()

                    }
                    is ApiResponse.Error -> Log.d("Anna","on api eror")

                    else -> Log.d("Anna","on eror")
                }
            })
    }

    private fun setupTagAdapter()
    {
        _tagsAdapter = BaseAdapter(
            {
                    parent, _ -> TagsViewHolder.inflate(parent)
            },
            {
                    viewHolder, position, item ->
                viewHolder.bind(item)
                viewHolder.setTagAction {
                    val bundle = Bundle()
                    bundle.putParcelable(MODEL_PARCEL_TAG,item)
                    val bottomSheetDialog = BottomSheetDialog()
                    bottomSheetDialog.arguments = bundle
                    bottomSheetDialog.show(parentFragmentManager,"TAG")
                }
            }
        )

    }

    private fun displayView()
    {
        _getBinding?.recylerViewHelpfullDefiniton?.apply()
        {
            adapter = _tagsAdapter
        }
    }

}