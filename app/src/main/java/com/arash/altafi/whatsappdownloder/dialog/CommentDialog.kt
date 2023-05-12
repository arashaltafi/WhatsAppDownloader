package com.arash.altafi.whatsappdownloder.dialog

import android.app.Dialog
import android.view.View
import android.widget.AbsListView
import android.widget.RatingBar
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.DialogCommentBinding
import com.arash.altafi.whatsappdownloder.utils.*
import com.arash.altafi.whatsappdownloder.activity.MainActivity

class CommentDialog(mainActivity: MainActivity) : Dialog(mainActivity) {

    private val binding by lazy {
        DialogCommentBinding.inflate(layoutInflater)
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
            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, v, _ ->
                    if (v == 0f) {
                        tvCommentCaffeBazaar.toGone()
                        btnComment.text = context.getString(R.string.comment)
                        btnComment.alpha = Constants.DISABLE_BUTTON_ALPHA
                    } else {
                        tvCommentCaffeBazaar.toShow()
                        btnComment.alpha = Constants.ENABLE_BUTTON_ALPHA

                        if (v < 4) {
                            btnComment.text = context.getString(R.string.send_email)
                            tvCommentCaffeBazaar.text =
                                context.getString(R.string.please_send_email)
                        } else {
                            btnComment.text = context.getString(R.string.comment)
                            tvCommentCaffeBazaar.text =
                                context.getString(R.string.please_register_your_comment)
                        }
                    }

                }

            ivClose.setOnClickListener {
                cancel()
            }

            btnComment.setOnClickListener(View.OnClickListener {
                when (ratingBar.rating) {
                    0f -> {
                        context.showToast(context.getString(R.string.submitt_comment))
                        return@OnClickListener
                    }
                    in 1f..3f -> MyIntent.emailIntent(context)
                }
                cancel()
            })
        }
    }

}