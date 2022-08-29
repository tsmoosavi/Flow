package com.example.flow.ui.utils.widget

import android.content.Context
import android.graphics.Color
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
    private val errorText: String
    private val emptyText: String
    private val lottieRow: Int
    private val errorTextSize: Float

    init {
        val attributes = context.obtainStyledAttributes(attr, R.styleable.LoadingView)
        errorText = attributes.getString(R.styleable.LoadingView_error_text) ?: resources.getString(
            R.string.try_again
        )
        emptyText = attributes.getString(R.styleable.LoadingView_empty_text) ?: resources.getString(
            R.string.empty_list
        )
        errorTextSize = attributes.getDimension(
            R.styleable.LoadingView_error_text_size,
            resources.getDimension(R.dimen.default_error_text_size)
        )
        lottieRow =
            attributes.getResourceId(R.styleable.LoadingView_lotti_rows, R.raw.loading_animation)
        attributes.recycle()
    }

    fun onLoading() {
        binding.apply {
            if (loadingAnimation.isAnimating)
                return
            loadingAnimation.playAnimation()
            loadingAnimation.visible()
            loadingAnimation.setAnimation(lottieRow)
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

    fun onError(text: String = errorText, listener: (() -> Unit)? = null) {
        binding.apply {
            loadingAnimation.pauseAnimation()
            loadingAnimation.gone()
            tvMsg.visible()
            tvMsg.text = text
            tvMsg.paint.isUnderlineText = listener != null
            tvMsg.isClickable = listener != null
            tvMsg.setOnClickListener { listener?.invoke() }
            tvMsg.setTextColor(Color.RED)
        }
    }

    fun onEmpty(text: String = emptyText, listener: (() -> Unit)? = null) {
        binding.apply {
            loadingAnimation.pauseAnimation()
            loadingAnimation.gone()
            tvMsg.visible()
            tvMsg.text = text
            tvMsg.paint.isUnderlineText = listener != null
            tvMsg.setTextColor(Color.GRAY)
            tvMsg.isClickable = listener != null
            tvMsg.setOnClickListener { listener?.invoke() }
        }
    }

    fun onSuccessOrEmpty(isEmpty: Boolean) {
        if (isEmpty)
            onEmpty()
        else
            onSuccess()
    }


//        4- 6 - 5 - 9 - 1 - 2 - 8
    /*
    * 1. pagination 4h -> 2 session
    * 2. data store 3h ->1.5 s
    * 3. coil 10m -> ignore!!:)
    *
    * 5. sateFlow / sharedFlow 6h -> 3 s
    * 6.intermediate operators of flow :too much 8h -> 4 s
    * 7. custom view 4h -> 2 s
    * 8. database 4h -> 2 s
    * 9. repeatOnLifecycle 2h -> 1 s
    *
    * result = 16.5 session*/
}