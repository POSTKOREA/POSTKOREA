package com.ssafy.travelcollector.config

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK


private const val TAG = "ApplicationClass"
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        NaverIdLoginSDK.initialize(applicationContext, NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_CLIENT_NAME)
        KakaoSdk.init(applicationContext, KAKAO_APP_KEY)
    }
    companion object{
        const val NAVER_CLIENT_ID = "ISnINdg5vxbmmrhbZ2rJ"
        const val NAVER_CLIENT_SECRET = "Zc1XDYhfgo"
        const val NAVER_CLIENT_NAME = "dMoblie"

        const val KAKAO_APP_KEY = "6bc61f45686049a50d5900bd5a6c330b"

    }


}