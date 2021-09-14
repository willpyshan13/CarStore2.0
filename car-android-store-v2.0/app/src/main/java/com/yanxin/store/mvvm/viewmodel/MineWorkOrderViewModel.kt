package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.will.habit.base.BaseListViewModel
import com.will.habit.extection.launch
import com.will.habit.utils.KLog
import com.will.habit.widget.recycleview.paging.LoadCallback
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.mvvm.entity.ReqWorkOrderEntity
import com.yanxin.store.mvvm.reposityry.MineRepository
import me.tatarka.bindingcollectionadapter2.ItemBinding

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
class MineWorkOrderViewModel(application: Application) : BaseListViewModel<MineRepository,MineWorkOrderItem>(application) {
    override fun getDiffItemCallback(): DiffUtil.ItemCallback<MineWorkOrderItem> {
        return object :DiffUtil.ItemCallback<MineWorkOrderItem>(){
            override fun areItemsTheSame(
                oldItem: MineWorkOrderItem,
                newItem: MineWorkOrderItem
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: MineWorkOrderItem,
                newItem: MineWorkOrderItem
            ): Boolean {
                return false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        setTitleText("工位订单")
        loadInit()
    }

    override fun showEmptyState() {
    }

    override fun getItemBinding(): ItemBinding<MineWorkOrderItem> {
        return ItemBinding.of(BR.viewModel, R.layout.activity_mine_work_order_item)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun loadData(pageIndex: Int, loadCallback: LoadCallback<MineWorkOrderItem>) {
        launch({
               val data = model.queryOrderVehicleStationList(ReqWorkOrderEntity(intArrayOf(0).asList(),pageIndex,uuid = MyApplication.getUserUuid()))

            KLog.d("data size=${data.data?.size}")
        },{
            loadCallback.onFailure()
        })
    }

}