package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.SupportOrderBean;

import java.util.List;

public class SupportMineAdapter extends BaseQuickAdapter<SupportOrderBean.DataBean, BaseViewHolder> {

    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");


    public SupportMineAdapter(int layoutResId, @Nullable List<SupportOrderBean.DataBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, SupportOrderBean.DataBean item) {
        helper.setText(R.id.item_title, item.getBrandName() + "/" + item.getCarModelName());
        helper.setText(R.id.item_desc, item.getFaultDesc());
        helper.setText(R.id.item_price, "¥" + item.getBasicDoorAmount());
        helper.addOnClickListener(R.id.parent_layout);
        TextView mStatusView = helper.getView(R.id.item_status);
        // 订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,
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
    }
}
