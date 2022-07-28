package com.example.data.network.util

import androidx.annotation.StringRes
import com.example.flow.R

data class Cause(
    val msg: String = "",
    @StringRes val msgResId: Int = R.string.default_error_message
)