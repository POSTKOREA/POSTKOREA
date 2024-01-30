package com.ssafy.travelcollector.fragment.travel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTravelPlanBinding
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.ItemTouchCallBack
import com.ssafy.travelcollector.test.TDto
import com.ssafy.travelcollector.test.TestAdapter
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import java.text.SimpleDateFormat
import java.util.Calendar.getInstance
import java.util.Collections
import java.util.Locale

private const val TAG = "TravelPlanFragment"
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(FragmentTravelPlanBinding::bind,
    R.layout.fragment_travel_plan
) {
    private val testAdapter: TestAdapter by lazy{
        TestAdapter()
    }

    val data = arrayListOf(TDto(1), TDto(2), TDto(3))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DB 불러오기
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        binding.travelListDaySelect.setOnClickListener{

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

        binding.travelPlanFabAdd.setOnClickListener{
            mainActivityViewModel.addDetailState(DetailStateEnum.AddToTravel)
            findNavController().navigate(R.id.heritageListFragment)
        }
    }

    private fun initAdapter(){
        testAdapter.deleteListener = object: TestAdapter.DeleteListener{
            override fun delete(position: Int) {
                val newList = mainActivityViewModel.travelPlanHeritageList.value
                newList.removeAt(position)
                mainActivityViewModel.setTravelPlanHeritageList(newList)
                testAdapter.submitList(mainActivityViewModel.travelPlanHeritageList.value)
            }
        }

        val itemTouchCallBack = ItemTouchHelper(ItemTouchCallBack())
        itemTouchCallBack.attachToRecyclerView(binding.travelPlanRv)

        testAdapter.startDragListener = object : TestAdapter.StartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchCallBack.startDrag(viewHolder)
            }
        }

        testAdapter.moveListener = object : TestAdapter.MoveListener{
            override fun onMove(from: Int, to: Int) {
                val newList = testAdapter.currentList.toMutableList()
                Collections.swap(newList, from, to)
                mainActivityViewModel.setTravelPlanHeritageList(newList as ArrayList)
                testAdapter.submitList(newList)
            }
        }

        testAdapter.removeListener = object : TestAdapter.RemoveListener{
            override fun onRemove(position: Int) {
                val newList = testAdapter.currentList.toMutableList()
                newList.removeAt(position)
                mainActivityViewModel.setTravelPlanHeritageList(newList as ArrayList)
                testAdapter.submitList(newList)
            }

        }

        testAdapter.submitList(mainActivityViewModel.travelPlanHeritageList.value)
        binding.travelPlanRv.adapter = testAdapter
        binding.travelPlanRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

    }


}