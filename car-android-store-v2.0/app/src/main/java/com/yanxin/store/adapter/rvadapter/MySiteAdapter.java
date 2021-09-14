package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.SupportOrderBean;

import java.util.List;

public class MySiteAdapter extends BaseQuickAdapter<SupportOrderBean.DataBean, BaseViewHolder> {
    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");

    public MySiteAdapter(int layoutResId, @Nullable List<SupportOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupportOrderBean.DataBean item) {
        helper.setText(R.id.item_brand, "品牌: " + item.getBrandName());
        helper.setText(R.id.item_model, "车型: " + item.getCarModelName());
        helper.setText(R.id.item_desc, "故障描述: " + item.getFaultDesc());
        helper.setText(R.id.item_amount, "基础上门费¥: " + item.getBasicDoorAmount());
        helper.setText(R.id.item_time, "维修时间: " + item.getCreatedTime());
        TextView mStatusView = helper.getView(R.id.item_status);
        switch (item.getOrderSts()) {
            case 0:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("待抢单");
                break;
            case 1:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("已接单/待付款");
                break;
            case 2:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("已付款待上门");
                break;
            case 3:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("待提交建议维修方案");
                break;
            case 4:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("维修方案待付款");
                break;
            case 5:
                mStatusView.setTextColor(processColor);
                mStatusView.setText("服务中");
                break;
            case 6:
                mStatusView.setTextColor(waitColor);
                mStatusView.setText("完成待确认");
                break;
            case 7:
                mStatusView.setTextColor(enterColor);
                mStatusView.setText("已完成");
                break;
            case 11:
            case 12:
            case 13:
                mStatusView.setTextColor(cancelColor);
                mStatusView.setText("已取消");
                break;
        }
//        helper.setText(R.id.item_status, "" + item.getOrderSts());
        helper.addOnClickListener(R.id.parent_layout);
    }
}
