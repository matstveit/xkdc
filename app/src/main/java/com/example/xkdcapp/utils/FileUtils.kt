package com.example.xkdcapp.utils

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import com.example.xkdcapp.BuildConfig
import com.example.xkdcapp.Constants.IMAGE_CACHE_DIRECTORY
import com.example.xkdcapp.common.di.app.AppScope
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

@AppScope
class FileUtils @Inject constructor(
    private val cacheProvider: CacheProvider,
    private val application: Application
) {

    private fun storeImage(bitmap: Bitmap, file: File) {
        try {
            val outPutStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outPutStream)
            outPutStream.flush()
            outPutStream.close()
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }

    fun getCompressedJpegAsBitmap(bitmap: Bitmap, quality: Int): Bitmap {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val byteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun storeBitmapAndGetUri(bitmap: Bitmap): Uri {
        val file = File(
            cacheProvider.getCacheDirectory(IMAGE_CACHE_DIRECTORY),
            "${Calendar.getInstance().timeInMillis}.jpeg"
        )

        storeImage(bitmap, file)

        return getUriForFile(
            application.applicationContext,
            BuildConfig.APPLICATION_ID + ".fileProvider",
            file
        )
    }
}