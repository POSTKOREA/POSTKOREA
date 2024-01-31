package com.ssafy.travelcollector

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
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
        initAdapter()
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            launch {
                mainActivityViewModel.recommendedTheme.collect{
                    Log.d(TAG, "initAdapter: $it")
                    themeAdapter.submitList(it)
                }
            }
        }

        themeAdapter.clickListener = object : ThemeAdapter.ClickListener{
            override fun onItemClick(position: Int) {

            }

            override fun onBookMarkClick(position: Int) {
                val newList = mainActivityViewModel.recommendedTheme.value.toMutableList()
                newList[position].isBookMarked = !newList[position].isBookMarked
                mainActivityViewModel.setRecommendedTheme(newList as ArrayList)
            }

        }
        binding.themeListRv.adapter = themeAdapter
    }

}