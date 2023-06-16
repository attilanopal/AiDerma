package com.example.aiderma.ui.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiderma.api.config.ApiConfig
import com.example.aiderma.api.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel : ViewModel() {
    companion object {
        val TAG = "ScanViewModel"
    }

    private val _uploadImg = MutableLiveData<UploadResponse>()
    val uploadImg : LiveData<UploadResponse> = _uploadImg

    fun sendImage(file : MultipartBody.Part){
        val client = ApiConfig.getApiService().uploadImg(file)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "${response.message()}")
                }else {
                    Log.e(TAG,"onResponse else: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }
}