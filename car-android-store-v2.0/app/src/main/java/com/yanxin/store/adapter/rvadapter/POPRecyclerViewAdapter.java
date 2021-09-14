package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.DepositBankBean;

import java.util.ArrayList;

public class POPRecyclerViewAdapter extends BaseQuickAdapter<DepositBankBean.DataBean, BaseViewHolder> {
    public POPRecyclerViewAdapter(int layoutResId, @Nullable ArrayList<DepositBankBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepositBankBean.DataBean item) {
        helper.setText(R.id.item_content, item.getLableDesc());
        helper.addOnClickListener(R.id.item_content);
    }
}
