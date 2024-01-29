package com.ssafy.travelcollector

import android.content.Context
import android.os.Bundle
import android.view.View
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.dto.Posting
import com.ssafy.travelcollector.viewModel.MainActivityViewModel

class MainFragment : BaseFragment<FragmentMainBinding> (FragmentMainBinding::bind, R.layout.fragment_main){

    private val mainActivityViewModel = MainActivityViewModel()

    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(true)

        binding.mainPostRv.adapter = MainPostingAdapter().apply {
            submitList(listOf(Posting(), Posting(), Posting()))
            clickListener = object : MainPostingAdapter.IClickListener{
                override fun onClick(position: Int) {
                    val curId = mainActivityViewModel.posting.value[position].postId
                    mainActivityViewModel.setSelectedPostingId(curId)
                }
            }
        }

    }




}