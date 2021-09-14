package com.yanxin.store.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.sharesdk.onekeyshare.OnekeyShare
import cn.sharesdk.wechat.friends.Wechat
import com.will.habit.base.BaseFragment
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.databinding.FragmentMineKtBinding
import com.yanxin.store.mvvm.viewmodel.FragmentMineViewModel

/**
 *
 * Date: 2020-12-16
 * Copyright: Copyright (c) 2018-2020
 * Company:
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
class MineFragmentKt : BaseFragment<FragmentMineKtBinding, FragmentMineViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_mine_kt
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.shareEvent.observe(this){
            showShare(Wechat.NAME)
        }
    }

    private fun showShare(platform: String) {
        val oks = OnekeyShare()
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
//        if (platform != null) {
//            oks.setPlatform(platform)
//        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题")
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn")
        // text是分享文本，所有平台都需要这个字段
        oks.text = "我是分享文本"
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
//        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn")
        //启动分享
        oks.show(requireContext())
    }
}