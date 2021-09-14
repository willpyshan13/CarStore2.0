package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.bean.GroupCreateBean;

import java.util.List;

public class MallGroupAdapter extends BaseQuickAdapter<GroupCreateBean.DataBean, BaseViewHolder> {
    public MallGroupAdapter(int layoutResId, @Nullable List<GroupCreateBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupCreateBean.DataBean item) {
        ImageView img = helper.getView(R.id.group_img);
        ImageView check = helper.getView(R.id.group_check);
        GroupCreateBean.DataBean.GoodsResBean goodsResBean = item.getGoodsRes().get(0);
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(goodsResBean.getImgList().get(0).getImgPath())
                .into(img);
        helper.setText(R.id.group_name, goodsResBean.getGoodsName());
        helper.setText(R.id.group_type, goodsResBean.getReceiveMethod() == 0 ? "上门服务" : "到店安装");
        helper.setText(R.id.group_amount, "¥" + goodsResBean.getAmt());
        if (item.isCheck()) {
            check.setImageResource(R.drawable.checkbox_check);
        } else {
            check.setImageResource(R.drawable.checkbox_uncheck);
        }
        helper.addOnClickListener(R.id.group_check);
    }
}
