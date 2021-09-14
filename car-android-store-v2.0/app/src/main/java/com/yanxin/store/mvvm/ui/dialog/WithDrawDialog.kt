package com.yanxin.store.mvvm.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.ToastUtils
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.yanxin.store.R
import com.yanxin.store.mvvm.entity.ReqWithDrawEntity

class WithDrawDialog(context: Context,val money:String,val confirmClick:BindingCommand<ReqWithDrawEntity>) :Dialog(context) {
    var cancel: AppCompatTextView?=null

    var confirm: AppCompatTextView?=null

    var confirmMoney: AppCompatTextView?=null

    var cardInput:AppCompatEditText?=null

    var bankInput: AppCompatEditText?=null

    var subInput: AppCompatEditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.with_draw_dialog,null)
        setContentView(view)

        cancel = view.findViewById(R.id.cancel)
        confirm = view.findViewById(R.id.confirm)

        confirmMoney = view.findViewById(R.id.money)
        confirmMoney?.text = money

        cardInput = view.findViewById(R.id.input_cardNum)

        bankInput = view.findViewById(R.id.input)

        subInput = view.findViewById(R.id.input_cardNum_sub)

        cancel?.setOnClickListener {
            dismiss()
        }

        confirm?.setOnClickListener {
            val bankText = bankInput?.text.toString()
            val subBankText = subInput?.text.toString()
            val cardText = cardInput?.text.toString()
            if (bankText.isNotEmpty()&&subBankText.isNotEmpty()&&cardText.isNotEmpty()) {
                confirmClick.execute(ReqWithDrawEntity(bankText, cardText, subBankText, money))
                dismiss()
            }else{

            }
        }
    }
}
