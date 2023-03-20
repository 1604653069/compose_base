package com.hong.compose.bean

class BaseResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
) {
    fun isSuccessFully() = errorCode == 0
}