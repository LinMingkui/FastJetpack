package com.aisier.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.databinding.FragmentMainBinding

class MainFragment : BaseBindingFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bt_api).setOnClickListener {
            view.findNavController().navigate(R.id.netListFragment)
        }
        view.findViewById<Button>(R.id.bt_save_state).setOnClickListener {
            view.findNavController().navigate(R.id.savedStateFragment)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding?.apply {
            btApi.setOnClickListener {
                btApi.findNavController().navigate(R.id.netListFragment)
            }
            btSaveState.setOnClickListener {
                btSaveState.findNavController().navigate(R.id.savedStateFragment)
            }
        }
    }

}