package com.yanxin.store.adapter.rvadapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.PersonBean;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.ui.OptionalEditLayout;

import java.util.ArrayList;

public class PersonAdapter extends BaseQuickAdapter<PersonBean, BaseViewHolder> {
    public PersonAdapter(int layoutResId, @Nullable ArrayList<PersonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        OptionalEditLayout nameView = helper.getView(R.id.contact_name);
        nameView.setContentEditView(item.getUserName());
        nameView.getEditValueView().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String contentEditValue = nameView.getContentEditValue();
                item.setUserName(contentEditValue);
            }
        });
        OptionalEditLayout mobileView = helper.getView(R.id.contact_mobile);
        mobileView.setContentEditView(item.getMobile());
        mobileView.getEditValueView().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String contentEditValue = mobileView.getContentEditValue();
                item.setMobile(contentEditValue);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OptionalEditLayout typeView = helper.getView(R.id.contact_type);
        typeView.setContentTextView(item.getRenType());
        if(!item.isDefaultPerson()){
            helper.getView(R.id.contact_del).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.contact_del).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.contact_type).addOnClickListener(R.id.contact_del);
    }
}
