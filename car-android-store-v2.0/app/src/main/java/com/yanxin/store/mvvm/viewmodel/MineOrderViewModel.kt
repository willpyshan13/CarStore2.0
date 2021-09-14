package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.yanxin.store.MyApplication
import com.yanxin.store.activity.*
import com.yanxin.store.mvvm.reposityry.MineRepository
import com.yanxin.store.mvvm.ui.activity.MineOrderActivity
import com.yanxin.store.mvvm.ui.activity.MineWorkOrderActivity
import com.yanxin.store.mvvm.ui.activity.OnSiteSupportActivity
import com.yanxin.store.mvvm.ui.activity.StoreOrderReportActivity

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
class MineOrderViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val showShop = ObservableBoolean(MyApplication.isStore())

    override fun onCreate() {
        super.onCreate()
    }

    val exampleClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MyBuyCaseActivity::class.java)
        }
    })

    val dtcClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(BuyOrderListActivity::class.java)
        }
    })

    val askClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            var bundle = Bundle()
            bundle.putBoolean("is_order", true)
            startActivity(MyRushOrderActivity::class.java, bundle)
        }
    })

    val storeOrderClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineOrderActivity::class.java)
        }
    })

    val goodsOrderClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineOrderActivity::class.java)
        }
    })

    val orderNowClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MyPlaceOrderActivity::class.java)
        }
    })

    val orderNow1Click = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MySiteGrabOrderActivity::class.java)
        }
    })

    val groupClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineWorkOrderActivity::class.java)
        }
    })

    val workOrderClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineWorkOrderActivity::class.java)
        }
    })

}