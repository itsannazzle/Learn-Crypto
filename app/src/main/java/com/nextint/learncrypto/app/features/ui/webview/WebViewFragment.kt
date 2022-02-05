package com.nextint.learncrypto.app.features.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.databinding.FragmentWebViewBinding
import com.nextint.learncrypto.app.util.BUNDLE_WHITEPAPER_URL


class WebViewFragment : Fragment() {
    private var _binding : FragmentWebViewBinding? = null
    private val _getBinding get() = _binding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding.inflate(layoutInflater,container,false)
        val stringWhitepaperUrl = arguments?.getString(BUNDLE_WHITEPAPER_URL)
        if (stringWhitepaperUrl != null)
        {
            _getBinding?.webViewLayout?.webViewClient = WebViewClient()
            _getBinding?.webViewLayout?.settings?.javaScriptEnabled = true
            _getBinding?.webViewLayout?.loadUrl(stringWhitepaperUrl)

        }
        return _getBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}