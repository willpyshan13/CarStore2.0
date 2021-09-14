package com.yanxin.store.adapter.rvadapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskDetailActivity;
import com.yanxin.store.activity.ImageActivity;
import com.yanxin.store.activity.RushDetailActivity;
import com.yanxin.store.activity.VideoActivity;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.MyRushConsultBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRushAdapter extends BaseQuickAdapter<MyRushBean.DataBean, BaseViewHolder> {
    public MyRushAdapter(int layoutResId, @Nullable List<MyRushBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyRushBean.DataBean item) {
        TextView mStatusView = helper.getView(R.id.sq_status);
        mStatusView.setVisibility(View.VISIBLE);
        RecyclerView rv = helper.getView(R.id.sq_rv);
        helper.setText(R.id.sq_brand, "品牌: " + item.getVehicleBrandName());
        helper.setText(R.id.sq_model, "车型: " + item.getVehicleModelName());
        ArrayList<String> imgUrl = item.getImgUrl();
        SrcAdapter srcAdapter = new SrcAdapter(R.layout.item_content_desc_img, imgUrl);
        rv.setAdapter(srcAdapter);
        srcAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            String mPath = imgUrl.get(position);
            String fileFormat = BasicUtils.getFileFormat(mPath);
            if (fileFormat.equals("mp4")) {
                mContext.startActivity(new Intent(mContext, VideoActivity.class).putExtra("video_url", mPath));
            } else {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("url", mPath);
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        ActivityUtils.getTopActivity(), view, "bigImg")
                        .toBundle());
            }
        });
        helper.setText(R.id.sq_title, item.getTitle());
        helper.setText(R.id.sq_price, "¥" + BasicUtils.floatDecimalFormat(item.getOrderAmount()));
        helper.setText(R.id.sq_content, item.getConsultDesc());
        helper.addOnClickListener(R.id.parent_layout);
    }
}
