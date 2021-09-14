package com.yanxin.store.adapter.rvadapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.FiltrateBean;

import java.util.ArrayList;

public class BrandAdapter extends BaseQuickAdapter<FiltrateBean, BaseViewHolder> {
    public BrandAdapter(int layoutResId, @Nullable ArrayList<FiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FiltrateBean item) {
        helper.setText(R.id.item_content, item.getName());
    }
}
