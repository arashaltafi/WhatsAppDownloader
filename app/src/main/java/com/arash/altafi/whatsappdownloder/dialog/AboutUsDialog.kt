package com.arash.altafi.whatsappdownloder.dialog

import android.app.Dialog
import android.content.Context
import android.widget.AbsListView
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.DialogAboutUsBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod

class AboutUsDialog(context: Context) : Dialog(context) {

    private val binding by lazy {
        DialogAboutUsBinding.inflate(layoutInflater)
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
        binding.ivClose.setOnClickListener {
            cancel()
        }
    }
}