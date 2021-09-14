package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.bean.SGrabContentBean;

import java.util.List;

/**
 * 服务页紧急共享工位已抢单的adapter
 */
public class SGrabCompleteAdapter extends BaseQuickAdapter<SGrabContentBean.DataBean, BaseViewHolder> {
    public SGrabCompleteAdapter(int layoutResId, @Nullable List<SGrabContentBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SGrabContentBean.DataBean item) {

    }
}
