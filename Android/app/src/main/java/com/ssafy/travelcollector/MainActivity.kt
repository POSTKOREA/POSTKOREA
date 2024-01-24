package com.ssafy.travelcollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.travelcollector.config.BaseActivity
import com.ssafy.travelcollector.databinding.ActivityMainBinding
import com.ssafy.travelcollector.databinding.FragmentMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayout.id, MainFragment())
            .commit()
    }


}