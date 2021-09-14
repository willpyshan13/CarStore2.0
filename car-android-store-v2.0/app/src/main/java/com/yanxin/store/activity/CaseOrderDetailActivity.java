package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.CaseDetailBean;
import com.yanxin.store.bean.CaseOrderDetailBean;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


@XmlLayoutResId(contentId = R.layout.activity_order_detail)
public class CaseOrderDetailActivity extends BaseActivity {
    private String orderUuid;
    private TextView mDetailTitle;
    private TextView mDetailName;
    private TextView mDetailYear;
    private TextView mDetailAmount;
    private OptionalEditLayout mDetailBuyTime;
    private OptionalEditLayout mDetailOrderNum;
    private OptionalEditLayout mDetailBuyPrice;
    private OptionalEditLayout mDetailPayType;
    private OptionalEditLayout mDetailUserName;
    private ImageView mAskMore;


    @Override
    protected void initData() {
        queryCaseOrderDetail(orderUuid);
    }

    @SuppressLint("CheckResult")
    private void queryCaseOrderDetail(String orderUuid) {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCaseOrderDetail(MyApplication.getUserToken(), orderUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(caseDetailBean -> {
                            if (caseDetailBean.isSuccess()) {
                                initCaseDetail(caseDetailBean.getData());
                            }
                        }
                        , throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    @Override
    protected void initView() {
        orderUuid = getIntent().getStringExtra("case_uuid");
        mDetailTitle = findViewById(R.id.detail_title);
        mDetailName = findViewById(R.id.detail_name);
        mDetailYear = findViewById(R.id.detail_year);
        mDetailAmount = findViewById(R.id.detail_amount);
        mDetailBuyTime = findViewById(R.id.detail_buy_time);
        mDetailOrderNum = findViewById(R.id.detail_order_num);
        mDetailBuyPrice = findViewById(R.id.detail_buy_price);
        mDetailPayType = findViewById(R.id.detail_pay_type);
        mDetailUserName = findViewById(R.id.detail_user_name);
        mAskMore = findViewById(R.id.ask_more);
    }

    private void initCaseDetail(CaseOrderDetailBean.DataBean data) {
        ArrayList<CaseOrderDetailBean.DataBean.CaseInfoListResBean> caseInfoListRes = data.getCaseInfoListRes();
        CaseOrderDetailBean.DataBean.TechnicianInfoResBean technicianInfoRes = data.getTechnicianInfoRes();
        CaseOrderDetailBean.DataBean.CaseInfoListResBean caseInfoListResBean;
        if (caseInfoListRes != null && caseInfoListRes.size() > 0) {
            caseInfoListResBean = caseInfoListRes.get(0);
            mDetailTitle.setText(caseInfoListResBean.getCaseName());
            mDetailName.setText(technicianInfoRes.getTechnicianName());
//            mDetailTitle.setText(data.getTitle());
//            mDetailTitle.setText(data.getTitle());
        }
//        mDetailBuyTime.setContentTextView(data.c);
        CaseOrderDetailBean.DataBean.OrderDetailResBean orderDetailRes = data.getOrderDetailRes();
        mDetailBuyTime.setContentTextView(data.getCreateTime());
        mDetailOrderNum.setContentTextView(orderDetailRes.getOrderNum());
        mDetailBuyPrice.setContentTextView("¥" + orderDetailRes.getOrderAmount());
        mDetailPayType.setContentTextView(orderDetailRes.getPayType() == 0 ? "微信支付" : "支付宝支付");
        mDetailUserName.setContentTextView(technicianInfoRes.getTechnicianName());
        mAskMore.setOnClickListener(v -> {
            Intent caseDetailIntent = new Intent(getBaseContext(), CaseDetailActivity.class);
            caseDetailIntent.putExtra("case_uuid", caseInfoListRes.get(0).getCaseUuid());
            caseDetailIntent.putExtra("is_order", false);
            startActivity(caseDetailIntent);
        });
        mAskMore.setVisibility(View.GONE);
    }
}
