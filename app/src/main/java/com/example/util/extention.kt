@file:OptIn(ExperimentalContracts::class, ExperimentalContracts::class)

package com.example.util

import android.content.Context
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.data.network.util.Cause
import com.example.data.network.util.NetworkResult
import com.example.flow.R
import com.example.flow.ui.utils.widget.LoadingView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

fun Fragment.showErrorMessage(cause: Cause?) {
    requireContext().showErrorMessage(cause)
}

fun Context.showErrorMessage(cause: Cause?) {
    val message = cause?.msg?.ifBlank { getString(cause.msgResId) }
        ?: getString(R.string.default_error_message)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun ViewModel.launchScope(crossinline block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch { block() }
}

inline fun Fragment.launchScope(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewLifecycleOwner.lifecycleScope.launch(context) { block() }
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
            if (isCrossFade) transition(
                DrawableTransitionOptions.withCrossFade(
                    defaultCrossFadeDuration
                )
            )
            if (isRoundedCorner) transform(RoundedCorners(defaultRoundCorner))
        }
        .into(this)
}

fun <T> T?.safe(): T = checkNotNull(this)

fun <T> T?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}

fun Editable?.textOrEmpty(): String = this?.toString().orEmpty()
fun EditText?.textOrEmpty(): String = this?.text.textOrEmpty()

inline fun <T> Flow<NetworkResult<T>>.safeCollect(
    loadingView: LoadingView? = null,
    dataView: View? = null,
    context: Context,
    scope: CoroutineScope,
    noinline onRetry: () -> Unit = {},
    crossinline onError: () -> Unit = {},
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: suspend (data: T) -> Unit,
//    isFading:Boolean
): Job = scope.launch {
    collect {
        when (it) {
            is NetworkResult.Error -> {
                loadingView?.onError { onRetry() }
                dataView?.gone() //or fadeOut if isFading==true
                context.showErrorMessage(it.cause)
                onError()
            }
            is NetworkResult.Loading -> {
                loadingView?.onLoading()
                dataView?.gone()
                onLoading()
            }
            is NetworkResult.Success -> {
                loadingView?.onSuccess()
                dataView?.visible()
                onSuccess(it.data!!)
            }
        }
    }
}

inline fun <T> Flow<T>.collectOnScope(
    scope: CoroutineScope,
    crossinline block: suspend (data: T) -> Unit
) = scope.launch {
    collect {
        block(it)
    }
}

fun Int?.orMinus() = this ?: -1
fun Int?.orZero() = this ?: 0


