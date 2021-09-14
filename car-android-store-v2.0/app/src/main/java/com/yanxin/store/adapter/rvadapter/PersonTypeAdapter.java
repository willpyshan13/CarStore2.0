package com.yanxin.store.adapter.rvadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.PersonTypeBean;

import java.util.ArrayList;

public class PersonTypeAdapter extends BaseQuickAdapter<PersonTypeBean.DataBean, BaseViewHolder> {
    public PersonTypeAdapter(int layoutResId, @Nullable ArrayList<PersonTypeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonTypeBean.DataBean item) {
        helper.setText(R.id.item_content,item.getLableDesc());
        helper.addOnClickListener(R.id.item_content);
    }
}
