package com.arash.altafi.whatsappdownloder.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.ActivitySplashScreenBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.PermissionUtils
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod
import java.util.*
import kotlin.concurrent.timerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val registerStorageResult = PermissionUtils.register(
        this,
        object : PermissionUtils.PermissionListener {
            override fun observe(permissions: Map<String, Boolean>) {
                if (
                    permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true &&
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true
                ) {
                    goToMainActivity()
                } else {
                    showRequestPermissionDialog()
                }
            }
        })

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    goToMainActivity()
                } else {
                    showRequestPermissionDialog()
                }
            }
        }

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if (UtilsMethod.isAndroid11orHigher()) {
            if (!Environment.isExternalStorageManager()) {
                showRequestPermissionDialog()
            } else {
                goToMainActivity()
            }
        } else {
            if (PermissionUtils.isGranted(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).not()
            ) {
                showRequestPermissionDialog()
            } else {
                goToMainActivity()
            }
        }
    }

    private fun showRequestPermissionDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(resources.getString(R.string.storage_permission_request))
            setMessage(resources.getString(R.string.need_access_storage))
            setPositiveButton(resources.getString(R.string.accept)) { _, _ -> requestPermission() }
            setNegativeButton(resources.getString(R.string.exit)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            create()
            show()
        }
    }

    @SuppressLint("InlinedApi")
    private fun requestPermission() {
        if (UtilsMethod.isAndroid11orHigher()) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                requestPermissionLauncher.launch(intent)
            }
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PermissionUtils.requestPermission(
                    this, registerStorageResult,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } else {
                intentToSetting()
            }
        }
    }

    private fun goToMainActivity() {
        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, Constants.SPLASH_DELAY)
    }

    private fun intentToSetting() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
        )
    }

}