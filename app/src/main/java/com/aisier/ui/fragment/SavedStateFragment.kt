package com.aisier.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.databinding.FragmentSavedStateBinding
import com.aisier.vm.SavedStateViewModel

class SavedStateFragment :
    BaseBindingFragment<FragmentSavedStateBinding>(R.layout.fragment_saved_state) {

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