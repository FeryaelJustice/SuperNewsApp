package com.feryaeljustice.supernewsapp.data.remote

import com.feryaeljustice.supernewsapp.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sources") sources: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("apiKey") apiKey: String,
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("q") query: String,
        @Query("sources") sources: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("apiKey") apiKey: String,
    ): NewsResponse
}
