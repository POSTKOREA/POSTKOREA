package com.ssafy.travelcollector

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding> (FragmentMainBinding::bind, R.layout.fragment_main){


    private lateinit var viewModel: MainViewModel


}