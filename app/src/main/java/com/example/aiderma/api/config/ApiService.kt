package com.example.aiderma.api.config

import com.example.aiderma.api.response.*
import okhttp3.MultipartBody
import retrofit2.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.*

interface ApiService  {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nama") nama: String,
        @Field("tanggal_lahir") tanggal_lahir: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call <LoginResponse>

    @GET("allskinDisease")
    fun getDisease(): Call<DiseaseResponse>

    @Multipart
    @POST("uploadImage")
    fun uploadImg(
        @Header("Authorization") token: String,
        @Part img: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("skinDiseases/search")
    fun search(
        @Query("searchTerm") search: String?
    ): Call<SearchResponse>
}