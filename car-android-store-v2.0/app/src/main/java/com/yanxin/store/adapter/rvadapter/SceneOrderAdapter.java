package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.SceneOrderBean;
import com.yanxin.store.bean.SupportOrderBean;

import java.util.List;

public class SceneOrderAdapter extends BaseQuickAdapter<SupportOrderBean.DataBean, BaseViewHolder> {
    private int exitColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");

    public SceneOrderAdapter(int layoutResId, @Nullable List<SupportOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupportOrderBean.DataBean item) {
        helper.setText(R.id.item_price, "¥" + item.getTotalAmount());
        helper.setText(R.id.item_title, item.getBrandName() + "/" + item.getCarModelName());
        helper.setText(R.id.item_desc, item.getFaultDesc());
        int orderSts = item.getOrderSts();
        TextView mStatusView = helper.getView(R.id.item_status);
        // 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 6：已完成 ,
        switch (orderSts) {
            case 0:

                break;
            case 1:
                mStatusView.setTextColor(processColor);
                mStatusView.setText("已付款待服务");
                break;
            case 2:
                mStatusView.setTextColor(cancelColor);
                mStatusView.setText("未付款已取消");
                break;
            case 6:
                mStatusView.setTextColor(exitColor);
                mStatusView.setText("已完成");
                break;
        }
    }
}
