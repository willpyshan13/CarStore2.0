package com.yanxin.store.mvvm.viewmodel

import com.will.habit.base.ItemViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand

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
class MineWorkOrderItem(viewModel: MineWorkOrderViewModel) : ItemViewModel<MineWorkOrderViewModel>(viewModel) {


    val onItemCollect = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })
}