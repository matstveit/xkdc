package com.example.xkdcapp.utils

import com.example.xkdcapp.common.di.app.AppScope
import java.io.File
import javax.inject.Inject

@AppScope
class CacheProvider @Inject constructor(private var cacheDirectory: File?) {

    fun getCacheDirectory(directoryName: String): File {
        val fileDirectory = File(cacheDirectory?.path + File.separator + directoryName)
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }
        return fileDirectory
    }

    fun clearCacheDirectory(directoryName: String): Boolean {
        val fileDirectory = File(cacheDirectory?.path + File.separator + directoryName)
        return fileDirectory.deleteRecursively()
    }
}