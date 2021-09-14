package com.yanxin.store.adapter.rvadapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.CreateMaterialsBean;

import java.util.List;

public class MallCreateMaterialsAdapter extends BaseQuickAdapter<CreateMaterialsBean, BaseViewHolder> {
    private ValueCall valueCall;

    public MallCreateMaterialsAdapter(int layoutResId, @Nullable List<CreateMaterialsBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, CreateMaterialsBean item) {
        helper.setIsRecyclable(false);
        ImageView comView = helper.getView(R.id.item_add);
        EditText nameView = helper.getView(R.id.item_name);
        EditText priceView = helper.getView(R.id.item_price);
        nameView.setText(item.getName());
        priceView.setText(item.getAmount());
        if (item.isDefault()) {
            comView.setImageResource(R.drawable.btn_add);
        } else {
            comView.setImageResource(R.drawable.btn_delete);
        }
        helper.addOnClickListener(R.id.item_add);
        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valueCall.nameValue(helper.getLayoutPosition(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        priceView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valueCall.amountValue(helper.getLayoutPosition(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setValueCall(ValueCall valueCall) {
        this.valueCall = valueCall;
    }

    public interface ValueCall {
        void nameValue(int position, String str);

        void amountValue(int position, String str);
    }
}
