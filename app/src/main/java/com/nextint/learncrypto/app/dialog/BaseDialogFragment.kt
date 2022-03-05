package com.nextint.learncrypto.app.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.databinding.FragmentBaseDialogBinding


class BaseDialogFragment : DialogFragment() {
    private var _bindingBaseDialogFragment : FragmentBaseDialogBinding? = null
    private val _getbindingBaseDialogFragment get() = _bindingBaseDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingBaseDialogFragment = FragmentBaseDialogBinding.inflate(inflater,container,false)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return _getbindingBaseDialogFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _getbindingBaseDialogFragment?.textViewDialogReportError?.setOnClickListener()
        {
            Toast.makeText(requireContext(), "Nanti dulu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val dividedWidth = width.toDouble() / 1.5
        dialog?.window?.setLayout(dividedWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun showErrorDialog(intErrorCode : Int?, buttonAction: (() -> Unit?)? = null, buttonText: Int? )
    {
        when (intErrorCode) {
            404 ->
            {
                with(_getbindingBaseDialogFragment)
                {
                    this?.imageViewDialog?.setImageResource(R.drawable.ic_baseline_broken_image_24)
                    this?.textViewDialogMessage?.text = "Data not found"
                    this?.buttonDialog?.text = getString(buttonText ?: R.string.BUTTON_CANCEL)
                    this?.buttonDialog?.setOnClickListener()
                    {
                        buttonAction?.invoke() ?: dismiss()
                    }
                }

            }
            else ->
            {
                with(_getbindingBaseDialogFragment)
                {
                    this?.imageViewDialog?.setImageResource(R.drawable.ic_baseline_broken_image_24)
                    this?.textViewDialogMessage?.text = "Something went wrong"
                    this?.buttonDialog?.text = getString(buttonText ?: R.string.BUTTON_CANCEL)
                    this?.buttonDialog?.setOnClickListener()
                    {
                        buttonAction?.invoke() ?: dismiss()
                    }
                }
            }
        }
    }

    private fun dialogAction(buttonAction: (() -> Unit?)? = null, buttonText: Int?) {
        with(_getbindingBaseDialogFragment?.buttonDialog)
        {
            this?.text = getString(buttonText ?: R.string.BUTTON_CANCEL)
            this?.setOnClickListener()
            {
                buttonAction?.invoke() ?: dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingBaseDialogFragment = null
    }

}