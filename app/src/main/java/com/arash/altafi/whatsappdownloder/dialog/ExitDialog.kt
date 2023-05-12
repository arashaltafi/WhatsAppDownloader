package com.arash.altafi.whatsappdownloder.dialog

import android.app.Activity
import android.app.Dialog
import android.widget.AbsListView
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.DialogExitBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.MyIntent
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod

class ExitDialog(private var activity: Activity) : Dialog(activity) {

    private val binding by lazy {
        DialogExitBinding.inflate(layoutInflater)
    }

    init {
        setContentView(binding.root)
        config()
        setOnClick()
    }

    private fun config() {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.attributes?.windowAnimations = R.style.scale_anim_style
        window?.setLayout(
            UtilsMethod.getScreenWidth(context, Constants.DIALOG_WIDTH_PERCENTAGE),
            AbsListView.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setOnClick() {
        binding.apply {
            btnClose.setOnClickListener {
                cancel()
            }

            btnExit.setOnClickListener {
                activity.finish()
            }

            btnOtherApp.setOnClickListener {
                MyIntent.otherAppIntent(activity)
            }
        }
    }
}