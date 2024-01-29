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
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
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
            showToast("clicked")
            startNaverLogin()
        }

    }

    private fun startNaverLogin(){
        var token: String? = ""
        val profileCallback = object: NidProfileCallback<NidProfileResponse>{
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                showToast("errorCode: $errorCode \n errorDescription: $errorDescription")
            }

            override fun onSuccess(result: NidProfileResponse) {
                val userId = result.profile?.id
                showToast("성공 $userId")

            }
        }

        val oauthLoginCallback = object: OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                showToast("error $errorCode \n message $message")
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                showToast("errorCode: $errorCode \n errorDescription: $errorDescription")
            }

            override fun onSuccess() {
                token = NaverIdLoginSDK.getAccessToken()
                Log.d(TAG, "onSuccess: $token")
                showToast("token $token")
                NidOAuthLogin().callProfileApi(profileCallback)
            }

        }

        NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)

    }


}