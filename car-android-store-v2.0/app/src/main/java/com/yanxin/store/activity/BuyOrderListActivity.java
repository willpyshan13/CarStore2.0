package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.DTCBuyOrderListAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.DTCOrderBean;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_dtc_buy_order)
public class BuyOrderListActivity extends BaseActivity {
    private ImageView mNotData;
    private RecyclerView mDtcRv;
    private ArrayList<DTCOrderBean.DataBean> dtcBean;
    private DTCBuyOrderListAdapter dtcBuyOrderListAdapter;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout mSwRefresh;

    @Override
    protected void initData() {
        queryOrderList();
        mSwRefresh.setOnRefreshListener(() -> {
            dtcBean.clear();
            queryOrderList();
        });
    }

    @SuppressLint("CheckResult")
    private void queryOrderList() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBuyDTCList(MyApplication.getUserToken())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(dtcOrderBean -> {
                    if (dtcOrderBean.isSuccess()) {
                        initDTCOrderList(dtcOrderBean.getData());
                    } else {
                        initError();
                    }
                }, throwable -> initError());
    }

    private void initDTCOrderList(ArrayList<DTCOrderBean.DataBean> data) {
        dtcBean.addAll(data);
        if (dtcBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mDtcRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mDtcRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
        dtcBuyOrderListAdapter.notifyDataSetChanged();
    }

    private void initError() {
        mNotData.setVisibility(View.VISIBLE);
        mDtcRv.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        mNotData = findViewById(R.id.not_data);
        mDtcRv = findViewById(R.id.dtc_rv);
        mSwRefresh = findViewById(R.id.sw_refresh);
        dtcBean = new ArrayList<>();
        dtcBuyOrderListAdapter = new DTCBuyOrderListAdapter(R.layout.item_dtc_buy_order, dtcBean);
        mDtcRv.setAdapter(dtcBuyOrderListAdapter);
    }
}
