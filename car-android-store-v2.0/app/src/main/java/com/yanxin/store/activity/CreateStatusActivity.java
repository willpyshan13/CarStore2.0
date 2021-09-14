package com.yanxin.store.activity;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

@XmlLayoutResId(contentId = R.layout.activity_create_goods_status)
public class CreateStatusActivity extends BaseActivity {
    private ImageView mIvSuccessIcon;
    private TextView mSuccessTips;
    private TextView mSuccessBack;

    @Override
    protected void initData() {
        findViewById(R.id.success_back)
                .setOnClickListener(view -> {
                    startTaskHomeActivity(2);
                });
        mSuccessTips.postDelayed(new Runnable() {
            int time = 3;
            @Override
            public void run() {
                --time;
                if (time < 0) {
                    startTaskHomeActivity(2);
                } else {
                    SpanUtils.with(mSuccessTips)
                            .append(time + "")
                            .setForegroundColor(Color.parseColor("#E02020"))
                            .append("s后将自动返回到商品列表")
                            .create();
                    mSuccessTips.postDelayed(this,1000);
                }
            }
        }, 1000);
    }

    @Override
    protected void initView() {
        mIvSuccessIcon = findViewById(R.id.iv_success_icon);
        mSuccessTips = findViewById(R.id.success_tips);
        mSuccessBack =  findViewById(R.id.success_back);
        SpanUtils.with(mSuccessTips)
                .append("3")
                .setForegroundColor(Color.parseColor("#E02020"))
                .append("s后将自动返回到商品列表")
                .create();
    }
}
