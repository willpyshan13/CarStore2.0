package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.will.habit.base.BaseListViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.binding.command.BindingConsumer
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.check
import com.will.habit.extection.launch
import com.will.habit.utils.KLog
import com.will.habit.widget.recycleview.paging.LoadCallback
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.activity.DtcMyBuyActivity
import com.yanxin.store.activity.GrabOrderDetailActivity
import com.yanxin.store.mvvm.reposityry.MineRepository
import com.yanxin.store.mvvm.ui.activity.MineInfoActivity
import com.yanxin.store.mvvm.ui.activity.MineInfoTechActivity
import com.yanxin.store.mvvm.ui.activity.MineWalletActivity
import com.yanxin.store.mvvm.ui.activity.NowOrderReportActivity
import me.tatarka.bindingcollectionadapter2.ItemBinding
import java.util.*

/**
 * Desc:
 *
 * Date: 2021-07-25
 * Copyright: Copyright (c) 2018-2020
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
class FragmentMessageViewModel(application: Application) : BaseListViewModel<MineRepository, FragmentMessageItem>(application) {
    companion object {
        val messageType_0 = ""  //全部消息
        val messageType_1 = "1"  //1工位通知
        val messageType_2 = "2"  //2发现消息
        val messageType_3 = "3"  //3现场支持
        val messageType_4 = "4"   //4商城消息
        val messageType_5 = "5" //5钱包消息
        val messageType_6 = "6"   //账户信息
    }

    var messageFilterType = 0

    var messageFilterText = ObservableField("未读")

    var messageType = messageType_0

    val showDialog = SingleLiveEvent<Void>()

    val filterClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDialog.call()
        }
    })


    /**
     * 关闭已打开的swipeLayout
     */
    val closeListener = BindingCommand<BindingAction>(object : BindingAction {
        override fun call() {

        }
    })

    var closeSwipe = ObservableBoolean(false)

    val readClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            readAllMessage()
        }
    })

    private fun readAllMessage() {
        launch({
            val data = model.updateMessageStatus()
            callReload(false)
        })
    }

    fun startPage(type: String, uuid: String) {
        //消息跳转页面
//        if (type ==messageType_5){
//            startActivity(MineWalletActivity::class.java)
//        }else if(type ==messageType_1){
//
//        }else if(type ==messageType_2){
//
//        }else if(type ==messageType_3){
//            val bundle = Bundle().apply { putString("grab_uuid",uuid) }
//            startActivity(GrabOrderDetailActivity::class.java,bundle)
//        }else if(type ==messageType_4){
////            startActivity(NowOrderReportActivity::class.java)
//        }else if(type ==messageType_6){
//            if (MyApplication.isStore()){
//                startActivity(MineInfoActivity::class.java)
//            }else {
//                startActivity(MineInfoTechActivity::class.java)
//            }
//        }
    }

    fun readMessage(id: String, item: FragmentMessageItem) {
        launch({
            model.updateMessageStatusById(id)
            item.readMessage()
        })
    }

    fun delMessage(id: String, item: FragmentMessageItem) {
        launch({
            model.delMessageStatusById(id)
            items.remove(item)
        })
    }

    override fun getDiffItemCallback(): DiffUtil.ItemCallback<FragmentMessageItem> {
        return object : DiffUtil.ItemCallback<FragmentMessageItem>() {
            override fun areItemsTheSame(
                    oldItem: FragmentMessageItem,
                    newItem: FragmentMessageItem
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                    oldItem: FragmentMessageItem,
                    newItem: FragmentMessageItem
            ): Boolean {
                return false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadInit()
    }

    val onCheckChange = BindingCommand<Int?>(object : BindingConsumer<Int?> {
        override fun call(t: Int?) {
            if (t != null) {
                KLog.d("t=$t")
                when (t) {
                    R.id.order_1 -> {
                        messageType = messageType_0
                    }
                    R.id.order_2 -> {
                        messageType = messageType_1
                    }
                    R.id.order_3 -> {
                        messageType = messageType_2
                    }
                    R.id.order_4 -> {
                        messageType = messageType_3
                    }
                    R.id.order_5 -> {
                        messageType = messageType_4
                    }
                    R.id.order_6 -> {
                        messageType = messageType_5
                    }
                    R.id.order_7 -> {
                        messageType = messageType_6
                    }
                }

                callReload(false)
            }
        }

    })

    override fun showEmptyState() {
    }

    override fun getItemBinding(): ItemBinding<FragmentMessageItem> {
        return ItemBinding.of(BR.viewModel, R.layout.fragment_message_item)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun loadData(pageIndex: Int, loadCallback: LoadCallback<FragmentMessageItem>) {
        launch({
            val data = model.getMessageList(messageType)
            val itemList = data.check().map { FragmentMessageItem(this, it) }
            loadCallback.onSuccess(itemList, pageIndex, 1)
        }, {
            loadCallback.onFailure()
        })
    }

}