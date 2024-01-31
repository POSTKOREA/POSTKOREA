package com.ssafy.travelcollector

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.SNSAuth
import com.ssafy.travelcollector.config.SNSAuth.disconnect
import com.ssafy.travelcollector.config.SNSAuth.startKakaoLogin
import com.ssafy.travelcollector.config.SNSAuth.startNaverLogin
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.viewModel.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "LoginFragment"
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(false)

        setLoginCallBack(view)

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

    private fun setLoginCallBack(view: View){

        SNSAuth.setLoginCallBack(object : SNSAuth.LoginCallback{
            override fun onAlreadySignIn(userInfo: User) {
            }

            override fun onSignUp(userInfo: User) {
                mainActivityViewModel.passUserInfoToSignUp(userInfo)
                Navigation.findNavController(view).navigate(R.id.signUpFragment)
            }

        })

    }

}