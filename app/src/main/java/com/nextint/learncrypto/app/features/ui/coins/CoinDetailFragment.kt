package com.nextint.learncrypto.app.features.ui.coins

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
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.TeamItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentCoinDetailBinding
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModel
import com.nextint.learncrypto.app.features.coins.viewmodel.CoinsViewModelFactory
import com.nextint.learncrypto.app.features.person.adapter.TeamViewHolder
import com.nextint.learncrypto.app.features.tags.adapter.TagsViewHolder
import com.nextint.learncrypto.app.features.ui.people.PeopleFragment
import com.nextint.learncrypto.app.features.ui.webview.WebViewFragment
import com.nextint.learncrypto.app.features.utils.BaseAdapter
import com.nextint.learncrypto.app.features.utils.loadImage
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.*
import timber.log.Timber
import javax.inject.Inject

class CoinDetailFragment : Fragment()
{
    private var _binding : FragmentCoinDetailBinding? = null
    private val binding get() = _binding
    private lateinit var _teamAdapter : BaseAdapter<TeamItem,TeamViewHolder>
    private lateinit var _tagsAdapter : BaseAdapter<TagByIdResponse,TagsViewHolder>

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
        progressBarVisibility()
        _coinsViewModel.getCoinById(idCoin.toString())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        getDetailCoin()
        setupTeamAdapter()
        setupTagsAdapter()

    }

    private fun getDetailCoin()
    {
        _coinsViewModel.coinById.observe(viewLifecycleOwner,
            { response ->
                when(response)
                { is ApiResponse.Success ->
                    { with(response.data)
                        {
                            binding?.textViewCoinName?.text = name
                            binding?.textViewCoinSocMed?.text = getString(R.string.find_out_more_about_1_s_on,name)
                            binding?.indicatorActive?.textViewStatus?.text = if (isActive) "Active" else "Inactive"
                            with(binding?.indicatorNew?.textViewStatus)
                            {
                                this?.visibility = setVisibility(isNew)
                                this?.text = if (isNew) "New" else "Not new"
                            }

                            binding?.indicatorOpenSource?.textViewStatus?.text = if (isOpenSource) "Open source" else "Private Source"
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
                            binding?.textViewWhitePaperSrc?.text = whitepaper.link?.takeLast(10)
                            binding?.imageViewWhitePaper?.setOnClickListener {
                                //need pdf viewer
                                val bundle = Bundle()
                                bundle.putString(BUNDLE_WEB_URL,whitepaper.link)
                                replaceFragment(parentFragmentManager,WebViewFragment(),bundle)
                            }
                            _teamAdapter.safeAddAll(team)
                            _tagsAdapter.safeAddAll(tags)
                            displayView()
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
                Timber.d(it.toString())
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