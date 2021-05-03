package com.example.xkdcapp.comics

import com.example.xkdcapp.networking.ComicApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.Serializable
import java.util.regex.Pattern
import javax.inject.Inject


class FetchComicListUseCase @Inject constructor(private val comicApi: ComicApi) {

    sealed class Result : Serializable {
        data class Success(val data: List<Comic>?) : Result()
        object Failure : Result()
    }

    suspend fun fetchComicList(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = comicApi.fetchComicList()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(prosesResponse(response.body()!!))
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }

    private fun prosesResponse(responseBody: ResponseBody): List<Comic>? {
        return try {
            val splitList = responseBody.string()
            val pattern = Pattern.compile("<a href=\"/(\\d*)/\" title=\"(.*)\">(.*)</a><br/>")
            val matcher = pattern.matcher(splitList)
            val comicList = mutableListOf<Comic>()

            while (matcher.find()) {
                comicList.add(
                    Comic(
                        matcher.group(1),
                        matcher.group(3),
                        matcher.group(2)
                    )
                )
            }
            comicList.toList()
        } catch (e: OutOfMemoryError) {
            null
        }
    }
}