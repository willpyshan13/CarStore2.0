package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.GroupCreateBean;

import java.util.ArrayList;
import java.util.List;

public class MallGroupBuyAdapter extends BaseQuickAdapter<GroupCreateBean.DataBean, BaseViewHolder> {
    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");

    public MallGroupBuyAdapter(int layoutResId, @Nullable List<GroupCreateBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupCreateBean.DataBean item) {
        GroupCreateBean.DataBean.GoodsResBean goodsResBean = item.getGoodsRes().get(0);
        helper.setText(R.id.mall_tab_item_title, goodsResBean.getGoodsName());
        TextView statusView = helper.getView(R.id.mall_tab_item_state);
        if (item.getGroupSts() == 0) {
            statusView.setTextColor(waitColor);
            statusView.setText("未开始");
        } else if (item.getGroupSts() == 1) {
            statusView.setTextColor(processColor);
            statusView.setText("进行中");
        } else if (item.getGroupSts() == 2) {
            statusView.setTextColor(enterColor);
            statusView.setText("已结束");
        }
        if (goodsResBean.getReceiveMethod() == 0) {
            helper.setText(R.id.mall_tab_item_install_way, "配送方式：上门服务");
        } else {
            helper.setText(R.id.mall_tab_item_install_way, "配送方式：到店自提");
        }
        helper.setText(R.id.mall_tab_item_price, "￥" + goodsResBean.getAmt());
        helper.addOnClickListener(R.id.mall_tab_item_online).addOnClickListener(R.id.mall_tab_item_edit);
        ArrayList<GroupCreateBean.DataBean.GoodsResBean.ImgListBean> imgList = goodsResBean.getImgList();
        if (imgList != null && imgList.size() != 0) {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                    .load(imgList.get(0).getImgPath()).into((ImageView) helper.getView(R.id.mall_tab_item_icon));
        }
    }
}
