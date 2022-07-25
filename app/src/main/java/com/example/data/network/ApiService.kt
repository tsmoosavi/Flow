package com.example.data.network

import com.example.flow.model.ImageItem
import retrofit2.Response
import retrofit2.http.GET

const val baseUrl = "https://picsum.photos/v2/"

interface ApiService {

    @GET("list")
    suspend fun getImageList():Response<List<ImageItem>>


}