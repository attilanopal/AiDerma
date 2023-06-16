package com.example.aiderma.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiderma.api.config.ApiConfig
import com.example.aiderma.api.response.DiseaseResponse
import com.example.aiderma.api.response.DiseasesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    companion object{
        val TAG = "HomeViewModel"
    }

    private val _listDisease = MutableLiveData<List<DiseasesItem>>()
    val listDisease: LiveData<List<DiseasesItem>> = _listDisease


    fun getListDisease(){
        val client = ApiConfig.getApiService().getDisease()
        client.enqueue(object: Callback<DiseaseResponse>{
            override fun onResponse(
                call: Call<DiseaseResponse>,
                response: Response<DiseaseResponse>
            ) {
                if(response.isSuccessful){
                    _listDisease.value = response.body()?.diseases
                }else {
                 Log.e(TAG, "onResponse : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DiseaseResponse>, t: Throwable) {
                Log.e(TAG, "onResponse ${t.message}")
            }
        })
    }
}