package com.yanxin.store.mvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.SPUtils
import com.will.habit.base.AppManager
import com.will.habit.base.BaseActivity
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.R
import com.yanxin.store.activity.LoginActivity
import com.yanxin.store.databinding.ActivityAboutBinding
import com.yanxin.store.mvvm.viewmodel.AboutViewModel

class AboutActivity : BaseActivity<ActivityAboutBinding, AboutViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_about
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.go2Login.observe(this) {
            finishPage()
        }
    }

    private fun finishPage() {
        SPUtils.getInstance().remove("user_type")
        SPUtils.getInstance().remove("user_token")
        JPushInterface.deleteAlias(baseContext, 0)
        SPUtils.getInstance().remove("user_uuid")
        SPUtils.getInstance().remove("user_sub_account")
        val startIntent = Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        AppManager.appManager?.appExit()
        startActivity(startIntent)
        finish()
    }

}