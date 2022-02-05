package com.nextint.learncrypto.app.features.ui.people

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentPeopleBinding
import com.nextint.learncrypto.app.features.person.viewmodel.PeopleViewModel
import com.nextint.learncrypto.app.features.person.viewmodel.PeopleViewModelFactory
import com.nextint.learncrypto.app.features.ui.webview.WebViewFragment
import com.nextint.learncrypto.app.features.utils.circleImage
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.BUNDLE_WEB_URL
import com.nextint.learncrypto.app.util.ID_TEAM_CONSTANT
import com.nextint.learncrypto.app.util.STRING_URL_AVATAR_APE
import timber.log.Timber
import javax.inject.Inject


class PeopleFragment : Fragment()
{
    private var _binding : FragmentPeopleBinding? = null
    private val _getBinding get() = _binding

    @Inject
    lateinit var _factoryPeopleViewModel : PeopleViewModelFactory
    private val _peopleViewModel : PeopleViewModel by viewModels()
    {
        _factoryPeopleViewModel
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
        _binding = FragmentPeopleBinding.inflate(layoutInflater, container, false)
        val stringPeopleId = arguments?.getString(ID_TEAM_CONSTANT) ?: "null"
        _peopleViewModel.getPeopleById(stringPeopleId)
        progressBarVisibility()
        return _getBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPeopleById()
    }

    private fun getPeopleById()
    {
        _peopleViewModel.peopleById.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        with(response.data)
                        {
                            _getBinding?.apply {
                                textViewPeopleName.text = name
                                textViewPeopleDesc.text = description ?: "Description not available"
                                textViewPeopleTotalPosition.text = getString(R.string.total_position, teamsCount.toString())
                                imageViewPeople.circleImage(STRING_URL_AVATAR_APE)
                                positions.map {
                                Timber.d("${it.position} at ${it.coinName}" )
                                    textViewPeoplePosition.text = getString(R.string.position,it.position, it.coinName)
                                }
//                                for (position in positions.indices)
//                                {
//                                    Timber.d(positions[position].coinName)
//                                    textViewPeoplePosition.text = getString(R.string.position,positions[position].position, positions[position].coinName)
//                                }
                                setupPeopleSocialMedia(imageViewPeopleTwitter,response.data)
                                setupPeopleSocialMedia(imageViewPeopleMedium,response.data)
                                setupPeopleSocialMedia(imageViewPeopleLinkedin,response.data)
                                setupPeopleSocialMedia(imageViewPeopleGitHub,response.data)
                            }
                        }
                    }
                }
            })
    }


    private fun progressBarVisibility()
    {
        _peopleViewModel.loading.observe(viewLifecycleOwner,
            {
                _getBinding?.progressBar?.visibility = setVisibility(it)
            })
    }

    //region SOCIALMEDIA
    private fun setupPeopleSocialMedia(
        imageView: ImageView,
        peopleResponse: PeopleResponse
    )
    {
        when(imageView)
        {
            _getBinding?.imageViewPeopleGitHub ->
            {
                with(imageView)
                {
                    if (peopleResponse.links.github.isNullOrEmpty()) {
                        visibility = setVisibility(false)
                    } else {
                        for (github in peopleResponse.links.github) {
                            setOnClickListener()
                            {
                                val bundle = Bundle()
                                bundle.putString(BUNDLE_WEB_URL, github.url)
                                replaceFragment(
                                    parentFragmentManager,
                                    WebViewFragment(),
                                    bundle
                                )
                            }
                        }
                    }
                }
            }
            _getBinding?.imageViewPeopleLinkedin ->
            {
                with(imageView)
                {
                    if (peopleResponse.links.linkedin.isNullOrEmpty())
                    {
                        visibility = setVisibility(false)
                    } else
                    {
                        for (linkedin in peopleResponse.links.linkedin)
                        {
                            setOnClickListener()
                            {
                                val bundle = Bundle()
                                bundle.putString(BUNDLE_WEB_URL, linkedin.url)
                                replaceFragment(
                                    parentFragmentManager,
                                    WebViewFragment(),
                                    bundle
                                )
                            }
                        }
                    }
                }
            }
            _getBinding?.imageViewPeopleTwitter ->
            {
                with(imageView)
                {
                    if (peopleResponse.links.twitter.isNullOrEmpty())
                    {
                        visibility = setVisibility(false)
                    } else
                    {
                        for (twitter in peopleResponse.links.twitter)
                        {
                            setOnClickListener()
                            {
                                val bundle = Bundle()
                                bundle.putString(BUNDLE_WEB_URL,twitter.url)
                                replaceFragment(parentFragmentManager,WebViewFragment(),bundle)
                            }
                        }
                    }
                }
            }
            _getBinding?.imageViewPeopleMedium ->
            {
                with(imageView)
                {
                    if (peopleResponse.links.medium.isNullOrEmpty())
                    {
                        visibility = setVisibility(false)
                    } else
                    {
                        for (medium in peopleResponse.links.medium)
                        {
                            setOnClickListener()
                            {
                                val bundle = Bundle()
                                bundle.putString(BUNDLE_WEB_URL,medium.url)
                                replaceFragment(parentFragmentManager,WebViewFragment(),bundle)
                            }
                        }
                    }
                }
            }
        }
    }

    //endregion

}