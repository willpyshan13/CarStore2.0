package com.yanxin.store.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.SGrabCompleteAdapter;
import com.yanxin.store.adapter.rvadapter.SGrabWaitAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.bean.SGrabContentBean;
import com.yanxin.store.req.SGrabContentReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * 服务紧急共享工位的子页面(待抢单/已抢单)下的
 */
@XmlLayoutResId(contentId = R.layout.fragment_serving_store_grab_child_content)
public class SGrabChildContentFragment extends BaseFragment {
    private int serviceType, status; //serviceType: 待接单/已接单状态      status:全部,进行时,完成
    private SGrabWaitAdapter mWaitAdapter;
    private SGrabCompleteAdapter mCompleteAdapter;
    private SwipeRefreshLayout mSwRefresh;
    private ArrayList<SGrabContentBean.DataBean> mWaitBean;
    private ArrayList<SGrabContentBean.DataBean> mCompleteBean;
    private ImageView mNotData;
    private RecyclerView mContentRv;
    private SGrabContentReq mSGrabContentReq;


    @Override
    protected void initData() {
        if (serviceType == 0) {
            mSGrabContentReq = new SGrabContentReq();
            ArrayList<Integer> statusList = new ArrayList<>();
            statusList.add(0);
            mSGrabContentReq.setOrderSts(statusList);
//            mSGrabContentReq.setStoreSameCity(1);
        } else {
            mSGrabContentReq = new SGrabContentReq();
            ArrayList<Integer> statusList = new ArrayList<>();
            mSGrabContentReq.setStoreUserUuid(MyApplication.getUserUuid());
            if (status == 1) {
                statusList.add(1);
                statusList.add(2);
                statusList.add(4);
                mSGrabContentReq.setOrderSts(statusList);
            } else if (status == 2) {
                statusList.add(7);
                mSGrabContentReq.setOrderSts(statusList);
            }
        }
        queryGrabStationList();
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSGrabContentReq.setPageNum(1);
                if (serviceType == 0) {
                    mWaitBean.clear();
                } else {
                    mCompleteBean.clear();
                }
                queryGrabStationList();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void queryGrabStationList() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderVehicleStationList(MyApplication.getUserToken(), mSGrabContentReq)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(sGrabContentBean -> {
                    mSwRefresh.setRefreshing(false);
                    if (sGrabContentBean.isSuccess()) {
                        initSGrabValue(sGrabContentBean.getData());
                    } else {
                        ToastUtils.showShort(sGrabContentBean.getMsg());
                    }
                }, throwable -> {
                    ToastUtils.showShort(throwable.getMessage());
                    mSwRefresh.setRefreshing(false);
                });
    }

    private void initSGrabValue(ArrayList<SGrabContentBean.DataBean> sGrabContentBeanData) {
        if (serviceType == 0) {
            mWaitBean.addAll(sGrabContentBeanData);
            if (mWaitBean.size() == 0) {
                mNotData.setVisibility(View.VISIBLE);
            } else {
                mNotData.setVisibility(View.GONE);
                mContentRv.setVisibility(View.VISIBLE);
            }
            mWaitAdapter.notifyDataSetChanged();
        } else {
            mCompleteBean.addAll(sGrabContentBeanData);
            if (mCompleteBean.size() == 0) {
                mNotData.setVisibility(View.VISIBLE);
            } else {
                mNotData.setVisibility(View.GONE);
                mContentRv.setVisibility(View.VISIBLE);
            }
            mCompleteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        serviceType = arguments.getInt("service_type");
        status = arguments.getInt("status");
        mWaitBean = new ArrayList<>();
        mCompleteBean = new ArrayList<>();
        mNotData = findViewById(R.id.not_data);
        mContentRv = findViewById(R.id.content_rv);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mWaitAdapter = new SGrabWaitAdapter(R.layout.item_service_grab_wait, mWaitBean);
        mCompleteAdapter = new SGrabCompleteAdapter(R.layout.item_service_grab_complete, mCompleteBean);
        if (serviceType == 0) {
            mContentRv.setAdapter(mWaitAdapter);
        } else {
            mContentRv.setAdapter(mCompleteAdapter);
        }

    }
}
