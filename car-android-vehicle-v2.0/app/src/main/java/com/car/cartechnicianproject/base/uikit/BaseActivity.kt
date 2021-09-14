package com.car.cartechnicianproject.base.uikit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!statusBarVisible())
            ImmersionBar.with(this).init();
    }

    protected fun statusBarVisible(): Boolean {
        return false
    }
}