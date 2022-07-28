package com.example.flow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    fun requestToNetwork(){
//        apiCaller.postValue(true)
    }

    val imageList = remoteRepository.getImageList()

}