package com.example.data.network.util

import androidx.annotation.StringRes
import com.example.flow.R

enum class HttpErrorTypes(
    val code: Int, @StringRes val msgResId: Int
) {
    BAD_REQUEST(code = HttpErrorCode.BAD_REQUEST, msgResId = R.string.default_error_message),
    NOT_FOUND(code = HttpErrorCode.NOT_FOUND, msgResId = R.string.default_error_message),
    INTERNAL_SERVICE_ERROR(
        code = HttpErrorCode.INTERNAL_SERVICE_ERROR,
        msgResId = R.string.default_error_message
    ),
    GATEWAY_TIME_OUT(
        code = HttpErrorCode.GATEWAY_TIME_OUT,
        msgResId = R.string.default_error_message
    ),
    FORBIDDEN(code = HttpErrorCode.FORBIDDEN, msgResId = R.string.default_error_message),
    UNKNOWN(code = HttpErrorCode.UNKNOWN, msgResId = R.string.default_error_message)
    // add more error type
    // add messageResId in string
}

object HttpErrorCode {
    const val BAD_REQUEST = 400
    const val NOT_FOUND = 404
    const val INTERNAL_SERVICE_ERROR = 500
    const val GATEWAY_TIME_OUT = 504
    const val FORBIDDEN = 403
    const val UNKNOWN = -1
}