package com.yanxin.store.adapter.rvadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.GoodsBean;

import java.util.List;

public class MallGoodsAdapter extends BaseQuickAdapter<GoodsBean.DataBean, BaseViewHolder> {
    public MallGoodsAdapter(int layoutResId, @Nullable List<GoodsBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.DataBean item) {
        helper.setText(R.id.mall_tab_item_title, item.getGoodsName());
        TextView statusView = helper.getView(R.id.mall_tab_item_online);
        if (item.getCheckSts() == 0) {
            helper.setText(R.id.mall_tab_item_state, "待审核");
            statusView.setVisibility(View.GONE);
        } else if (item.getCheckSts() == 1) {
            if (item.getSellSts() == 0) {
                helper.setText(R.id.mall_tab_item_state, "已下架");
                helper.setText(R.id.mall_tab_item_online, "上架");
            } else {
                helper.setText(R.id.mall_tab_item_state, "已上架");
                helper.setText(R.id.mall_tab_item_online, "下架");
            }
        } else if (item.getCheckSts() == 2) {
            helper.setText(R.id.mall_tab_item_state, "审核驳回");
            statusView.setVisibility(View.GONE);
        }
        if (item.getReceiveMethod() == 0) {
            helper.setText(R.id.mall_tab_item_install_way, "配送方式：上门服务");
        } else {
            helper.setText(R.id.mall_tab_item_install_way, "配送方式：到店自提");
        }
        helper.setText(R.id.mall_tab_item_price, "￥" + item.getAmt());
        helper.addOnClickListener(R.id.mall_tab_item_online).addOnClickListener(R.id.mall_tab_item_edit).addOnClickListener(R.id.parent_layout);
    }
}
