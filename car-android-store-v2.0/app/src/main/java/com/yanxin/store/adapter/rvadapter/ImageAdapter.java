package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(item).into((ImageView) helper.getView(R.id.item_src));
        helper.addOnClickListener(R.id.item_src);
    }
}
