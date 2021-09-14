package com.will.habit.widget.banner

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Desc: Banner
 * <p>
 * Date: 2021/7/26
 * Copyright: Copyright (c) 2021-2021
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: pengyushan
 */
@BindingMethods(
    BindingMethod(type = BannerView::class, attribute = "itemBinding", method = "setAdapter"),
    BindingMethod(type = BannerView::class, attribute = "items", method = "setAdapter"),
    BindingMethod(type = BannerView::class, attribute = "loop", method = "setAdapter"),
    BindingMethod(type = BannerView::class, attribute = "loopTime", method = "setAdapter"),
    BindingMethod(type = BannerView::class, attribute = "onPageScrolledCommand", method = "onScrollChangeCommand"),
    BindingMethod(type = BannerView::class, attribute = "onPageSelectedCommand", method = "onScrollChangeCommand"),
    BindingMethod(type = BannerView::class, attribute = "onPageScrollStateChangedCommand", method = "onScrollChangeCommand"),
    BindingMethod(type = BannerView::class, attribute = "currentItem", method = "setCurrentItem"),
)
class BannerViewAdapter

/**
 * Desc: BannerView Binding
 * <p>
 * Author: linjiaqiang
 * Date: 2020/5/13
 *
 * @param loop 是否循环播放
 * @param loopTime banner切换等待时长
 */
@BindingAdapter(value = ["itemBinding", "items", "loop", "loopTime"], requireAll = false)
fun <T> BannerView.setAdapter(itemBinding: ItemBinding<in T>?,
                              items: List<T>?,
                              loop: Boolean?,
                              loopTime: Long?) {
    if (itemBinding != null) {
        this.isLoop = loop ?: true
        this.loopTime = loopTime ?: LOOP_TIME
        val oldAdapter = this.getViewPager().adapter as? BindingBannerAdapter<T>
        val adapter = oldAdapter ?: BindingBannerAdapter(this)
        adapter.itemBinding = itemBinding
        adapter.setItems(items)
        if (oldAdapter !== adapter) {
            this.getViewPager().adapter = adapter
        }
        this.initLoop()
    } else {
        this.getViewPager().adapter = null
    }
}