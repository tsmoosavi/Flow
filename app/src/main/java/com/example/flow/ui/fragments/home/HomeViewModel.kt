package com.example.flow.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.network.util.NetworkResult
import com.example.util.launchScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    var isNightMode:Boolean  = false
    private val apiCaller = Channel<Boolean>()
    val imageList = apiCaller.receiveAsFlow().flatMapLatest {
        remoteRepository.getImageList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = NetworkResult.Loading()
    )
    val secondImageList = flowOf("")

    init {
        fetchItems()
    }

    fun fetchItems() {
        launchScope {
            apiCaller.send(true)
        }
    }


}