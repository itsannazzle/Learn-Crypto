package com.nextint.learncrypto.app.features.ui.people

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentPeopleBinding
import com.nextint.learncrypto.app.features.person.viewmodel.PeopleViewModel
import com.nextint.learncrypto.app.features.person.viewmodel.PeopleViewModelFactory
import com.nextint.learncrypto.app.features.ui.webview.WebViewFragment
import com.nextint.learncrypto.app.features.utils.circleImage
import com.nextint.learncrypto.app.features.utils.loadImage
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.util.BUNDLE_WHITEPAPER_URL
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
                                textViewPeopleDesc.text = description
                                textViewPeopleTotalPosition.text = getString(R.string.total_position, teamsCount.toString())
                                imageViewPeople.circleImage(STRING_URL_AVATAR_APE)
                                for (position in positions)
                                {
                                    textViewPeoplePosition.text = getString(R.string.position,position.position, position.coinName)
                                }
                                if (links.github.isNullOrEmpty())
                                {
                                    Timber.d("github null")
                                } else
                                {
                                    for (github in links.github)
                                    {
                                        imageViewPeopleGitHub.setOnClickListener {
                                            val bundle = Bundle()
                                            bundle.putString(BUNDLE_WHITEPAPER_URL,github.url)
                                            replaceFragment(parentFragmentManager,WebViewFragment(),bundle)
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            })
    }

}