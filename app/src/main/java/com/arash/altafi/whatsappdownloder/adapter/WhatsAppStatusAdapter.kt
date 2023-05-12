package com.arash.altafi.whatsappdownloder.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.WhatsappStatusBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.FileOperation
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod
import com.arash.altafi.whatsappdownloder.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File

class WhatsAppStatusAdapter(private var context: Context) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(WhatsappStatusBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(private val binding: WhatsappStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpViews(position: Int) {
            binding.apply {
                if (UtilsMethod.isVideoFile(differ.currentList[position].path))
                    ivVideo.toShow()
                else
                    ivVideo.toGone()

                if (File(Constants.SAVED_DIRECTORY + "/" + differ.currentList[position].name).exists()) {
                    lottieDownload.frame = Constants.LOTTIE_END_FRAME
                } else
                    lottieDownload.frame = Constants.LOTTIE_START_FRAME
            }
        }

        fun setStatusImage(position: Int) {
            if (UtilsMethod.isVideoFile(differ.currentList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(differ.currentList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(context).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivStatus)
                } catch (e: Exception) {
                    println(e)
                }
            } else {
                Glide.with(context).load(differ.currentList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivStatus)
            }
        }

        fun setOnClickListener(position: Int) {
            binding.apply {
                ivShareStatus.setOnClickListener {
                    FileOperation.shareFile(context, differ.currentList[position])
                }

                ivStatus.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.MEDIA_PATH_KEY, differ.currentList[position])
                    Navigation.findNavController(it).navigate(R.id.show_media_fragment, bundle)
                }

                lottieDownload.setOnClickListener {
                    if (FileOperation.saveFile(context, differ.currentList[position]))
                        lottieDownload.playAnimation()
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}