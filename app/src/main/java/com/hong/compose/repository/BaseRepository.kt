package com.hong.compose.repository

import androidx.lifecycle.liveData
import com.hong.compose.bean.BaseResponse
import kotlinx.coroutines.Dispatchers

open class BaseRepository {
    protected fun <T> request(block: suspend () -> BaseResponse<T>) =
        liveData(Dispatchers.IO) {
            val result = try {
                val data = block()
                if (data.isSuccessFully())
                    Result.success(data.data)
                else
                    Result.failure(RuntimeException(data.errorMsg))
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}