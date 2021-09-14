package com.yanxin.store.adapter.rvadapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.base.BasePageDataAdapter;
import com.yanxin.store.bean.MallTabBean;

import java.util.List;

import androidx.annotation.Nullable;

public class MallTabEmployeeAdapter extends BasePageDataAdapter<MallTabBean.DataBean, BaseViewHolder> {

    public MallTabEmployeeAdapter(int layoutResId, @Nullable List<MallTabBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    public void setNewData(@Nullable List<MallTabBean.DataBean> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallTabBean.DataBean item) {
        ImageView icon = helper.getView(R.id.item_mall_tab_employee_icon);
        TextView title = helper.getView(R.id.item_mall_tab_employee_name);
        TextView price = helper.getView(R.id.item_mall_tab_employee_price);
        TextView way = helper.getView(R.id.item_mall_tab_employee_install_way);
        TextView state = helper.getView(R.id.item_mall_tab_employee_pay_state);
        TextView time = helper.getView(R.id.item_mall_tab_employee_time);

        icon.setBackgroundResource(R.drawable.pic_card_upload_negative);
        title.setText("标题是什么呀标题是什么呀标题在多内标题在多内");
        price.setText("¥99.99");
        way.setText("工位名称：上海一号工位");
        state.setText("未支付已取消");
        time.setText("使用时间：2020.10.19 12:02");
    }


}
