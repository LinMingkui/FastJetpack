package com.aisier.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.databinding.FragmentDownloadBinding
import com.aisier.vm.SavedStateViewModel

class DownloadFragment :
    BaseBindingFragment<FragmentDownloadBinding>(R.layout.fragment_download) {

    private val stateViewModel by viewModels<SavedStateViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        Log.i("SavedStateActivity--> ", "SavedStateViewModel: $stateViewModel")
        Log.i("SavedStateActivity--> ", "userName: ${stateViewModel.userName}")
        val value: String = stateViewModel.inputLiveData.value.toString()
        Log.i("SavedStateActivity--> ", "input text: ${value}")
        mBinding?.apply {
            submit.setOnClickListener {
                stateViewModel.userName = "Hello world"
                val inputText: String = editText.toString()
                stateViewModel.inputLiveData.value = inputText
            }
        }

    }
}