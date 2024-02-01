package com.ssafy.travelcollector

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.SNSAuth
import com.ssafy.travelcollector.config.SNSAuth.disconnect
import com.ssafy.travelcollector.config.SNSAuth.startKakaoLogin
import com.ssafy.travelcollector.config.SNSAuth.startNaverLogin
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
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

        setLoginCallBack()

        lifecycleScope.launch {
            mainActivityViewModel.accessToken.collect{
                if(it.isNotEmpty() && it!="null" && mainActivityViewModel.loginResponseCode == 200){
                    lifecycleScope.launch {
                        mainActivityViewModel.getInfo(it)
                        findNavController().navigate(R.id.mainFragment)
                    }
                }else{
                    if(mainActivityViewModel.loginResponseCode != 0){
                        Log.d(TAG, "onViewCreated: ${mainActivityViewModel.loginResponseCode}")
                        showToast("잘못됐습니다")
                    }
                }
            }
        }

        binding.loginBtnLogin.setOnClickListener{
            lifecycleScope.launch {
                try{
                    mainActivityViewModel.login(binding.loginEtId.text.toString(), binding.loginEtPw.text.toString())
                }catch(e: Exception){
                    Log.e(TAG, "onViewCreated: $e", )
                }
            }
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

        binding.loginBtnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    private fun setLoginCallBack(){

        SNSAuth.setLoginCallBack(object : SNSAuth.LoginCallback{
            override fun onAlreadySignIn(userInfo: User) {
            }

            override fun onSignUp(userInfo: User) {
                mainActivityViewModel.passUserInfoToSignUp(userInfo)
                findNavController().navigate(R.id.signUpFragment)
            }

        })

    }

}