package com.example.data.network.util

import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response


inline fun <T> safeApiCall(
    crossinline api: suspend ()-> Response<T>
) = flow<NetworkResult<T>> {
    emit(NetworkResult.Loading())
    try {
        val response = api.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null)
                emit(NetworkResult.Success(body))
        else{
//            val responseCode = response.code()
            val errorBody = response.errorBody()
            val serverMessage = convertErrorBody(errorBody)
            if (serverMessage != null)
                emit(NetworkResult.Error(Cause(msg =  serverMessage.message)))
            else
                emit(NetworkResult.Error(Cause(msg = "ErrorBody is null")))
        }
    }catch (e:Throwable){
        emit(NetworkResult.Error(Cause(msg = e.message.orEmpty())))
    }

}
fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
    return try {
        errorBody?.source()?.let {
            Gson().fromJson(it.readUtf8() ,ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}
data class ErrorResponse(
    val message:String
)