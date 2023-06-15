package com.example.aiderma.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiderma.api.config.ApiConfig
import com.example.aiderma.api.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    companion object{
        val TAG = "LoginViewMOdel"
    }

    private val _userLogin = MutableLiveData<LoginResponse?>()
    val userLogin : LiveData<LoginResponse?> = _userLogin

    fun loginUser(email: String, password: String){
        val client= ApiConfig.getApiService().login(email,password)
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    _userLogin.value = response.body()
                    Log.d(TAG,"onResponse: Berhasil!")
                }else {
                    Log.e(TAG,"onResponse else: ${response.message()} ")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }
}