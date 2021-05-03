package com.example.xkdcapp.comics

import com.example.xkdcapp.networking.ComicDetailApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.Serializable
import javax.inject.Inject

class FetchComicDetailUseCase @Inject constructor(
    private val comicDetailApi: ComicDetailApi
) {

    sealed class Result: Serializable {
        data class Success(val data: String?) : Result()
        object Failure : Result()
    }

    suspend fun fetchComicDetail(comicId: String, comicName: String): Result {
        val page = "$comicId:_${comicName.replace(" ", "_")}"
        return withContext(Dispatchers.IO) {
            try {
                val response = comicDetailApi.fetchComicDetail(page)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(prosesResponse(response.body()!!))
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                Timber.e(t.localizedMessage)
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }

    private fun prosesResponse(responseBody: ResponseBody): String? {
        return try {
            val text = responseBody.string()
            //TODO: fix this ugly regex stuff
            text.replace(".*titletext = ".toRegex(), "")
                .replace("\\\\n\\}\\}\\\\n\\\\n==Explanation==\\\\n".toRegex(), "")
                .replace("\\\\n\\\\n==Transcript==\\\\n.*".toRegex(), "")
                .replace("\\{.*?[ \\|\\}]+".toRegex(), " ")
                .replace("\\[.*?[ \\|\\]]+".toRegex(), " ")
                .replace("[\\]\\}]+".toRegex(), ".")
                .replace("\\\\n".toRegex(), " ")
                .replace("[\\|\\\\]".toRegex(), " ")
        } catch (e: OutOfMemoryError) {
            null
        }
    }
}