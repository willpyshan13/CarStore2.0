package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.will.habit.utils.KLog
import com.yanxin.store.mvvm.entity.ReqOnSiteSupportEntity
import com.yanxin.store.mvvm.entity.RespOnSiteSupportAmountEntity
import com.yanxin.store.mvvm.entity.RespStoreReportEntity
import com.yanxin.store.mvvm.reposityry.MineRepository
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

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
class OnSiteSupportViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val totalOrder = ObservableField("0")

    val totalAmount = ObservableField("0")

    val amountData = SingleLiveEvent<List<RespOnSiteSupportAmountEntity>>()

    val totalData = SingleLiveEvent<List<RespOnSiteSupportAmountEntity>>()
    private val dataFormat = SimpleDateFormat("yyyy年MM月")
    val countDate = ObservableField(dataFormat.format(System.currentTimeMillis()))

    val showDatePick = SingleLiveEvent<Void>()

    /**
     * 0    收入
     * 1    支出
     */
    var dataType = 0

    override fun onCreate() {
        super.onCreate()
        setTitleText("现场支持订单统计")
        getOnSiteSupport(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1)
    }

    private fun getOnSiteSupport(year: Int,month:Int){
        launch({
            val data = model.sceneOrderStatistics(ReqOnSiteSupportEntity(year,month))
            KLog.d("data=$data")
            totalOrder.set(data.total.toString())
            totalAmount.set(data.totalAmount.toString())
            amountData.value = data.amountList
            totalData.value = data.numList
        })
    }

    val countClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDatePick.call()
            dataType =0
        }
    })

    fun setTimeText(date: Date){
        val calendar = Calendar.getInstance()
        calendar.time = date
        countDate.set(dataFormat.format(date))
        getOnSiteSupport(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1)
    }
}