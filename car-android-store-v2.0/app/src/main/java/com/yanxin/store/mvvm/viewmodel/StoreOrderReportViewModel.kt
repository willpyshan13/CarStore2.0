package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.yanxin.store.mvvm.entity.RespStoreReportEntity
import com.yanxin.store.mvvm.reposityry.MineRepository
import java.text.SimpleDateFormat
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
class StoreOrderReportViewModel(application: Application) : BaseViewModel<MineRepository>(application) {
    private val dataFormat = SimpleDateFormat("yyyy年MM月")

    val payText = ObservableField(dataFormat.format(System.currentTimeMillis()))

    val orderText = ObservableField(dataFormat.format(System.currentTimeMillis()))

    val amountDataChange = SingleLiveEvent<RespStoreReportEntity>()

    val amountTotal = ObservableField("")

    val amountDate = ObservableField("")

    val countDataChange = SingleLiveEvent<RespStoreReportEntity>()

    val countTotal = ObservableField("")

    val countDate = ObservableField("")

    val showDatePick = SingleLiveEvent<Void>()

    /**
     * 0    收入
     * 1    支出
     */
    var dataType = 0

    override fun onCreate() {
        super.onCreate()
        setTitleText("商城订单统计报表")
        getStoreAmountData()
        getStoreCountData()
    }

    val countClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDatePick.call()
            dataType =0
        }
    })

    val amountTimeClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDatePick.call()
            dataType =1
        }
    })

    private fun getStoreAmountData(){
        launch({
            val count = model.queryGoodsGroupAmount(orderText.get().toString())
            countDataChange.value = count
            countTotal.set(count.allTotal.toString())
        })
    }

    private fun getStoreCountData(){
        launch({
            val count = model.queryGoodsGroupCount(orderText.get().toString())
            amountDataChange.value = count
            amountTotal.set(count.allTotal.toString())
        })
    }

    fun setTimeText(date: Date){
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (dataType ==0){
            countDate.set(dataFormat.format(date))
            getStoreCountData()
        }else{
            amountDate.set(dataFormat.format(date))
            getStoreAmountData()
        }
    }
}