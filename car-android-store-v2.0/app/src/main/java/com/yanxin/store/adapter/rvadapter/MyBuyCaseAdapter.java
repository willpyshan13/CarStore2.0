package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyOrderBean;

import java.util.ArrayList;
import java.util.List;

public class MyBuyCaseAdapter extends BaseQuickAdapter<MyOrderBean.DataBean, BaseViewHolder> {
    public MyBuyCaseAdapter(int layoutResId, @Nullable List<MyOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderBean.DataBean item) {
        ImageView src = helper.getView(R.id.item_src);
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_user_name, "客户昵称：" + item.getTechnicianName());
        helper.setText(R.id.item_user_type, "实付：" + "¥" + item.getOrderAmount());
//        }
        helper.setText(R.id.item_scroe, "购买时间："+item.getCreatedTime());
        //rmb
        helper.addOnClickListener(R.id.parent_layout);
    }
}
