package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import android.content.Intent
import androidx.databinding.ObservableField
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.will.habit.base.AppManager
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.yanxin.store.activity.LoginActivity
import com.yanxin.store.mvvm.reposityry.MineRepository
import com.yanxin.store.mvvm.ui.activity.MineWalletActivity

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
class AboutViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val versionText = ObservableField(AppUtils.getAppVersionName())

    val go2Login = SingleLiveEvent<Void>()

    override fun onCreate() {
        super.onCreate()
        setTitleText("关于我们")
    }

    val exitClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            go2Login.call()
        }
    })

}