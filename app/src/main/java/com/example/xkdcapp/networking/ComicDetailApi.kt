package com.example.xkdcapp.networking

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicDetailApi {

    @GET("/wiki/api.php?action=parse&prop=wikitext&sectiontitle=Explanation&format=json")
    suspend fun fetchComicDetail(@Query("page") page: String): Response<ResponseBody>
}