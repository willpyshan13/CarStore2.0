package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.BrandBean;

import java.util.ArrayList;


public class RegBrandAdapter extends BaseQuickAdapter<BrandBean.DataBean, BaseViewHolder> {
    public RegBrandAdapter(int layoutResId, @Nullable ArrayList<BrandBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandBean.DataBean item) {
        ImageView selectView = helper.getView(R.id.brand_select);
        if (item.isCheck()) {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.checkbox_uncheck))
                    .load(R.drawable.checkbox_check).into(selectView);
        } else {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.checkbox_uncheck))
                    .load(R.drawable.checkbox_uncheck).into(selectView);
        }
        helper.setText(R.id.brand_content, item.getConfigName());
        helper.addOnClickListener(R.id.brand_select);
    }
}
