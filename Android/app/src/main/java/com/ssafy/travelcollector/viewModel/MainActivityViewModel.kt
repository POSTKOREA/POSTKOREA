package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import com.ssafy.travelcollector.dto.Posting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel : ViewModel() {

    private val _selectedPostingId = MutableStateFlow(0)
    val selectedPostingId = _selectedPostingId.asStateFlow()
    fun setSelectedPostingId(idx: Int){
        _selectedPostingId.value = idx
    }

    private val _posting = MutableStateFlow(arrayListOf<Posting>())
    val posting = _posting.asStateFlow()

}