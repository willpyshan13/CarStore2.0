package com.will.habit.binding.viewadapter.radiogroup

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.will.habit.binding.command.BindingCommand

/**
 * @author will
 */
object ViewAdapter {
    @JvmStatic
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun onCheckedChangedCommand(radioGroup: RadioGroup, bindingCommand: BindingCommand<Int?>) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            bindingCommand.execute(checkedId)
        }
    }
}