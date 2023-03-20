package com.hong.compose.repository

import android.util.Log
import com.hong.compose.http.RetrofitManager
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor() : BaseRepository() {
    /**
     * 获取首页文章
     */
    fun getIndexArticles() = request {
        Log.d("TAG","我这里被执行了")
        RetrofitManager.getApiService().getIndexArticle(0)
    }


}