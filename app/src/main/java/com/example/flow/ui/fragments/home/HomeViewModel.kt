package com.example.flow.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.Resource
import com.example.Status
import com.example.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    var imageList = liveData {
//        emit(Resource(Status.LOADING,null,""))
        emit(remoteRepository.getImageList())
        }
}