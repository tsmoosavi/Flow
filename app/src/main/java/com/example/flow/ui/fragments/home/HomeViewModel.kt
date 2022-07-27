package com.example.flow.ui.fragments.home

import androidx.lifecycle.*
import com.example.Resource
import com.example.Status
import com.example.data.Repository
import com.example.flow.model.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val remoteRepository: Repository) : ViewModel() {


    init {
//        getImageList()
    }

    private val _picList = MutableLiveData<List<ImageItem>?>()
    val picList: LiveData<List<ImageItem>?> = _picList

    var imageList =
        liveData<List<ImageItem>?>(context = Dispatchers.IO, timeoutInMs = 5000) {
            remoteRepository.getImageList().let {
                if (it.status == Status.SUCCESSFUL && it.data != null) {
                    emit(it.data)

                }
            }
        }

}