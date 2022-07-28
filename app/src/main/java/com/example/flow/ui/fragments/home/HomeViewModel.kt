package com.example.flow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.Resource
import com.example.Status
import com.example.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    private val apiCaller = MutableLiveData<Boolean>(true)

    val  imageList = apiCaller.switchMap {
        liveData {
            emit(Resource(Status.LOADING,null,""))
            emit(remoteRepository.getImageList())
        }
    }

    fun requestToNetwork(){
        apiCaller.postValue(true)
    }

}