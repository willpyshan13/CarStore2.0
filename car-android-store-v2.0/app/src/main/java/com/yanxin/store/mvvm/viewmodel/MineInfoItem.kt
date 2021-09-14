package com.yanxin.store.mvvm.viewmodel

import androidx.databinding.ObservableField
import com.will.habit.base.ItemViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.yanxin.store.mvvm.entity.StoreUserRes

/**
 *
 * <p>
 * Date: 2021-07-25
 * Updater:
 * Update Time:
 * Update Comments:
 * @param data ActionItemEntity    具体数据
 * @constructor
 *
 * Author: will
 */
class MineInfoItem(viewModel: MineInfoViewModel, val data: StoreUserRes) : ItemViewModel<MineInfoViewModel>(viewModel) {

    val telText = ObservableField(data.mobile)

    val nameText = ObservableField(data.userName)

    val typeText = ObservableField(data.position)

    var personUuid:String? = ""

    val onTypeClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            viewModel.showRolePick(this@MineInfoItem)
        }
    })

    fun getItemData():StoreUserRes{
        data.userName = nameText.get()
        data.mobile = telText.get()
        data.personType = personUuid
        data.storeUuid =viewModel.getUuid()
        return data
    }
}