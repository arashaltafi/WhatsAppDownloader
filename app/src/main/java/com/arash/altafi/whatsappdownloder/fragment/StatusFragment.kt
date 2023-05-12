package com.arash.altafi.whatsappdownloder.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.FragmentWhatsappStatusBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.MyIntent
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod
import com.arash.altafi.whatsappdownloder.utils.*
import com.arash.altafi.whatsappdownloder.adapter.SavedStatusAdapter
import com.arash.altafi.whatsappdownloder.adapter.WhatsAppStatusAdapter
import java.io.File

class StatusFragment : Fragment() {

    private val binding by lazy {
        FragmentWhatsappStatusBinding.inflate(layoutInflater)
    }

    private var statusFileList: ArrayList<File> = ArrayList()
    private lateinit var directoryAddress: String
    private var recyclerViewState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        return binding.root
    }

    private fun init() {
        if (arguments != null) {
            this.directoryAddress = arguments?.getString(Constants.DIRECTORY_KEY).toString()
        } else {
            directoryAddress = if (UtilsMethod.isAndroid11orHigher())
                Constants.NEW_WHATSAPP_DIRECTORY
            else
                Constants.WHATSAPP_DIRECTORY
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.rvStatus.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        setUpList()
        if (recyclerViewState != null)
            binding.rvStatus.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setOnClick() {
        binding.btnOpenWhatsapp.setOnClickListener {
            if (directoryAddress == Constants.WHATSAPP_BUSINESS_DIRECTORY
                || directoryAddress == Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY
            ) {
                MyIntent.openWhatsAppBusiness(requireContext())
            } else {
                MyIntent.openWhatsApp(requireContext())
            }
        }
    }

    private fun setUpList() {
        statusFileList.clear()
        val statusDirectory = File(directoryAddress)

        if (statusDirectory.exists())
            prepareStatusList(statusDirectory)
        else
            showDirectoryNotExist()
    }

    private fun showList() {
        binding.apply {
            rvStatus.toShow()
            tvWarning.toGone()
            ivWarning.toGone()
            btnOpenWhatsapp.toGone()
        }
    }

    private fun hiddenList() {
        binding.apply {
            rvStatus.toGone()
            tvWarning.toShow()
            ivWarning.toShow()
        }
    }

    private fun prepareStatusList(statusDirectory: File) {
        val fileList: Array<out File>? = statusDirectory.listFiles()

        for (file in fileList!!) {
            if (UtilsMethod.isImageFile(file.name) || UtilsMethod.isVideoFile(file.name))
                statusFileList.add(file)
        }

        if (statusFileList.isNotEmpty()) {
            showList()
            setUpRecyclerView()
        } else {
            hiddenList()
            showNoStatusWasObserved()
        }
    }

    private fun showNoStatusWasObserved() {
        binding.apply {
            when (directoryAddress) {
                Constants.WHATSAPP_DIRECTORY, Constants.NEW_WHATSAPP_DIRECTORY -> {
                    tvWarning.text = getString(R.string.no_w_status_was_observed)
                    btnOpenWhatsapp.text = getString(R.string.open_whatsapp)
                    btnOpenWhatsapp.toShow()
                }

                Constants.WHATSAPP_BUSINESS_DIRECTORY, Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY -> {
                    tvWarning.text = getString(R.string.no_wb_status_was_observed)
                    btnOpenWhatsapp.text = getString(R.string.open_business_whatsapp)
                    btnOpenWhatsapp.toGone()
                }

                Constants.SAVED_DIRECTORY, Constants.SAVED_DIRECTORY -> {
                    tvWarning.text = getString(R.string.no_status_saved)
                    btnOpenWhatsapp.toGone()
                }
            }
        }
    }

    private fun showDirectoryNotExist() {
        binding.apply {
            when (directoryAddress) {
                Constants.WHATSAPP_DIRECTORY, Constants.NEW_WHATSAPP_DIRECTORY -> {
                    tvWarning.text = getString(R.string.whatsapp_is_not_installed)
                }

                Constants.WHATSAPP_BUSINESS_DIRECTORY, Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY -> {
                    tvWarning.text = getString(R.string.whatsapp_business_is_not_installed)
                }

                Constants.SAVED_DIRECTORY -> tvWarning.text = getString(R.string.no_status_saved)

                else -> tvWarning.text = getString(R.string.directory_not_exist)
            }
            btnOpenWhatsapp.toGone()
            hiddenList()
        }
    }

    private fun setUpRecyclerView() {
        if (directoryAddress == Constants.SAVED_DIRECTORY) {
            val statusAdapter = SavedStatusAdapter(requireContext(), statusFileList)
            binding.rvStatus.apply {
                adapter = statusAdapter
            }
        } else {
            val statusAdapter = WhatsAppStatusAdapter(requireContext())
            statusAdapter.differ.submitList(statusFileList)
            binding.rvStatus.apply {
                adapter = statusAdapter

            }
        }
    }

}