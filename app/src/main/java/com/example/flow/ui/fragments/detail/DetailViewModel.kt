package com.example.flow.ui.fragments.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.flow.model.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val stateHandle: SavedStateHandle) : ViewModel() {
    val imageItem = stateHandle.get<ImageItem>("imageItem")
    var title : String = stateHandle["keyTitle"]?: ""  // get from saveStateHandle
    set(value) {
        field = value
        stateHandle["keyTitle"] = value  //save to saveStateHandle
    }
}