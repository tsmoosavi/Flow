package com.example.util

import android.content.Context
import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun View.visible() {
    isVisible = true
}

fun View.gone() {
    isGone = true
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Fragment.showToastMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

inline fun ViewModel.launchScope(crossinline  block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch { block() }
}

fun <T, F> ImageView.loadImage(
    receiver: T,
    data: F,
    placeholder: Int? = null,
    errorPicture: Int? = null,
    isCircular: Boolean = false,
    isCrossFade: Boolean = false,
    isRoundedCorner: Boolean = false,
    defaultRoundCorner: Int = 40,
    defaultCrossFadeDuration: Int = 500
) {
    if (receiver !is Context && receiver !is View)
        return
    (if (receiver is Context) Glide.with(receiver) else Glide.with(receiver as View))
        .load(data)
        .apply {
            if (placeholder != null) placeholder(placeholder)
            if (errorPicture != null) error(errorPicture)
            if (isCircular) circleCrop()
            if (isCrossFade) transition(DrawableTransitionOptions.withCrossFade(defaultCrossFadeDuration))
            if (isRoundedCorner) transform(RoundedCorners(defaultRoundCorner))
        }
        .into(this)

}