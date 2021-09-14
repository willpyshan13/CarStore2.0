package com.yanxin.store.adapter.rvadapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.SGrabContentBean;
import com.yanxin.store.ui.OptionalEditLayout;

import java.util.List;

public class SGrabWaitAdapter extends BaseQuickAdapter<SGrabContentBean.DataBean, BaseViewHolder> {
    public SGrabWaitAdapter(int layoutResId, @Nullable List<SGrabContentBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SGrabContentBean.DataBean item) {
        OptionalEditLayout brandView = helper.getView(R.id.item_brand);
        OptionalEditLayout modelView = helper.getView(R.id.item_model);
        OptionalEditLayout descView = helper.getView(R.id.item_desc);
        OptionalEditLayout amountView = helper.getView(R.id.item_amount);
        OptionalEditLayout timeView = helper.getView(R.id.item_time);
        brandView.setContentTextView(item.getVehicleBrandName());
        modelView.setContentTextView(item.getVehicleModelName());
        descView.setContentTextView(item.getRemark());
        amountView.setContentTextView("¥" + item.getDurationPrice());
        timeView.setContentTextView(item.getUseDate() + " " + item.getUseTime());
    }
}
