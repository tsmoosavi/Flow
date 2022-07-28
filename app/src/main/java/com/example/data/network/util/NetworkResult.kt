package com.example.data.network.util



sealed class NetworkResult<T> (val data: T? = null, var cause: Cause? = null ){
   class Success<T>(data:T):NetworkResult<T>(data)
   class Error<T>(cause: Cause?):NetworkResult<T>(cause = cause)
   class Loading<T>:NetworkResult<T>()
}
