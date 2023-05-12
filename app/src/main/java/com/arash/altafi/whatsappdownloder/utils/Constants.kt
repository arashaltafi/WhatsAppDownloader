package com.arash.altafi.whatsappdownloder.utils

import android.os.Environment

object Constants {


    //SplashActivity
    const val SPLASH_DELAY: Long = 1000

    //MainActivity
    const val DIRECTORY_KEY = "directory_key"
    const val MAX_NUMBER_OF_OPEN_APP = 2

    val WHATSAPP_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"

    val NEW_WHATSAPP_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses"

    val WHATSAPP_BUSINESS_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsApp Business/Media/.Statuses"

    val NEW_WHATSAPP_BUSINESS_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses"

    val SAVED_DIRECTORY =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/WhatsAppStatusDirectory"

    //WhatsappStatusAdepter
    const val LOTTIE_START_FRAME: Int = 22
    const val LOTTIE_END_FRAME: Int = 100
    const val MEDIA_PATH_KEY = "media_path_key"


    //Dialogs
    const val DIALOG_WIDTH_PERCENTAGE = 90
    const val DISABLE_BUTTON_ALPHA = 0.4f
    const val ENABLE_BUTTON_ALPHA = 1f


    //MyIntent
    const val WHATSAPP_PACKAGE = "com.whatsapp"
    const val WHATSAPP_BUSINESS_PACKAGE = "com.whatsapp.w4b"


    //MySharedPreferences
    const val SHARED_PREFERENCES_NAME = "shared_preferences_name"
    const val IS_REGISTER_COMMENT = "is_register_comment"
    const val NUMBER_OF_OPEN_APP = "NUMBER_OF_OPEN_APP"

}