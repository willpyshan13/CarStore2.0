package com.yanxin.store.activity;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.GroupCreateBean;

@XmlLayoutResId(contentId = R.layout.activity_mall_group_create)
public class MallGroupDetailCreateActivity extends BaseActivity {
    private GroupCreateBean.DataBean mCreateBean;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mCreateBean = (GroupCreateBean.DataBean) getIntent().getSerializableExtra("bean");

    }
}
