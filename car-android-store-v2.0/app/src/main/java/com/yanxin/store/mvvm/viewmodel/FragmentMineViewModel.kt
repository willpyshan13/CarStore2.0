package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.will.habit.utils.KLog
import com.will.habit.widget.recycleview.FooterStatus
import com.will.habit.widget.recycleview.RefreshStatus
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.activity.*
import com.yanxin.store.mvvm.reposityry.MineRepository
import com.yanxin.store.mvvm.ui.activity.*
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Desc:
 *
 * Date: 2021-7-24
 * Copyright: Copyright (c) 2018-2020
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
class FragmentMineViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val itemBinding =
            ItemBinding.of<FragmentMineBannerItem>(BR.viewModel, R.layout.activity_banner_item)
    val items = ObservableArrayList<FragmentMineBannerItem>()


    val walletMoney = ObservableField("")
    val orderCount = ObservableField("")
    val imageHeader = ObservableField("")
    val score = ObservableField("0")

    val userName = ObservableField("")

    val nameDesc1 = ObservableField(if (MyApplication.isStore()) "店铺" else "技师")

    val nameDesc2 = ObservableField("")

    val withDrawEvent = SingleLiveEvent<Void>()

    val shareEvent = SingleLiveEvent<Void>()

    val showShop = ObservableBoolean(MyApplication.isStore())
    val refreshStatus = ObservableInt(RefreshStatus.STATUS_NONE)
    override fun onCreate() {
        super.onCreate()
        getAccountInfo()
    }

    val onRefreshCommand = BindingCommand<BindingAction>(object : BindingAction {
        override fun call() {
            getAccountInfo()
            refreshStatus.set(RefreshStatus.STATUS_FORBID)
        }
    })

    val infoClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (MyApplication.isStore()) {
                startActivity(MineInfoActivity::class.java)
            } else {
                startActivity(MineInfoTechActivity::class.java)
            }
        }
    })

    val settingClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(AboutActivity::class.java)
        }
    })

    val walletClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineWalletActivity::class.java)
        }
    })

    val withDrawClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineWalletActivity::class.java)
        }
    })

    val orderClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MineOrderActivity::class.java)
        }
    })

    val orderNowClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(OnSiteSupportActivity::class.java)
        }
    })

    val orderStoreClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(StoreOrderReportActivity::class.java)
        }
    })

    val techOrderClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(StoreOrderReportActivity::class.java)
        }
    })

    val dtcClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(DtcMyBuyActivity::class.java)
        }
    })

    val exampleClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(MyCaseActivity::class.java)
        }
    })

    val questionClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            var bundle = Bundle()
            bundle.putBoolean("is_order", false)
            startActivity(RushOrderActivity::class.java, bundle)
        }
    })

    val shareClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            shareEvent.call()
        }
    })

    private fun getAccountInfo() {
        launch({
            val data = model.getAccountInfo()
            val bannerData = model.getBannerList()
//            val headUrl = model.getAccountHeaderUrl()
            val bannerList = bannerData.map { FragmentMineBannerItem(this, it.img) }
            items.addAll(bannerList)
            imageHeader.set(data.userInfoRes.headImage)
            walletMoney.set("${data.accountAmt}")
            orderCount.set("${data.orderNum}")
            nameDesc2.set(data.userInfoRes.type)
            score.set("${data.score}")
        })
    }

}