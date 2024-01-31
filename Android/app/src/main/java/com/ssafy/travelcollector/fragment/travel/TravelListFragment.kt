package com.ssafy.travelcollector.fragment.travel

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelListBinding

class TravelListFragment : BaseFragment<FragmentTravelListBinding> (FragmentTravelListBinding::bind,
    R.layout.fragment_travel_list
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.travelListAddBtnMyTravel.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.travelPlanFragment)
        }
    }

}