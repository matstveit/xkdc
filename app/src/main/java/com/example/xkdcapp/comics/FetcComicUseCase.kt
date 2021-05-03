package com.example.xkdcapp.comics

import com.example.xkdcapp.networking.ComicApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Serializable
import javax.inject.Inject

class FetchComicUseCase @Inject constructor(private val comicApi: ComicApi) {

    sealed class Result : Serializable {
        data class Success(val data: ComicDetail) : Result()
        object Failure : Result()
    }

    suspend fun fetchComic(imageId: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = comicApi.fetchComic(imageId)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
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
}