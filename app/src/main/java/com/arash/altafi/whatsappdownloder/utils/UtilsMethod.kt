package com.arash.altafi.whatsappdownloder.utils

import android.content.Context
import android.os.Build

object UtilsMethod {

    fun getScreenWidth(context: Context, percentage: Int = 100): Int =
        context.resources.displayMetrics.widthPixels * percentage / 100

    fun isImageFile(fileName: String): Boolean {
        return (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(
            ".gif"
        ))
    }

    fun isVideoFile(fileName: String): Boolean =
        (fileName.endsWith(".mp4") || fileName.endsWith(".mkv"))

    fun isAndroid11orHigher() = (Build.VERSION.SDK_INT >= 30)

}