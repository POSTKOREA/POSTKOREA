package com.ssafy.travelcollector

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPlanBinding
import java.util.Calendar
import androidx.core.util.Pair
import java.text.SimpleDateFormat
import java.util.Calendar.getInstance
import java.util.Locale


class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(FragmentTravelPlanBinding::bind, R.layout.fragment_travel_plan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.travelListDaySelect.setOnClickListener{
            Locale.setDefault(resources.configuration.locale)

            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간 선택").build()

            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = getInstance()
                calendar.timeInMillis = selection?.first ?: 0
                val startDate = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(calendar.time).toString()

                calendar.timeInMillis = selection?.second ?: 0
                val endDate = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(calendar.time).toString()

                binding.travelPlanTvDuration.text = dateRangePicker.headerText
            }
        }
    }


}