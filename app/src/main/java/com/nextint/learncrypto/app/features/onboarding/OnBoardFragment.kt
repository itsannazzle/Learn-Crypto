package com.nextint.learncrypto.app.features.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.databinding.FragmentOnBoardBinding
import com.nextint.learncrypto.app.features.home.HomeFragment
import com.nextint.learncrypto.app.features.utils.circleImage


class OnBoardFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardBinding.inflate(layoutInflater,container, false)
        renderView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun renderView(){
        binding.topImg.circleImage("https://images.unsplash.com/photo-1621504450181-5d356f61d307?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",)
        binding.middleImg.circleImage("https://static.gatra.com/foldershared/images/2021/Pra/05-May/crypto.jpg")
        binding.bottomImg.circleImage("https://pyxis.nymag.com/v1/imgs/8f8/e12/51b54d13d65d8ee3773ce32da03e1fa220-dogecoin.rsquare.w700.jpg")
    }

    private fun initListener(){
        binding.btnLearnNow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainActivityContainer, HomeFragment())
                .commitNow()
        }
    }

}