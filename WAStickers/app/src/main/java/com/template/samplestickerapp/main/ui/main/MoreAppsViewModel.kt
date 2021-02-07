package com.template.samplestickerapp.main.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.samplestickerapp.main.data.MyRepository
import com.template.samplestickerapp.main.data.model.MoreApp
import com.template.samplestickerapp.main.utils.NetworkHelper
import com.template.samplestickerapp.main.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MoreAppsViewModel @ViewModelInject constructor(

        private val myRepository: MyRepository,
        private val networkHelper: NetworkHelper,

        ) : ViewModel() {


    private val _myData = MutableLiveData<ResponseState<MoreApp>>()
    val myData: LiveData<ResponseState<MoreApp>> = _myData

    fun getMyData() = viewModelScope.launch {
        safeGetMyData()
    }

    private suspend fun safeGetMyData() {
        withContext(Dispatchers.IO) {
            _myData.postValue(ResponseState.Loading())
            try {
                if (networkHelper.isNetworkConnected()) {

                    val response = myRepository.getMoreApps()
                    _myData.postValue(handleMyDataResponse(response))

                } else {
                    _myData.postValue(ResponseState.Error("Please connect to internet"))
                }


            } catch (e: Exception) {
                _myData.postValue(e.message?.let { ResponseState.Error(it) })
            }

        }
    }

    private fun handleMyDataResponse(response: Response<MoreApp>): ResponseState<MoreApp>? {

        if (response.isSuccessful) {

            response.body()?.let { moreApp ->
                return ResponseState.Success(moreApp)
            }

        }
        return ResponseState.Error(response.message())

    }


}