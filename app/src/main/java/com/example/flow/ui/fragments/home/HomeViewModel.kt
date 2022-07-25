package com.example.flow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Resource
import com.example.flow.model.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    var imageList= MutableLiveData<Resource<List<ImageItem>>>()

//    fun getImageList():
}