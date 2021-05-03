package com.example.xkdcapp.networking

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ComicApi {

    @GET("/archive/")
    suspend fun fetchComicList(): Response<ResponseBody>
}