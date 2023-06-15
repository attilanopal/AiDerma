package com.example.aiderma.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiderma.api.config.ApiConfig
import com.example.aiderma.api.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    companion object {
        const val TAG = "RegisterViewModel"
    }

    private val _userRegister = MutableLiveData<RegisterResponse?>()
    val userRegister : LiveData<RegisterResponse?> = _userRegister

    fun registerUser(email: String, password: String, nama: String, tanggal_lahir: String ) {

        val client = ApiConfig.getApiService().register(email,password,nama, tanggal_lahir)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful) {
                    _userRegister.value = response.body()
                    Log.d(TAG, "onResponse: Berhasil!")
                }else {
                    Log.e(TAG,  "onResponse else : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}