package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.CaseVehicleBean;

import java.util.List;

public class CaseVehicleAdapter extends BaseQuickAdapter<CaseVehicleBean.DataBean, BaseViewHolder> {
    public CaseVehicleAdapter(int layoutResId, @Nullable List<CaseVehicleBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseVehicleBean.DataBean item) {
        helper.setText(R.id.item_title, item.getTitle());
        ImageView mTypeView = helper.getView(R.id.item_type);
        TextView mPersonView = helper.getView(R.id.person_type);
        TextView mYear = helper.getView(R.id.case_type);
        if (item.getTechnicianType() == 3) {
            SpanUtils.with(mYear).append("类型：").append(item.getCybAuth().equals("102") ? "4S店" : "其它").create();
            mPersonView.setVisibility(View.GONE);
        } else {
            SpanUtils.with(mYear).append("工龄：").append("" + item.getWorkingYear()).setForegroundColor(Color.parseColor("#0091FF")).append("年").create();
            mPersonView.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                .load(item.getTechnicianType() == 3 ? R.drawable.case_type_store : R.drawable.case_type_person)
                .into(mTypeView);
        helper.setText(R.id.item_name, item.getTechnicianName());
        helper.setText(R.id.item_price, "¥" + item.getAmt());
        //rmb
        TextView mBuyNum = helper.getView(R.id.case_buy_num);
        TextView mScore = helper.getView(R.id.case_score);
        SpanUtils.with(mBuyNum).append("购买:").append("" + item.getPurchaseNumber()).setForegroundColor(Color.parseColor("#FDC920")).append("次").create();
        SpanUtils.with(mScore).append("评分:").append("" + item.getScore()).setForegroundColor(Color.parseColor("#FDC920")).append("分").create();
        helper.addOnClickListener(R.id.parent_layout);
    }
}
