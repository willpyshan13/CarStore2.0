package com.yanxin.store.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.yanxin.store.mvvm.entity.ReqOnSiteSupportEntity
import com.yanxin.store.mvvm.entity.ReqWithDrawEntity
import com.yanxin.store.mvvm.entity.RespStatisticsDataEntity
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
class MineWalletViewModel(application: Application) : BaseViewModel<MineRepository>(application) {
    val withDrawEvent = SingleLiveEvent<String>()
    val walletMoney = ObservableField("")
    val waitMoney = ObservableField("")
    val withDrawMoney = ObservableField("")
    val frozenMoney = ObservableField("")
    val withDrawTotal = ObservableField("")
    val outTotal = ObservableField("")

    var withDrawMoneyValue = 0f
    val total_1 = ObservableField("0")
    val total_2 = ObservableField("0")
    val total_3 = ObservableField("0")
    val total_4 = ObservableField("0")
    val withDraw_1 = ObservableField("0")
    val withDraw_4 = ObservableField("0")
    val withDraw_2 = ObservableField("0")
    val withDraw_3 = ObservableField("0")

    val money_1 = ObservableField("0")
    val money_1_progress = ObservableInt(0)
    val money_2_progress = ObservableInt(0)
    val money_3_progress = ObservableInt(0)
    val money_4_progress = ObservableInt(0)
    var maxProgress = 100
    val money_4 = ObservableField("0")
    val money_2 = ObservableField("0")
    val money_3 = ObservableField("0")


    val initPie = SingleLiveEvent<List<RespStatisticsDataEntity>>()

    val showDatePick = SingleLiveEvent<Void>()

    /**
     * 0    收入
     * 1    支出
     */
    var dataType = 0

    val dataFormat = SimpleDateFormat("yyyy年MM月")


    val payText = ObservableField(dataFormat.format(System.currentTimeMillis()))

    val incomeText = ObservableField(dataFormat.format(System.currentTimeMillis()))


    var currentYearPay = Calendar.getInstance().get(Calendar.YEAR)

    var currentMonthPay = Calendar.getInstance().get(Calendar.MONTH) + 1

    var currentYearOut = Calendar.getInstance().get(Calendar.YEAR)

    var currentMonthOut = Calendar.getInstance().get(Calendar.MONTH) + 1
    override fun onCreate() {
        super.onCreate()
        setTitleText("我的钱包")
        getAccountInfo()

        getStatistics()
        getStatisticsPay()
    }

    private fun getAccountInfo() {
        launch({
            val data = model.getAccountInfo()
            walletMoney.set("${data.accountAmt}")
            withDrawMoneyValue = data.waitAmount
            withDrawMoney.set("${data.withdrawAmount}")
            waitMoney.set("￥:${data.waitAmount}")
            frozenMoney.set("${data.frozenAmt}")
        })
    }

    fun setTimeText(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (dataType == 0) {
            payText.set(dataFormat.format(date))
            currentYearPay = calendar.get(Calendar.YEAR)
            currentMonthPay = calendar.get(Calendar.MONTH) + 1
            getStatistics()
        } else {
            incomeText.set(dataFormat.format(date))
            currentYearOut = calendar.get(Calendar.YEAR)
            currentMonthOut = calendar.get(Calendar.MONTH) + 1
            getStatisticsPay()
        }
    }

    fun startWithDraw(data: ReqWithDrawEntity) {
        launch({
            model.withdrawal(data)
            ToastUtils.showLong("提现成功")
            getAccountInfo()
        })
    }

    val withDrawClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (withDrawMoneyValue > 0) {
                withDrawEvent.value = withDrawMoneyValue.toString()
            } else {
                ToastUtils.showLong("当前可提现金额为0，当日营收款项7日后记入可提现金额，请稍后再试!")
            }
        }
    })

    val payTimeClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDatePick.call()
            dataType = 0
        }
    })

    val outcomeTimeClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDatePick.call()
            dataType = 1
        }
    })

    private fun getStatisticsPay() {
        launch({
            val pay = model.statisticsPayAmount(ReqOnSiteSupportEntity(currentYearPay, currentMonthPay))
            val maxMoney = pay.list.maxOf { it.data }.toFloat()

            pay.list.forEachIndexed { index, respStatisticsDataEntity ->
                when (respStatisticsDataEntity.name) {
                    "现场支持" -> {
                        if (maxMoney == 0F) {
                            money_4_progress.set(1)
                        } else {
                            var progress = (respStatisticsDataEntity.data / maxMoney * maxProgress).toInt()
                            if (progress == 0) {
                                progress = 1
                            }
                            money_4_progress.set(progress)
                        }
                        money_4.set(respStatisticsDataEntity.data.toString())
                    }
                    "DTC查询" -> {
                        if (maxMoney == 0F) {
                            money_3_progress.set(1)
                        } else {
                            var progress = (respStatisticsDataEntity.data / maxMoney * maxProgress).toInt()
                            if (progress == 0) {
                                progress = 1
                            }
                            money_3_progress.set(progress)
                        }
                        money_3.set(respStatisticsDataEntity.data.toString())
                    }
                    "购买案例" -> {
                        if (maxMoney == 0F) {
                            money_2_progress.set(1)
                        } else {
                            var progress = (respStatisticsDataEntity.data / maxMoney * maxProgress).toInt()
                            if (progress == 0) {
                                progress = 1
                            }
                            money_2_progress.set(progress)
                        }
                        money_2.set(respStatisticsDataEntity.data.toString())
                    }
                    else -> {
                        if (maxMoney == 0F) {
                            money_1_progress.set(1)
                        } else {
                            var progress = (respStatisticsDataEntity.data / maxMoney * maxProgress).toInt()
                            if (progress == 0) {
                                progress = 1
                            }
                            money_1_progress.set(progress)
                        }
                        money_1.set(respStatisticsDataEntity.data.toString())
                    }
                }
            }

            outTotal.set("总计：￥${pay.count}")

        })
    }

    private fun getStatistics() {
        launch({
            val income = model.statisticsIncomeAmount(ReqOnSiteSupportEntity(currentYearOut, currentMonthOut))
            initPie.value = income.list
            income.list.forEachIndexed { index, respStatisticsDataEntity ->
                when (respStatisticsDataEntity.name) {
                    "现场支持" -> {
                        total_4.set(respStatisticsDataEntity.data.toString())
                        withDraw_4.set(respStatisticsDataEntity.data.toString())
                    }
                    "回答收入" -> {
                        total_3.set(respStatisticsDataEntity.data.toString())
                        withDraw_3.set(respStatisticsDataEntity.data.toString())
                    }
                    "案例收入" -> {
                        total_2.set(respStatisticsDataEntity.data.toString())
                        withDraw_2.set(respStatisticsDataEntity.data.toString())
                    }
                    else -> {
                        total_1.set(respStatisticsDataEntity.data.toString())
                        withDraw_1.set(respStatisticsDataEntity.data.toString())
                    }
                }
            }
            withDrawTotal.set("总计：￥${income.count}")
        })
    }
}