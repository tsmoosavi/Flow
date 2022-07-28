package com.example.data

import com.example.Resource
import com.example.data.network.util.safeApiCall
import com.example.flow.model.ImageItem
import javax.inject.Inject


class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    //    suspend fun getImageList():Resource<List<ImageItem>>{
//        return remoteDataSource.getImageList()
//    }
    fun getImageList() = safeApiCall { remoteDataSource.getImageList() }
}