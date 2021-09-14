package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.BrandBean;

import java.util.ArrayList;

public class RegChosenBrandAdapter extends BaseQuickAdapter<BrandBean.DataBean, BaseViewHolder> {
    public RegChosenBrandAdapter(int layoutResId, @Nullable ArrayList<BrandBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandBean.DataBean item) {
        helper.addOnClickListener(R.id.del_chosen);
        helper.setText(R.id.chosen_content,item.getConfigName());
    }
}
