package com.ssafy.travelcollector

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.SNSAuth.disconnect
import com.ssafy.travelcollector.config.SNSAuth.startKakaoLogin
import com.ssafy.travelcollector.config.SNSAuth.startNaverLogin
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
import kotlin.math.exp
import kotlin.math.log


private const val TAG = "LoginFragment"
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(false)

        binding.loginBtnLogin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.mainFragment)
        }

        binding.loginBtnNaverLogin.setOnClickListener{
            startNaverLogin(requireContext(),view)
        }

        binding.loginKakaoLogin.setOnClickListener{
            startKakaoLogin(requireContext(), view)
        }

        binding.loginBtnFindId.setOnClickListener {
            disconnect()
        }
    }




}