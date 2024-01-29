package com.ssafy.travelcollector.config

import android.app.Application
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK


private const val TAG = "ApplicationClass"
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        NaverIdLoginSDK.initialize(applicationContext, NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_CLIENT_NAME)
    }
    companion object{
        const val NAVER_CLIENT_ID = "ISnINdg5vxbmmrhbZ2rJ"
        const val NAVER_CLIENT_SECRET = "Zc1XDYhfgo"
        const val NAVER_CLIENT_NAME = "dMoblie"
    }

}