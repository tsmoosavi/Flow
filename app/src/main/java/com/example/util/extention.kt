package com.example.util

import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun View.visible(){
    isVisible = true
}

fun View.gone(){
    isGone = true
}
fun View.invisible(){
    visibility = View.INVISIBLE
}
fun Fragment.showToastMessage(message: String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
inline fun ViewModel.launchScope(crossinline block: ()-> Unit ): Job{
   return viewModelScope.launch { block()}
}