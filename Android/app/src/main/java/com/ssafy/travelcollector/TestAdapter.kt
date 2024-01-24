package com.ssafy.travelcollector

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.TestBinding

class TestAdapter: BaseAdapter<TDto>() {

    inner class TestHolder(private val binding: TestBinding): BaseHolder(binding) {
        override fun bindInfo(data: TDto) {
            binding.testing.text = data.a.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = TestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

}