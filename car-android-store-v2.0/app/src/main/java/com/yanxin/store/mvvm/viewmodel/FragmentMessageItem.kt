package com.yanxin.store.mvvm.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.will.habit.base.ItemViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.yanxin.store.mvvm.entity.RespMessageEntity

/**
 *
 * <p>首页，收藏 item 样式
 * Date: 2021-07-25
 * Updater:
 * Update Time:
 * Update Comments:
 * @param data ActionItemEntity    具体数据
 * @constructor
 *
 * Author: will
 */
class FragmentMessageItem(viewModel: FragmentMessageViewModel,val data: RespMessageEntity) : ItemViewModel<FragmentMessageViewModel>(viewModel) {

    val showReadView = ObservableInt(if (data.status ==1) View.VISIBLE else View.GONE)
    var closeSwipe = ObservableBoolean(false)

    val onItemCollect = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            viewModel.readMessage(data.uuid,this@FragmentMessageItem)
            viewModel.startPage(data.type,data.orderUuid)
        }
    })

    val onItemDel = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            viewModel.delMessage(data.uuid,this@FragmentMessageItem)
        }
    })

    fun readMessage(){
        showReadView.set(View.GONE)
    }
}