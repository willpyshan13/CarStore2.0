package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.MineCaseBean;

import java.util.List;

public class MineCaseAdapter extends BaseQuickAdapter<MineCaseBean.DataBean, BaseViewHolder> {
    public MineCaseAdapter(int layoutResId, @Nullable List<MineCaseBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineCaseBean.DataBean item) {
        ImageView src = helper.getView(R.id.item_src);
        helper.setText(R.id.item_title, item.getTitle());
        if (item.getTechniciantype() == 3) {
            helper.setText(R.id.item_user_name, "店铺：" + item.getUserName());
            helper.setText(R.id.item_user_type, "类型：" + (item.getCybAuth().equals("102") ? "4S店" : "其它"));
        } else {
            helper.setText(R.id.item_user_name, "技师：" + item.getUserName());
            helper.setText(R.id.item_user_type, "级别：" + (item.getCybAuth().equals("1") ? "专家" : "普通"));
        }
        helper.setText(R.id.item_scroe, "评分：" + item.getScore() + "分");
        helper.setText(R.id.item_buy_num, "购买次数：" + item.getNum() + "次");
//        TextView mIncome = helper.getView(R.id.item_income);
//        SpanUtils.with(mIncome).append("总收益：").append("¥" + (item.getNum() * item.getAmt())).setForegroundColor(Color.parseColor("#E02020"));
        //rmb
        helper.addOnClickListener(R.id.parent_layout);
    }
}
