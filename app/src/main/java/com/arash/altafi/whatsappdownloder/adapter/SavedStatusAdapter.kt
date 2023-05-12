package com.arash.altafi.whatsappdownloder.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.SavedStatusBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.FileOperation
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod
import com.arash.altafi.whatsappdownloder.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File

class SavedStatusAdapter(private var context: Context, private var statusList: MutableList<File>) :
    RecyclerView.Adapter<SavedStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SavedStatusBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)
    }

    override fun getItemCount(): Int = statusList.size

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(private val binding: SavedStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUpViews(position: Int) {
            binding.apply {
                if (UtilsMethod.isVideoFile(statusList[position].path))
                    ivVideo.toShow()
                else
                    ivVideo.toGone()
            }
        }

        fun setStatusImage(position: Int) {
            if (UtilsMethod.isVideoFile(statusList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(statusList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(context).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivStatus)

                } catch (e: Exception) {
                    println(e)
                }
            } else {
                Glide.with(context).load(statusList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivStatus)
            }
        }

        fun setOnClickListener(position: Int) {
            binding.apply {
                ivShareStatus.setOnClickListener {
                    FileOperation.shareFile(context, statusList[position])
                }
                ivDelete.setOnClickListener {
                    showDeleteDialog(position)
                }
                ivStatus.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.MEDIA_PATH_KEY, statusList[position])
                    Navigation.findNavController(it).navigate(R.id.show_media_fragment, bundle)
                }
            }
        }
    }

    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.delete_status))
            .setMessage(context.getString(R.string.do_you_want_to_delete))
            .setCancelable(true)
            .setPositiveButton(context.getString(R.string.yes)) { _, _ -> deleteStatus(position) }
            .setNegativeButton(context.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    private fun deleteStatus(position: Int) {
        FileOperation.deleteFile(context, statusList[position])
        statusList.removeAt(position)
        notifyItemRemoved(position)
    }

}