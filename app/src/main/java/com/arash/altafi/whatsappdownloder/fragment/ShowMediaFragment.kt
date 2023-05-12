package com.arash.altafi.whatsappdownloder.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.FragmentShowMediaBinding
import com.arash.altafi.whatsappdownloder.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File


class ShowMediaFragment : Fragment() {

    private val binding by lazy {
        FragmentShowMediaBinding.inflate(layoutInflater)
    }

    private lateinit var statusFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setUpView(statusFile)
        setOnclick()
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            if (videoView.isPlaying)
                videoView.pause()
        }
    }

    private fun init() {
        statusFile = arguments?.serializable(Constants.MEDIA_PATH_KEY) as File
    }

    private fun setUpView(file: File) {
        binding.apply {
            if (UtilsMethod.isImageFile(file.name)) {
                Glide.with(requireContext()).load(statusFile)
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.zvPreview)

                zvPreview.toShow()
                videoView.toGone()
            } else {
                setUpVideoView(statusFile)
                zvPreview.toGone()
                videoView.toShow()
            }
        }
    }

    private fun setUpVideoView(file: File) {
        binding.apply {
            val mediaController = MediaController(context)
            videoView.apply {
                setOnPreparedListener { mp ->
                    mp.setOnVideoSizeChangedListener { _, _, _ ->
                        mediaController.setAnchorView(videoView)
                        mediaController.setMediaPlayer(videoView)
                    }
                }
                setVideoPath(file.path)
                seekTo(1)
                setMediaController(mediaController)
                start()
            }
        }
    }

    private fun setOnclick() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnDelete.setOnClickListener {
                showDeleteDialog()
            }

            btnSave.setOnClickListener {
                FileOperation.saveFile(requireActivity(), statusFile)
            }

            btnShare.setOnClickListener {
                FileOperation.shareFile(requireActivity(), statusFile)
            }
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.delete_status))
            .setMessage(requireContext().getString(R.string.do_you_want_to_delete))
            .setCancelable(true)
            .setPositiveButton(requireContext().getString(R.string.yes)) { _, _ ->
                FileOperation.deleteFile(requireActivity(), statusFile)
                findNavController().navigateUp()
            }
            .setNegativeButton(requireContext().getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

}