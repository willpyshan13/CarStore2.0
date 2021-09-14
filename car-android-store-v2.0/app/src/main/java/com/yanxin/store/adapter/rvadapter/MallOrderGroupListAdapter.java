package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.bean.GroupCreateBean;

import java.util.List;

public class MallOrderGroupListAdapter extends BaseQuickAdapter<GroupCreateBean.DataBean, BaseViewHolder> {
    public MallOrderGroupListAdapter(int layoutResId, @Nullable List<GroupCreateBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupCreateBean.DataBean item) {

    }
}
