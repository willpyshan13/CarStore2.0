package com.yanxin.store.mvvm.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.gyf.immersionbar.ktx.immersionBar
import com.will.habit.base.BaseFragment
import com.will.habit.widget.dialog.ChoiceDialog
import com.yanxin.store.BR
import com.yanxin.store.R
import com.yanxin.store.databinding.FragmentMessageBinding
import com.yanxin.store.mvvm.viewmodel.FragmentMessageViewModel

class MessageFragment : BaseFragment<FragmentMessageBinding, FragmentMessageViewModel>() {

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_message
    }

    override fun initParam() {
        super.initParam()
        immersionBar {
            statusBarColor(com.will.habit.R.color.translate)
            navigationBarColor(com.will.habit.R.color.translate)
            statusBarDarkFont(true)
        }
    }

    val filterList = arrayOf("已读","未读")
    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.showDialog.observe(this){
            ChoiceDialog(requireContext(),true)
                .setItems(filterList)
                .setOnItemClickListener { onClickView, position ->
                    viewModel.messageFilterText.set(filterList[position])
                }
                .setDialogGravity(Gravity.CENTER)
                .create()
                .show()
        }
    }

}