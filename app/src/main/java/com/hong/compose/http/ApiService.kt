package com.hong.compose.http

import com.hong.compose.bean.BaseResponse
import com.hong.compose.bean.IndexArticle
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * 获取首页文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getIndexArticle(@Path("page") page: Int): BaseResponse<IndexArticle>
}