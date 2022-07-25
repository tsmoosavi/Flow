package com.example.data

import com.example.NetworkCall
import com.example.Resource
import com.example.data.network.ApiService
import com.example.flow.model.ImageItem
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getImageList():Resource<List<ImageItem>>{
        return object: NetworkCall<List<ImageItem>>(){
            override suspend fun createCall():Response<List<ImageItem>>{
              return  apiService.getImageList()
            }
        }.fetch()
    }




}