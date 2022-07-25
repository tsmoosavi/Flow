package com.example.data

import com.example.Resource
import com.example.flow.model.ImageItem
import javax.inject.Inject


class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource){

    suspend fun getImageList():Resource<List<ImageItem>>{
        return remoteDataSource.getImageList()

    }
}