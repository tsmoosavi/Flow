package com.example.data.network.util



sealed class NetworkResult<out T> (val data: T? = null){
   class Success<out T>(data:T):NetworkResult<T>(data)
   class Error(val cause: Cause?):NetworkResult<Nothing>()
   class Loading<out T>:NetworkResult<T>()
}
