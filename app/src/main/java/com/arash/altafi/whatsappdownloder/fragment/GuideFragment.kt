package com.arash.altafi.whatsappdownloder.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arash.altafi.whatsappdownloder.databinding.FragmentGuideBinding
import com.arash.altafi.whatsappdownloder.utils.Constants

class GuideFragment : Fragment() {

    private val binding by lazy {
        FragmentGuideBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lottieDownload2.frame = Constants.LOTTIE_END_FRAME
    }
}