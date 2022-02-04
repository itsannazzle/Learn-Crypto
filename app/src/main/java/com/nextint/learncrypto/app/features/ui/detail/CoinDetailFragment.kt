package com.nextint.learncrypto.app.features.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.TagsItem
import com.nextint.learncrypto.app.core.source.remote.response.TeamItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentCoinDetailBinding
import com.nextint.learncrypto.app.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModel
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModelFactory
import com.nextint.learncrypto.app.features.person.adapter.TeamViewHolder
import com.nextint.learncrypto.app.features.tags.adapter.TagsViewHolder
import com.nextint.learncrypto.app.features.ui.people.PeopleFragment
import com.nextint.learncrypto.app.features.utils.BaseAdapter
import com.nextint.learncrypto.app.features.utils.loadImage
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import com.nextint.learncrypto.app.util.ID_TAG_CONSTANT
import com.nextint.learncrypto.app.util.ID_TEAM_CONSTANT
import javax.inject.Inject

class CoinDetailFragment : Fragment()
{
    private var _binding : FragmentCoinDetailBinding? = null
    private val binding get() = _binding
    private lateinit var _teamAdapter : BaseAdapter<TeamItem,TeamViewHolder>
    private lateinit var _tagsAdapter : BaseAdapter<TagsItem,TagsViewHolder>

    @Inject
    lateinit var _factoryCoinViewModel : CoinsViewModelFactory
    private val _coinsViewModel : CoinsViewModel by viewModels()
    {
        _factoryCoinViewModel
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
        val idCoin = arguments?.getString(ID_COIN_CONSTANT)
        _binding = FragmentCoinDetailBinding.inflate(inflater,container,false)
        binding?.textViewCoinName?.text = idCoin
        _coinsViewModel.getCoinById(idCoin.toString())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        progressBarVisibility()
        getDetailCoin()
        setupTeamAdapter()
        setupTagsAdapter()
        displayView()
    }

    private fun getDetailCoin()
    { _coinsViewModel.coinById.observe(viewLifecycleOwner,
            { response ->
                when(response)
                { is ApiResponse.Success ->
                    { with(response.data)
                        {
                            binding?.textViewCoinName?.text = name
                            binding?.indicatorActive?.textViewStatus?.text = if (isActive) "Active" else "Inactive"
                            with(binding?.indicatorNew?.textViewStatus)
                            {
                                this?.visibility = setVisibility(isNew)
                                this?.text = if (isNew) "New" else "Not new"
                            }

                            binding?.indicatorOpenSource?.textViewStatus?.text = if (isOpenSource) "Open source" else "Private"
                            binding?.textViewSymbol?.text = getString(R.string.symbol, symbol)
                            binding?.textViewType?.text = getString(R.string.type,type)
                            binding?.textViewAboutCoin?.text = if (description.isEmpty()) "no desc" else description
                            binding?.textViewStarted?.text = startedAt
                            binding?.textViewFirstData?.text = firstDataAt
                            binding?.textViewLastData?.text = lastDataAt
                            binding?.textViewDevStats?.text = developmentStatus
                            binding?.textViewHardWallet?.text = if (isHardwareWallet) "Yes" else "No"
                            binding?.textViewProofType?.text = proofType
                            binding?.textViewOrganizationStatus?.text = orgStructure
                            binding?.textViewHasiAlgo?.text = hashAlgorithm
                            binding?.imageViewWhitePaper?.loadImage(response.data.whitepaper.thumbnail.toString())
                            binding?.textViewWhitePaperSrc?.text = whitepaper.link?.takeLast(43)
                            _teamAdapter.safeAddAll(team)
                            _tagsAdapter.safeAddAll(tags)
                        }
                    }
                    is ApiResponse.Empty -> Toast.makeText(requireContext(),"Data empty",Toast.LENGTH_LONG).show()
                    is ApiResponse.Error -> Toast.makeText(requireContext(),response.message,Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun progressBarVisibility()
    {
        _coinsViewModel.loading.observe(viewLifecycleOwner,
            {
                binding?.progressBarDetail?.visibility = setVisibility(it)
            })
    }

    private fun setupTeamAdapter()
    {
        _teamAdapter = BaseAdapter(
            {
                    parent, _ -> TeamViewHolder.inflate(parent)
            },
            {
                    viewHolder, _, item ->
                viewHolder.bind(item)
                viewHolder.setTeamAction {
                    val bundle = Bundle()
                    bundle.putString(ID_TEAM_CONSTANT,item.id)
                    replaceFragment(parentFragmentManager,PeopleFragment(),bundle)
                }
            }
        )
    }

    private fun setupTagsAdapter()
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
                    bundle.putString(ID_TAG_CONSTANT,item.id)
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
        binding?.indicatorNew?.cardStatus?.setCardBackgroundColor(Color.GREEN)
        binding?.indicatorOpenSource?.cardStatus?.setCardBackgroundColor(Color.parseColor("#F09204"))
    }


    private fun displayView()
    {
        setupIndicatorColor()
        binding?.recylerViewTeam?.apply {
            adapter = _teamAdapter
        }
        binding?.recyclerViewTags?.apply {
            adapter = _tagsAdapter
        }
    }
}