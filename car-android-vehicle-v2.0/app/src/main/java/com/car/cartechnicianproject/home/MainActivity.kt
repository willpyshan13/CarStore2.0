package com.car.cartechnicianproject.home

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.car.cartechnicianproject.R
import com.car.cartechnicianproject.base.uikit.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bnvView = findViewById<BottomNavigationView>(R.id.bnv_view)
        bnvView.itemIconTintList = null
        bnvView.itemRippleColor = null
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_Container) as NavHostFragment
        val navController = host.navController
        bnvView.setupWithNavController(navController)
    }
    
     //  TODO 添加角标 https://blog.csdn.net/a_zhon/article/details/78334515


}