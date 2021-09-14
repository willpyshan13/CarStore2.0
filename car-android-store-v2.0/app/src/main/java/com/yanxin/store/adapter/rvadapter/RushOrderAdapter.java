package com.yanxin.store.adapter.rvadapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.RushOrderBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.List;

public class RushOrderAdapter extends BaseQuickAdapter<RushOrderBean, BaseViewHolder> {
    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");
    private int errorColor = Color.parseColor("#FF0000");

    public RushOrderAdapter(int layoutResId, @Nullable List<RushOrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RushOrderBean item) {
        RecyclerView mRv = helper.getView(R.id.item_rv);
        SrcAdapter srcAdapter = new SrcAdapter(R.layout.item_content_desc_img, item.getConsultImg());
        mRv.setAdapter(srcAdapter);
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_desc, item.getDesc());
        TextView mStatusView = helper.getView(R.id.item_status);
        if (item.getAmount() != 0) {
            helper.setText(R.id.item_amount, "¥" + BasicUtils.floatDecimalFormat(item.getAmount()));
        }
        switch (item.getOrderSts()) {
            case 0:
            case 3:
                mStatusView.setTextColor(waitColor);
                break;
            case 1:
                switch (item.getConsultCheckSts()) {
                    case 0:
                        mStatusView.setTextColor(waitColor);
                        break;
                    case 1:
                        if (item.getAnswerSts() == 0) {
                            mStatusView.setTextColor(waitColor);
                        } else {
                            mStatusView.setTextColor(enterColor);
                        }
                        break;
                    case 2:
                        mStatusView.setTextColor(errorColor);
                        break;
                }
                break;
            case 2:
                mStatusView.setTextColor(cancelColor);
                break;
            case 4:
                mStatusView.setTextColor(enterColor);
                mStatusView.setTextColor(enterColor);
                break;
            case 5:
                mStatusView.setTextColor(errorColor);
                break;
        }
        mStatusView.setText(item.getStatusTitle());
        helper.addOnClickListener(R.id.parent_layout).addOnClickListener(R.id.item_desc).addOnClickListener(R.id.item_title);
    }
}
