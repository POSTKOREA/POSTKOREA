package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentCulturalAssetDetailBinding
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.viewModel.MainActivityViewModel

class MainFragment : BaseFragment<FragmentCulturalAssetDetailBinding> (FragmentCulturalAssetDetailBinding::bind, R.layout.fragment_cultural_asset_detail){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}