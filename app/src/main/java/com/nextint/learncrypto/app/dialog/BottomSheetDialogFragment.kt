package com.nextint.learncrypto.app.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.databinding.FragmentBottomSheetDialogBinding
import com.nextint.learncrypto.app.util.BUNDLE_TAG_DESC
import com.nextint.learncrypto.app.util.BUNDLE_TAG_NAME
import com.nextint.learncrypto.app.util.ID_TAG_CONSTANT
import timber.log.Timber


class BottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding : FragmentBottomSheetDialogBinding? = null
    private val _getBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetDialogBinding.inflate(layoutInflater,container,false)
        val idTag = arguments?.getString(ID_TAG_CONSTANT)
        val descTag = arguments?.getString(BUNDLE_TAG_DESC)
        Timber.d("bottom oncreateview")
        if (idTag != null)
        {
            _getBinding?.textViewTagName?.text = idTag
            _getBinding?.textViewTagDesc?.text = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
        }
        return _getBinding?.root
    }

    override fun onPause() {
        super.onPause()
        Timber.d("bottom onpause")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("bottom onstart")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("bottom onpause")
    }

    override fun getTheme(): Int {
        Timber.d("bottom style")
        return R.style.BottomDialogTheme
    }

    override fun onDestroy() {
        Timber.d("bottom ondest")
        super.onDestroy()
        _binding = null
    }
}