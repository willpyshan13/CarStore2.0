package com.yanxin.store.adapter.rvadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.RushAnswerBean;

import java.util.ArrayList;
import java.util.List;

public class FQRushAdapter extends BaseQuickAdapter<RushAnswerBean.DataBean, BaseViewHolder> {
    public FQRushAdapter(int layoutResId, @Nullable List<RushAnswerBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RushAnswerBean.DataBean item) {
        RecyclerView mSrc = helper.getView(R.id.sq_rv);
        helper.setText(R.id.sq_title, item.getTitle());
        helper.setText(R.id.sq_price, "¥" + item.getConsultAmt());
        helper.setText(R.id.sq_content, item.getConsultDesc());
        helper.setText(R.id.sq_brand, "品牌: " + item.getVehicleBrandName());
        helper.setText(R.id.sq_model, "车型: " + item.getVehicleModelName());
        mSrc.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SrcBaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_content_desc_img, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Glide.with(mContext)
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                        .load(item.getConsultImgList().get(position)).into((ImageView) holder.itemView.findViewById(R.id.item_src));
            }

            @Override
            public int getItemCount() {
                ArrayList<String> consultImgList = item.getConsultImgList();
                if (consultImgList != null) {
                    return Math.min(3, consultImgList.size());
                } else {
                    return 0;
                }
            }
        });
        helper.addOnClickListener(R.id.parent_layout);
    }

    class SrcBaseViewHolder extends RecyclerView.ViewHolder {
        public ImageView mSrc;

        public SrcBaseViewHolder(@NonNull View itemView) {
            super(itemView);
            mSrc = itemView.findViewById(R.id.item_src);
        }
    }
}
