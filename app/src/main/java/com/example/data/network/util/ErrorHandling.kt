package com.example.data.network.util

import com.example.NETWORK_EXCEPTION
import com.example.Resource
import com.example.Status
import com.example.flow.R
import com.google.gson.Gson
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


inline fun <T> safeApiCall(
    crossinline api: suspend () -> Response<T>
) = flow<NetworkResult<T>> {
    emit(NetworkResult.Loading())
    try {
        val response = api.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null)
            emit(NetworkResult.Success(body))
        else {
            val responseCode = response.code()
            val errorBody = response.errorBody()
            val serverMessage = convertErrorBody(errorBody)
            if (serverMessage != null)
                emit(NetworkResult.Error(Cause(msg = serverMessage.message)))
            else
                emit(NetworkResult.Error(handleErrorCode(responseCode)))
        }
    } catch (e: Throwable) {
        emit(NetworkResult.Error(handleErrorException(e)))

    }

}

fun <T> FlowCollector<NetworkResult<T>>.handleErrorException(throwable: Throwable): Cause {
    return when (throwable) {
        is SocketTimeoutException -> Cause(msgResId = HttpErrorTypes.BAD_REQUEST.msgResId)
        //            ConnectException
//        SocketTimeoutException
//                UnknownHostException
        else -> Cause(msgResId = HttpErrorTypes.UNKNOWN.msgResId)
    }
}

fun handleExceptions(e: Throwable): Cause {
    return when (e) {
        is HttpException -> Cause(msgResId = HttpErrorTypes.BAD_REQUEST.msgResId)
        //            ConnectException
//        SocketTimeoutException
//                UnknownHostException
        else -> Cause(msgResId = HttpErrorTypes.UNKNOWN.msgResId)
    }
}

fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
    return try {
        errorBody?.source()?.let {
            Gson().fromJson(it.readUtf8(), ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}

fun <T> FlowCollector<NetworkResult<T>>.handleErrorCode(code: Int): Cause {
    return HttpErrorTypes.values().find {
        it.code == code
    }
        ?.let {
            Cause(msgResId = it.msgResId)
        }
        ?: Cause(msgResId = HttpErrorTypes.UNKNOWN.msgResId)
}





