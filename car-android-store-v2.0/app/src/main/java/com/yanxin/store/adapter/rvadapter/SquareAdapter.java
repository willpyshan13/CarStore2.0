package com.yanxin.store.adapter.rvadapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanxin.store.R;
import com.yanxin.store.activity.RushDetailActivity;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.req.PayAuditReq;

import java.util.ArrayList;

public class SquareAdapter extends BaseQuickAdapter<AuditBean.DataBean, BaseViewHolder> {
    private PayCallBack payCallBack;

    public SquareAdapter(int layoutResId, @Nullable ArrayList<AuditBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuditBean.DataBean item) {
        RecyclerView mDescImgView = helper.getView(R.id.sq_rv);
        helper.setText(R.id.sq_title, item.getTitle());
        helper.setText(R.id.sq_brand, "品牌: " + item.getVehicleBrandName());
        helper.setText(R.id.sq_model, "车型: " + item.getVehicleModelName());
        SrcAdapter srcAdapter = new SrcAdapter(R.layout.item_content_desc_img, item.getImgs());
        mDescImgView.setAdapter(srcAdapter);
        srcAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (item.getYesOrNo() == 1) {
                mContext.startActivity(new Intent(mContext, RushDetailActivity.class)
                        .putExtra("order_uuid", item.getUuid())
                        .putExtra("is_order", true)
                        .putExtra("is_pt", true));
            } else {
                PayAuditReq payAuditReq = new PayAuditReq();
                payAuditReq.setOrderUuid(item.getUuid());
                payCallBack.payCallBack(payAuditReq);
            }
        });
        helper.setText(R.id.sq_content, item.getConsultDesc());
        if (item.getYesOrNo() == 1) {
            helper.setText(R.id.sq_price, "已旁听");
        } else {
            helper.setText(R.id.sq_price, "¥2.00");
        }
        helper.addOnClickListener(R.id.parent_layout).addOnClickListener(R.id.sq_rv);
    }

    public void setPayCallBack(PayCallBack payCallBack) {
        this.payCallBack = payCallBack;
    }

    public interface PayCallBack {
        void payCallBack(PayAuditReq payAuditReq);
    }
}
