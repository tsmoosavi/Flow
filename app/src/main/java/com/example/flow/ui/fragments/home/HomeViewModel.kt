package com.example.flow.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.example.data.Repository
import com.example.util.launchScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: Repository) : ViewModel() {

    private val apiCaller = Channel<Boolean>()
    val imageList = apiCaller.receiveAsFlow().flatMapLatest {
        remoteRepository.getImageList()
    }

    init {
        fetchItems()
    }

    fun fetchItems() {
        launchScope {
            apiCaller.send(true)
        }
    }


}