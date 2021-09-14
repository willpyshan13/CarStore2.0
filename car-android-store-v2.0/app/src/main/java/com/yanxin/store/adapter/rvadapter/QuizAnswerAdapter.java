package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.TechnicianAnswerBean;

import java.util.ArrayList;
import java.util.List;

public class QuizAnswerAdapter extends BaseQuickAdapter<TechnicianAnswerBean.DataBean, BaseViewHolder> {

    public QuizAnswerAdapter(int layoutResId, @Nullable List<TechnicianAnswerBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TechnicianAnswerBean.DataBean item) {
        ImageView mSrc = helper.getView(R.id.item_src);
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(item.getPhotoImgUrl()).into(mSrc);
        helper.setText(R.id.item_title, item.getName());
        helper.setText(R.id.item_score_num, item.getScore());
        SpanUtils.with(helper.getView(R.id.item_type))
                .append(item.getTechnologyType())
                .append("    ").append(item.getQaCount() + "")
                .setForegroundColor(Color.parseColor("#0091FF"))
                .append("次咨询").create();
        StringBuffer sb = new StringBuffer();
        ArrayList<TechnicianAnswerBean.DataBean.BrandResListBean> brandResArrayList = item.getBrandResList();
        if (brandResArrayList != null) {
            for (TechnicianAnswerBean.DataBean.BrandResListBean dataBean : brandResArrayList) {
                sb.append(dataBean.getBrandName() + "/");
            }
            String brandName = sb.deleteCharAt(sb.length() - 1).toString();
            SpanUtils.with(helper.getView(R.id.item_gt))
                    .append("擅长：")
                    .append(brandName)
                    .create();
        }
        helper.setText(R.id.item_address, item.getAddressCityName());
        helper.setText(R.id.item_price, "¥" + item.getAnswerAmt());
        helper.addOnClickListener(R.id.item_to);
    }
}
