package com.ssafy.travelcollector.config

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.travelcollector.R
import kotlin.math.log

private const val TAG = "SNSAuth"
object SNSAuth{

    val serviceTerms = listOf("service")

    fun disconnect(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    fun startKakaoLogin(context: Context, view: View){
        UserApiClient.instance.loginWithKakaoAccount(context, serviceTerms = serviceTerms){ token, error ->
            if(error != null){
                Log.e(TAG, "startKakaoLogin: 실패, $error")
            }
            else if(token!=null){
                Log.i(TAG, "startKakaoLogin: 성공, ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    Log.d(TAG, "startKakaoLoginUser: $user")

                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패1", error)
                    }
                    else if (user != null) {
                        Log.d(TAG, "startKakaoLogin: ${user.kakaoAccount?.email}")
                        Log.d(TAG, "startKakaoLogin: ${user.properties?.get("nickname")}")
                        Log.d(TAG, "startKakaoLogin: ${user.properties?.get("profile_image")}")
                    }
                }


//                Navigation.findNavController(view).navigate(R.id.mainFragment)
            }

        }

    }

    fun startNaverLogin(context: Context, view: View){
        var token: String? = ""
        val profileCallback = object: NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context, "errorCode: $errorCode \n errorDescription: $errorDescription", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: NidProfileResponse) {
                val userId = result.profile?.id
                Log.d(TAG, "onSuccess: $result")
                Log.d(TAG, "onSuccessId: $userId")
                Navigation.findNavController(view).navigate(R.id.mainFragment)
            }
        }

        val oauthLoginCallback = object: OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                Toast.makeText(context, "error $errorCode \n message $message", Toast.LENGTH_SHORT).show()
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context, "errorCode: $errorCode \n errorDescription: $errorDescription", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                token = NaverIdLoginSDK.getAccessToken()
                Log.d(TAG, "onSuccess: $token")
                NidOAuthLogin().callProfileApi(profileCallback)
            }

        }

        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)

    }
}