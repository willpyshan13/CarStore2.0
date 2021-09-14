package com.yanxin.store.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.NetworkUtils
import com.will.habit.base.BaseViewModel
import com.yanxin.store.mvvm.reposityry.MineRepository

/**
 * Desc:
 *
 * Date: 2020-12-16
 * Copyright: Copyright (c) 2018-2020
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
class NowOrderReportViewModel(application: Application) : BaseViewModel<MineRepository>(application) {
    override fun onCreate() {
        super.onCreate()
        setTitleText("商城订单统计报表")
    }
}