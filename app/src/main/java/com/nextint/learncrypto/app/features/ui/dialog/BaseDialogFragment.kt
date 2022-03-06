package com.nextint.learncrypto.app.features.ui.dialog

import android.content.Context
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
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG


class BaseDialogFragment : DialogFragment()
{
    private var _bindingBaseDialogFragment : FragmentBaseDialogBinding? = null
    private val _getbindingBaseDialogFragment get() = _bindingBaseDialogFragment

    private var modelDialog : DialogModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        _bindingBaseDialogFragment = FragmentBaseDialogBinding.inflate(inflater,container,false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        modelDialog = arguments?.getParcelable<DialogModel>(KEY_BUNDLE_MODEL_DIALOG)


        return _getbindingBaseDialogFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgroud_dialog)
        _getbindingBaseDialogFragment?.textViewDialogReportError?.setOnClickListener()
        {
            Toast.makeText(requireContext(), "Nanti dulu", Toast.LENGTH_SHORT).show()
        }
        modelDialog?.let { showErrorDialog(it) }
    }


    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val dividedWidth = width.toDouble() / 1.5
        dialog?.window?.setLayout(dividedWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun showErrorDialog(modelDialog : DialogModel)
    {
        when (modelDialog.httpErrorCode) {
            404 ->
            {
                if (_getbindingBaseDialogFragment!= null)
                {
                    with(_getbindingBaseDialogFragment)
                    {
                        this?.imageViewDialog?.setImageResource(R.drawable.ic_error_404)
                        this?.textViewDialogMessage?.text = getString(R.string.dialog_404_message)
                        this?.buttonDialog?.text = getString(modelDialog.buttonText ?: R.string.BUTTON_CANCEL)
                        this?.buttonDialog?.setOnClickListener()
                        {
                            modelDialog.retryActionDialog?.invoke() ?: dismiss()
                        }
                    }
                } else
                {
                    Toast.makeText(requireContext(), getString(R.string.dialog_default_title),Toast.LENGTH_SHORT).show()
                }

            }
            409 ->
            {
                if (_getbindingBaseDialogFragment!= null)
                {
                    with(_getbindingBaseDialogFragment)
                    {
                        this?.imageViewDialog?.setImageResource(R.drawable.ic_error_429)
                        this?.textViewDialogMessage?.text = getString(R.string.dialog_429_message)
                        this?.buttonDialog?.text = getString(modelDialog.buttonText ?: R.string.BUTTON_CANCEL)
                        this?.buttonDialog?.setOnClickListener()
                        {
                            dismiss()
                        }
                    }
                } else
                {
                    Toast.makeText(requireContext(), getString(R.string.dialog_default_title),Toast.LENGTH_SHORT).show()
                }
            }
            1404 ->
            {
                if (_getbindingBaseDialogFragment!= null)
                {
                    with(_getbindingBaseDialogFragment)
                    {
                        this?.imageViewDialog?.setImageResource(R.drawable.ic_data_empty)
                        this?.textViewDialogMessage?.text = getString(R.string.dialog_1404_message)
                        this?.buttonDialog?.text = getString(modelDialog.buttonText ?: R.string.BUTTON_CANCEL)
                        this?.buttonDialog?.setOnClickListener()
                        {
                            dismiss()
                        }
                    }
                } else
                {
                    Toast.makeText(requireContext(), getString(R.string.dialog_default_title),Toast.LENGTH_SHORT).show()
                }
            }
            else ->
            {
                with(_getbindingBaseDialogFragment)
                {
                    this?.imageViewDialog?.setImageResource(R.drawable.ic_something_went_wrong)
                    this?.textViewDialogMessage?.text = getString(R.string.dialog_default_message + R.string.dialog_default_message)
                    this?.buttonDialog?.text = getString(modelDialog.buttonText ?: R.string.BUTTON_CANCEL)
                    this?.buttonDialog?.setOnClickListener()
                    {
                       dismiss()
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingBaseDialogFragment = null
    }

}