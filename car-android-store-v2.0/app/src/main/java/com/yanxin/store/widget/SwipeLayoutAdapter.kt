package com.yanxin.store.widget

import androidx.databinding.BindingAdapter
import com.will.habit.utils.KLog

@BindingAdapter("closeCurrentSwipe")
fun SwipeLayout.closeCurrentSwipe(closeSwipe: Boolean) {
    this.close()
}