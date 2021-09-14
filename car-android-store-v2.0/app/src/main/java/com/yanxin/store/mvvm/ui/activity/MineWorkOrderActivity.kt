package com.yanxin.store.mvvm.ui.activity

import android.os.Bundle
import com.will.habit.base.BaseActivity
import com.yanxin.store.BR
import com.yanxin.store.R
import com.yanxin.store.databinding.ActivityMineOrderBinding
import com.yanxin.store.mvvm.viewmodel.MineOrderViewModel
import com.yanxin.store.mvvm.viewmodel.MineWorkOrderViewModel

class MineWorkOrderActivity : BaseActivity<ActivityMineOrderBinding, MineWorkOrderViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_work_order
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}