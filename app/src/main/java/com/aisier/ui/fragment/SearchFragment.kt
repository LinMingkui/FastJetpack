package com.aisier.ui.fragment

import android.os.Bundle
import com.aisier.BR
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.architecture.base.DataBindingArguments
import com.aisier.databinding.FragmentSearchBinding
import com.aisier.vm.ApiViewModel

class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val model by lazy { getViewModel(ApiViewModel::class.java) }
    override fun init(savedInstanceState: Bundle?) {
        model.getRecommendUser()
    }

    override fun getDataBindingArguments(): DataBindingArguments? {
        return DataBindingArguments(BR.vm, model)
    }
}
