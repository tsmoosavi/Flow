package com.example.flow.ui.utils.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.flow.R
import com.example.flow.databinding.LayoutLoadingViewBinding
import com.example.util.gone
import com.example.util.visible

class LoadingView(context: Context, attr: AttributeSet) :
    ConstraintLayout(context, attr) {
    private val binding: LayoutLoadingViewBinding = LayoutLoadingViewBinding.bind(
        inflate(
            context, R.layout.layout_loading_view, this
        )
    )
    private var errorText: String

    init {
        val attributes = context.obtainStyledAttributes(attr, R.styleable.LoadingView)
        errorText = attributes.getString(R.styleable.LoadingView_error_text) ?: resources.getString(
            R.string.try_again
        )
        val emptyText = attributes.getString(R.styleable.LoadingView_empty_text)
        val errorTextSize = attributes.getDimension(
            R.styleable.LoadingView_error_text_size,
            resources.getDimension(R.dimen.default_error_text_size)
        )
        val lottieRow =
            attributes.getResourceId(R.styleable.LoadingView_lotti_rows, R.raw.loading_animation)
        attributes.recycle()
    }

    fun onLoading() {
        binding.apply {
            if (!loadingAnimation.isAnimating) loadingAnimation.playAnimation()
            loadingAnimation.visible()
            tvMsg.gone()
        }
    }

    fun onSuccess() {
        binding.apply {
            loadingAnimation.pauseAnimation()
            loadingAnimation.gone()
            tvMsg.gone()
        }
    }

    fun onError() {
        binding.apply {
            loadingAnimation.pauseAnimation()
            loadingAnimation.gone()
            tvMsg.visible()
            tvMsg.text = errorText
        }
    }

    fun onEmpty() {

    }

    fun onSuccessOrEmpty(isEmpty: Boolean) {
    }
}