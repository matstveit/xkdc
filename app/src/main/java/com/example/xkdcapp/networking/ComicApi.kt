package com.example.xkdcapp.networking

import com.example.xkdcapp.comics.ComicDetail
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicApi {

    @GET("/archive/")
    suspend fun fetchComicList(): Response<ResponseBody>

    @GET("/{imageId}/info.0.json")
    suspend fun fetchComic(@Path("imageId") numberId: String): Response<ComicDetail>
}