package com.example.flow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.util.launchScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    private val apiCaller = Channel<Boolean>()
    init {
        fetchItems()
    }

    fun fetchItems(){
        launchScope {
            apiCaller.send(true)
        }
    }

    val imageList = apiCaller.receiveAsFlow().flatMapLatest {
        remoteRepository.getImageList()
    }

}