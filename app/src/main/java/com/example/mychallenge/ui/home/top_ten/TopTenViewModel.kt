package com.example.mychallenge.ui.home.top_ten

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychallenge.common.Extensions.getOrderList
import com.example.mychallenge.domain.GetTopTens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TopTenViewModel @Inject constructor(
    private val getTopTensUseCase: GetTopTens): ViewModel() {

    private val _topTensList = MutableLiveData<List<Any>>()
    val topTensList: LiveData<List<Any>> get() = _topTensList

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progressVisible


    init {
        _progressVisible.postValue(true)
        getUpdateTopTenList()
    }

    fun getTopTenList(){
        viewModelScope.launch{
            _progressVisible.postValue(false)
            _topTensList.postValue(getTopTensUseCase().getOrderList() ?: emptyList())
        }
    }

    fun getUpdateTopTenList(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                while (true){
                    getTopTenList()
                    delay(3000)
                }
            }
        }
    }

}