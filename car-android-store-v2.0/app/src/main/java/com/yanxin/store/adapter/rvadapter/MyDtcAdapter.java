package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.MyDtcBean;

import java.util.ArrayList;

public class MyDtcAdapter extends BaseQuickAdapter<MyDtcBean.DataDTO, BaseViewHolder> {
    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");
    private int errorColor = Color.parseColor("#FF0000");

    public MyDtcAdapter(int layoutResId, @Nullable ArrayList<MyDtcBean.DataDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyDtcBean.DataDTO item) {
        helper.setText(R.id.dtc_brand, item.getDtcBrandUuidName());
        helper.setText(R.id.dtc_code, item.getDtcCode());
        helper.setText(R.id.dtc_code2, item.getDtcCode2());
        helper.setText(R.id.dtc_code3, item.getDtcCode3());
        switch (item.getDtcType()) {
            case 0:
                helper.setText(R.id.dtc_count, item.getReadCount() + "");
                break;
            case 1:
                helper.setText(R.id.dtc_count, "续费");
                break;
            case 2:
                TextView mDtcStatus = helper.getView(R.id.dtc_count);
                mDtcStatus.setText(item.getDtcCheckSts() == 0 ? "待审核" : item.getDtcCheckSts() == 1 ? "审核通过" : "审核驳回");
                mDtcStatus.setTextColor(item.getDtcCheckSts() == 0 ? waitColor : item.getDtcCheckSts() == 1 ? enterColor : errorColor);
                break;
        }
        helper.addOnClickListener(R.id.dtc_layout).addOnClickListener(R.id.dtc_count);
    }
}
