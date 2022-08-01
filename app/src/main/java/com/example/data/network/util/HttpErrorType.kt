package com.example.data.network.util

import androidx.annotation.StringRes

enum class HttpErrorTypes(
    val code: Int, @StringRes val msgResId: Int){
    BAD_REQUEST(code = HttpErrorCode.BAD_REQUEST, msgResId = 0  ),
    NOT_FOUND(code = HttpErrorCode.NOT_FOUND, msgResId = 0  ),
    INTERNAL_SERVICE_ERROR(code = HttpErrorCode.INTERNAL_SERVICE_ERROR, msgResId = 0  ),
    GATEWAY_TIME_OUT(code = HttpErrorCode.GATEWAY_TIME_OUT, msgResId = 0  ),
    FORBIDDEN(code = HttpErrorCode.FORBIDDEN, msgResId = 0  ),
    UNKNOWN(code = HttpErrorCode.UNKNOWN, msgResId = 0  )
    // add more error type
    // add messageResId in string
}
    object HttpErrorCode{
      const val BAD_REQUEST = 400
      const val NOT_FOUND= 404
      const val INTERNAL_SERVICE_ERROR = 500
      const val GATEWAY_TIME_OUT = 504
      const val FORBIDDEN = 403
      const val UNKNOWN = -1
    }