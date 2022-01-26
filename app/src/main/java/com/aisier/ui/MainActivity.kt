package com.aisier.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.aisier.R
import com.aisier.architecture.base.BaseBindingActivity
import com.aisier.databinding.ActivityHomeBinding

class MainActivity : BaseBindingActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun init(savedInstanceState: Bundle?) {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        val navController = navHostFragment.navController
        mBinding?.apply {
            NavigationUI.setupWithNavController(
                bottomNavigationView,
                navController
            )
        }
    }
}
