package com.example.testmagangbatch6.api

import com.example.testmagangbatch6.model.DataUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<DataUser>
}