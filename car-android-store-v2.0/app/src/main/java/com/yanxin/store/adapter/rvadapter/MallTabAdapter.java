package com.yanxin.store.adapter.rvadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.base.BasePageDataAdapter;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.MallTabBean;
import com.yanxin.store.ui.MallTabDialog;

import java.util.List;

import androidx.annotation.Nullable;

public class MallTabAdapter extends BasePageDataAdapter<GoodsBean.DataBean, BaseViewHolder> {

    private int mTabType = 1;

    public void setTabType(int tabType) {
        mTabType = tabType;
    }

    public MallTabAdapter(int layoutResId, @Nullable List<GoodsBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    public void setNewData(@Nullable List<GoodsBean.DataBean> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.DataBean item) {
        ImageView itemIcon = helper.getView(R.id.mall_tab_item_icon);
        TextView itemTitle = helper.getView(R.id.mall_tab_item_title);
        TextView itemState = helper.getView(R.id.mall_tab_item_state);
        TextView itemPrice = helper.getView(R.id.mall_tab_item_price);
        TextView itemWay = helper.getView(R.id.mall_tab_item_install_way);
        TextView itemCustomer = helper.getView(R.id.mall_tab_item_customer_number);
        View itemOnline = helper.getView(R.id.mall_tab_item_online);
        View itemEdit = helper.getView(R.id.mall_tab_item_edit);

        itemIcon.setBackgroundResource(R.drawable.mall_tab_tips);
        itemTitle.setText(item.getGoodsName());
        int receiveMethod = item.getReceiveMethod();
        if (receiveMethod == 0) {
            itemWay.setText("配送方式：上门服务");
        } else {
            itemWay.setText("配送方式：到店自提");
        }
        if (mTabType == 2) {
            itemState.setText("代付款");
        } else {
            itemState.setText("已下架");
        }
        itemPrice.setText("￥"+item.getAmt());
        itemCustomer.setVisibility(View.GONE);
        if (mTabType == 2) {
            SpanUtils.with(itemPrice)
                    .append("拼团价：")
                    .append("￥500")
                    .setForegroundColor(Color.parseColor("#ffe02020"))
                    .append("￥1000")
                    .setStrikethrough()
                    .create();
            SpanUtils.with(itemCustomer)
                    .append("成团人数：")
                    .append("2")
                    .setForegroundColor(Color.parseColor("#0091ff"))
                    .create();
        } else if (mTabType == 3) {
            SpanUtils.with(itemPrice)
                    .append("实付：")
                    .append("￥"+item.getAmt())
                    .create();
        }

        itemCustomer.setText("");
        itemOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext());
            }
        });
        itemOnline.setVisibility(mTabType == 1 ? View.VISIBLE : View.GONE);

        itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        itemEdit.setVisibility(mTabType == 1 ? View.VISIBLE : View.GONE);
    }

    private void showDialog(Context cx) {
        final MallTabDialog dialog = new MallTabDialog(cx);
        dialog.setMessage("确定要上架这个商品吗？上架后不可编辑！。")
                .setOnClickBottomListener(new MallTabDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();

                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }
                }).show();
    }


}
