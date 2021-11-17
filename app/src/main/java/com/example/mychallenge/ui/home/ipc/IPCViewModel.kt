package com.example.mychallenge.ui.home.ipc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychallenge.common.Extensions.getDate
import com.example.mychallenge.common.Extensions.getOrderInfo
import com.example.mychallenge.common.Extensions.getTime
import com.example.mychallenge.domain.GetIPC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IPCViewModel @Inject constructor(
    private val getIPCUseCase: GetIPC): ViewModel() {

    private val _ipcList = MutableLiveData<MutableMap<Long, Float>>()
    val ipcList: LiveData<MutableMap<Long, Float>> get() = _ipcList

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _textDate = MutableLiveData<String>()
    val textDate: LiveData<String> get() = _textDate

    private val _showGraph = MutableLiveData<Boolean>()
    val showGraph: LiveData<Boolean> get() = _showGraph

    init {
        getIPCList()
    }

    fun getIPCList(){
        _progressVisible.postValue(true)
        _showGraph.postValue(false)
        viewModelScope.launch{
            val valuesXY = getIPCUseCase().getOrderInfo()
            getUpdateTime(valuesXY.keys)
            _ipcList.postValue(valuesXY)
            _progressVisible.postValue(false)
            _showGraph.postValue(true)
        }
    }

    private fun getUpdateTime(listTime: MutableSet<Long>){
        _textDate.postValue(listTime.getDate())
    }

}
