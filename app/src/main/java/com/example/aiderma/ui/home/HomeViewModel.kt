package com.example.aiderma.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiderma.api.config.ApiConfig
import com.example.aiderma.api.response.DiseaseResponse
import com.example.aiderma.api.response.DiseasesItem
import com.example.aiderma.api.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    companion object{
        const val TAG = "HomeViewModel"
    }

    private val _listDisease = MutableLiveData<List<DiseasesItem>>()
    val listDisease: LiveData<List<DiseasesItem>> = _listDisease

    private val _searchedListDisease = MutableLiveData<List<DiseasesItem>>()
    val searchedListDisease: LiveData<List<DiseasesItem>> = _searchedListDisease



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

    fun search(keyword: String) {
        val client = ApiConfig.getApiService().search(keyword)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if(response.isSuccessful) {
                    _searchedListDisease.value = response.body()?.diseases
                }else {
                    Log.e(TAG, "onResponse : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "onResponse ${t.message}")
            }
        })
    }


}