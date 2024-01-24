package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.viewModel.MainActivityViewModel

class MainFragment : BaseFragment<FragmentMainBinding> (FragmentMainBinding::bind, R.layout.fragment_main){

    private val mainActivityViewModel = MainActivityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainPostRv.adapter = MainPostingAdapter().apply {
            clickListener = object : MainPostingAdapter.IClickListener{
                override fun onClick(position: Int) {
                    val curId = mainActivityViewModel.posting.value[position].postId
                    mainActivityViewModel.setSelectedPostingId(curId)
                }
            }
        }

    }




}