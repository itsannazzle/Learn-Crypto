package com.nextint.learncrypto.app.features.ui.people

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextint.learncrypto.app.databinding.FragmentPeopleBinding
import com.nextint.learncrypto.app.util.ID_TEAM_CONSTANT


class PeopleFragment : Fragment() {
    private var _binding : FragmentPeopleBinding? = null
    private val _getBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPeopleBinding.inflate(layoutInflater, container, false)
        _getBinding?.textViewPeopleName?.text = arguments?.getString(ID_TEAM_CONSTANT) ?: "null"

        return _getBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

}