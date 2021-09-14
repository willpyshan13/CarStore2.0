package com.yanxin.store.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.yanxin.store.R;
import com.yanxin.store.activity.MainActivity;
import com.yanxin.store.activity.subactivity.SubAccountActivity;
import com.yanxin.store.utils.XmlLayoutResIdUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(XmlLayoutResIdUtils.checkRes(this));
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init(); //默认，需要状态栏渐变子类自己再设置
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    public void onBackFinish(View view) {
        finish();
    }

    protected void startTaskHomeActivity(int index) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (index != 0) {
            intent.putExtra("index", index);
        }
        startActivity(intent);
    }

    protected void startTaskSubActivity() {
        Intent intent = new Intent(this, SubAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
