package com.yanxin.store.adapter.rvadapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.entity.PicEntity;

import java.util.ArrayList;

public class PicAdapter extends BaseQuickAdapter<PicEntity, BaseViewHolder> {
    public PicAdapter(int layoutResId, @Nullable ArrayList<PicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicEntity item) {
        ImageView mPicView = helper.getView(R.id.business_pic);
        ImageView mClearView = helper.getView(R.id.business_pic_clear);
        if (item.isDefault()) {
            mClearView.setVisibility(View.INVISIBLE);
            Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic)).load(R.drawable.pic_upload_pic).into(mPicView);
        } else {
            mClearView.setVisibility(View.VISIBLE);
            if (item.isNetWork()) {
                Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic)).load(item.getPath()).into(mPicView);
            } else {
                Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic)).load(item.getPicFile()).into(mPicView);
            }
        }
        helper.addOnClickListener(R.id.pic_layout).addOnClickListener(R.id.business_pic_clear);
    }
}
