package com.arash.altafi.whatsappdownloder.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arash.altafi.whatsappdownloder.R
import java.io.Serializable

fun View.toShow() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showError() {
    showToast(getString(R.string.unknown_error))
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)!!
    else -> @Suppress("DEPRECATION") getSerializable(key) as T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key,
        T::class.java
    )
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}