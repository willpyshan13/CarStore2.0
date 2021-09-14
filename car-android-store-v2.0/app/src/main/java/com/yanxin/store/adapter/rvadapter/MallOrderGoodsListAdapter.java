package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.bean.GoodsBean;

import java.util.List;

public class MallOrderGoodsListAdapter extends BaseQuickAdapter<GoodsBean.DataBean, BaseViewHolder> {
    public MallOrderGoodsListAdapter(int layoutResId, @Nullable List<GoodsBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.DataBean item) {

    }
}
