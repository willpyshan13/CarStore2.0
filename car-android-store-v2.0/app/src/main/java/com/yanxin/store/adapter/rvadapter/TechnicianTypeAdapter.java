package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.bean.TechnicianTypeBean;

import java.util.ArrayList;

public class TechnicianTypeAdapter extends BaseQuickAdapter<TechnicianTypeBean.DataBean, BaseViewHolder> {
    public TechnicianTypeAdapter(int layoutResId, @Nullable ArrayList<TechnicianTypeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TechnicianTypeBean.DataBean item) {
        helper.setText(R.id.item_content,item.getLableDesc());
        helper.addOnClickListener(R.id.item_content);
    }
}
