package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.DTCBean;

import java.util.ArrayList;

public class DTCAdapter extends BaseQuickAdapter<DTCBean.DataBean, BaseViewHolder> {
    public DTCAdapter(int layoutResId, @Nullable ArrayList<DTCBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DTCBean.DataBean item) {
        LinearLayout mDTCLayout = helper.getView(R.id.dtc_layout);
        if (item.isWhite()) {
            mDTCLayout.setBackgroundColor(Color.WHITE);
        } else {
            mDTCLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
        helper.setText(R.id.dtc_brand, item.getConfigName());
        helper.setText(R.id.dtc_code, item.getDtcCode());
        helper.setText(R.id.dtc_code2, item.getDtcCode2());
        helper.setText(R.id.dtc_code3, item.getDtcCode3());
        helper.setText(R.id.dtc_buy, item.getNeedBuy() == 0 ? "查看" : "购买");
        helper.addOnClickListener(R.id.dtc_buy);
    }
}
