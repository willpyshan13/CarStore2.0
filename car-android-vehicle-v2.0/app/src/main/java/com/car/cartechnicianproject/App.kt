package com.car.cartechnicianproject

import android.app.Application
import android.util.Log

/**
 * @Description:
 * @Author: fengzeyuan
 * @Date: 2021/8/8 2:20 下午
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //  TODO init 
        Log.d("javen","all ${this::class}")
    }
}