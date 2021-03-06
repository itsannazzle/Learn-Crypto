package com.nextint.learncrypto.app.features.person

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentPeopleBinding
import com.nextint.learncrypto.app.features.person.presentation.PeopleViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction.setVisibility
import com.nextint.learncrypto.app.features.utils.circleImage
import com.nextint.learncrypto.app.util.ID_TEAM_CONSTANT
import com.nextint.learncrypto.app.util.STRING_URL_AVATAR_APE


class PeopleFragment : BaseFragment<PeopleViewModel>()
{
    private var _bindingFragmentPeople : FragmentPeopleBinding? = null
    private val _getBindingFragmentPeople get() = _bindingFragmentPeople
    private var peopleId :String? = null

    override fun setupViewModel(): Class<PeopleViewModel> = PeopleViewModel::class.java

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
        _bindingFragmentPeople = FragmentPeopleBinding.inflate(layoutInflater, container, false)
        peopleId = arguments?.getString(ID_TEAM_CONSTANT)
        _activityMain = activity as MainActivity
        _modelDialog = DialogModel()
        _viewModel.getPeopleById(peopleId ?: "")
        _activityMain._dialog.show()
        return _getBindingFragmentPeople?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData()
    {
        _viewModel.peopleById.observe(viewLifecycleOwner)
        { response ->
            when (response) {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.getPeopleById(peopleId ?: "") }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)
                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                    is ApiResponse.Success ->
                    {
                        _activityMain._dialog.hide()
                        response.data?.let()
                        {
                            with(response.data)
                            {
                                _getBindingFragmentPeople?.apply {
                                    textViewPeopleName.text = name
                                    textViewPeopleDesc.text = description ?: getString(R.string.desc_not_found)
                                    textViewPeopleTotalPosition.text = getString(R.string.total_position, teamsCount.toString())
                                    imageViewPeople.circleImage(STRING_URL_AVATAR_APE)
                                    val positionTeam = positions.joinToString { it.position.plus(" at ${it.coinName}") }

                                    textViewPeoplePosition.text = getString(R.string.position,positionTeam)
                                    if (response.data.links != null)
                                    {
                                        setupPeopleSocialMedia(imageViewPeopleTwitter,response.data)
                                        setupPeopleSocialMedia(imageViewPeopleMedium,response.data)
                                        setupPeopleSocialMedia(imageViewPeopleLinkedin,response.data)
                                        setupPeopleSocialMedia(imageViewPeopleGitHub,response.data)
                                    }

                                }
                            }
                        }
                    }
                }
            }
    }



    //region SOCIALMEDIA

    private fun setupPeopleSocialMedia(imageView: ImageView, peopleResponse: PeopleResponse)
    {
        when(imageView)
        {
                _getBindingFragmentPeople?.imageViewPeopleGitHub ->
                {
                    with(imageView)
                    {
                        if (peopleResponse.links?.github.isNullOrEmpty())
                        {
                            visibility = setVisibility(false)
                        }
                        else
                        {
                            for (github in peopleResponse.links!!.github)
                            { setOnClickListener()
                            {
                                UtilitiesFunction.openBrowserWithURL(requireContext(),github.url)
                            }
                            }
                        }
                    }
                }

                _getBindingFragmentPeople?.imageViewPeopleLinkedin ->
                {
                    with(imageView)
                    {
                        if (peopleResponse.links?.linkedin.isNullOrEmpty())
                        {
                            visibility = setVisibility(false)
                        }
                        else
                        {
                            for (linkedin in peopleResponse.links!!.linkedin)
                            { setOnClickListener()
                            {
                                UtilitiesFunction.openBrowserWithURL(requireContext(),linkedin.url)
                            }
                            }
                        }
                    }
                }

                _getBindingFragmentPeople?.imageViewPeopleTwitter ->
                {
                    with(imageView)
                    {
                        if (peopleResponse.links?.twitter.isNullOrEmpty())
                        {
                            visibility = setVisibility(false)
                        }
                        else
                        {
                            for (twitter in peopleResponse.links!!.twitter)
                            { setOnClickListener()
                            {
                                UtilitiesFunction.openBrowserWithURL(requireContext(),twitter.url)
                            }
                            }
                        }
                    }
                }
                _getBindingFragmentPeople?.imageViewPeopleMedium ->
                {
                    with(imageView)
                    {
                        if (peopleResponse.links?.medium.isNullOrEmpty())
                        {
                            visibility = setVisibility(false)
                        }
                        else
                        {
                            for (medium in peopleResponse.links!!.medium)
                            { setOnClickListener()
                            {
                                UtilitiesFunction.openBrowserWithURL(requireContext(),medium.url)
                            }
                            }
                        }
                    }
                }
            }

    }

    //endregion

    override fun onDestroy() {
        super.onDestroy()
        _bindingFragmentPeople = null
    }
}