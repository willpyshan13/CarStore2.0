package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.SupportOrderBean;

import java.util.List;

public class SupportWaitAdapter extends BaseQuickAdapter<SupportOrderBean.DataBean, BaseViewHolder> {
    public SupportWaitAdapter(int layoutResId, @Nullable List<SupportOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupportOrderBean.DataBean item) {
        helper.setText(R.id.item_title, item.getBrandName() + "/" + item.getCarModelName());
        helper.setText(R.id.item_desc, item.getFaultDesc());
        helper.setText(R.id.item_distance, item.getDistance() + "km");
        helper.setText(R.id.item_price, "¥" + item.getBasicDoorAmount());
        helper.addOnClickListener(R.id.item_support).addOnClickListener(R.id.parent_layout);
    }
}
