package com.aisier.ui.fragment

import android.os.Bundle
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.databinding.FragmentSearchBinding

class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun init(savedInstanceState: Bundle?) {
        mBinding?.apply {
//            btApi.setOnClickListener {
//                btApi.findNavController().navigate(R.id.netListFragment)
//            }
//            btSaveState.setOnClickListener {
//                btSaveState.findNavController().navigate(R.id.savedStateFragment)
//            }
        }
    }

}