package com.ssafy.travelcollector.viewModel

import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.lifecycle.ViewModel
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.dto.Posting
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.test.TDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "MainActivityViewModel"

enum class DetailStateEnum(private val state: Int) {
    None(0),
    AddToTravel(1),
    MiniGame(2),
}

class MainActivityViewModel : ViewModel() {


    private var detailState = arraySetOf(DetailStateEnum.None)
    fun addDetailState(state: DetailStateEnum): MainActivityViewModel{
        detailState.add(state)
        return this
    }

    private val _selectedPostingId = MutableStateFlow(0)
    val selectedPostingId = _selectedPostingId.asStateFlow()
    fun setSelectedPostingId(idx: Int){
        _selectedPostingId.value = idx
    }

    private val _posting = MutableStateFlow(arrayListOf<Posting>())
    val posting = _posting.asStateFlow()


    private val _userInfoToSignUp = MutableStateFlow(User())
    private val userInfoToSignUP = _userInfoToSignUp.asStateFlow()
    fun passUserInfoToSignUp(user:User){
        _userInfoToSignUp.update{user}

    }
    fun getUserInfoToSignUp(): User{
        return  userInfoToSignUP.value
    }

    private var _travelPlanHeritageList = MutableStateFlow(arrayListOf(TDto(1), TDto(2), TDto(3)))
    val travelPlanHeritageList = _travelPlanHeritageList.asStateFlow()
    fun getTravelPlanHeritageList(){
        //rest 통신 하여 각 여행의 문화재 리스트를 불러온다
    }

    fun setTravelPlanHeritageList(list: ArrayList<TDto>){
        _travelPlanHeritageList.update { list }
    }

}