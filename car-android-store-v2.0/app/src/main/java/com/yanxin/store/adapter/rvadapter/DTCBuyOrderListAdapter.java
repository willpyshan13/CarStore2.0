package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.DTCOrderBean;
import com.yanxin.store.ui.OptionalEditLayout;

import java.util.List;

public class DTCBuyOrderListAdapter extends BaseQuickAdapter<DTCOrderBean.DataBean, BaseViewHolder> {
    public DTCBuyOrderListAdapter(int layoutResId, @Nullable List<DTCOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DTCOrderBean.DataBean item) {
        OptionalEditLayout timeView = helper.getView(R.id.dtc_buy_time);
        OptionalEditLayout amountView = helper.getView(R.id.dtc_buy_amount);
        timeView.setContentTextView("购买时间:" + item.getCreatedTime());
        amountView.setContentTextView("¥:" + item.getOrderAmount());
        amountView.setDescTextView(item.getDtcCode());
    }
}
