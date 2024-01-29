package com.ssafy.travelcollector.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.databinding.OverlapImagesBinding

class OverlapImages constructor(context: Context, attrs: AttributeSet) :  LinearLayout(context, attrs){

    private val binding: OverlapImagesBinding by lazy{
        OverlapImagesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.overlap_images, this, false)
        )
    }
    init{
        addView(binding.root)
    }

}