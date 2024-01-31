package com.ssafy.travelcollector

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssafy.travelcollector.adapter.ThemeAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentThemeListBinding
import kotlinx.coroutines.launch

private const val TAG = "ThemeListFragment"
class ThemeListFragment : BaseFragment<FragmentThemeListBinding>(FragmentThemeListBinding::bind, R.layout.fragment_theme_list){

    private val themeAdapter: ThemeAdapter by lazy{
        ThemeAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private var onlyBookMarked = false

    private fun submitListByStateChange(){
        val currentList = mainActivityViewModel.recommendedTheme.value
        themeAdapter.submitList(
            if(onlyBookMarked) currentList.toMutableList().filter{ it.isBookMarked }
            else currentList
        )
    }

    private fun initView(){
        binding.themeListToggleOnlyRecommended.setOnCheckedChangeListener{
            _, isChecked ->
            onlyBookMarked = isChecked
            submitListByStateChange()
        }
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    mainActivityViewModel.recommendedTheme.collect{
                        submitListByStateChange()
                    }
                }
            }
        }

        themeAdapter.clickListener = object : ThemeAdapter.ClickListener{
            override fun onItemClick(position: Int) {

            }

            override fun onBookMarkClick(position: Int) {
                val newList = mainActivityViewModel.recommendedTheme.value.toMutableList()
                val bookMarked = newList[position].isBookMarked
                newList[position] = newList[position].copy(isBookMarked = !bookMarked)
                mainActivityViewModel.setRecommendedTheme(newList as ArrayList)
            }

        }
        binding.themeListRv.adapter = themeAdapter
    }

}