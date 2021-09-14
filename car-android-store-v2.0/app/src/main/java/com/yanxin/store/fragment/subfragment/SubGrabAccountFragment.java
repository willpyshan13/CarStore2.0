package com.yanxin.store.fragment.subfragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.GrabOrderDetailActivity;
import com.yanxin.store.adapter.rvadapter.SupportEnterAdapter;
import com.yanxin.store.adapter.rvadapter.SupportMineAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.SubAccountContract;
import com.yanxin.store.presenter.SubAccountPresenter;
import com.yanxin.store.req.GrabOrderReq;

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.fragment_sub_grab)
public class SubGrabAccountFragment extends BaseMvpFragment<SubAccountPresenter> implements SubAccountContract.SubAccountView {
    private ImageView mNotData;
    private RecyclerView mGrabRv;
    private SupportMineAdapter mPushAdapter;
    private SupportEnterAdapter mGrabAdapter;
    private ArrayList<SupportOrderBean.DataBean> mSupportBeans;
    private ArrayList<SupportOrderBean.DataBean> mCompletedBeans;
    private int fragmentType;
    private int orderType;
    private GrabOrderReq mSupReq;
    private final int pageSize = 50;
    private int pageNum = 1;
    private SwipeRefreshLayout mSwRefresh;


    @Override
    protected void initData() {
        super.initData();
        //0的时候是我抢到的   1是我发布的
        switch (fragmentType) {
            case 0:
                mGrabRv.setAdapter(mGrabAdapter);
                mSupReq.setQueryType(1);
                break;
            case 1:
                mGrabRv.setAdapter(mPushAdapter);
                mSupReq.setQueryType(2);
                break;
        }
        mPushAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
            intent.putExtra("grab_uuid", mSupportBeans.get(position).getUuid());
            startActivity(intent);
        });
        mGrabAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
            intent.putExtra("grab_uuid", mCompletedBeans.get(position).getUuid());
            startActivity(intent);
        });
        mSwRefresh.setOnRefreshListener(() -> {
            pageNum = 1;
            switch (fragmentType) {
                case 0:
                    mCompletedBeans.clear();
                    break;
                case 1:
                    mSupportBeans.clear();
                    break;
            }
            queryOrderList();
        });
        queryOrderList();
    }

    private void queryOrderList() {
        mSupReq.setPageNum(pageNum);
        mSupReq.setPageSize(pageSize);
        if (orderType != 0) {
            mSupReq.setStatus(orderType);
        }
        switch (fragmentType) {
            case 0:
                mPresenter.getCompletedOrder(mSupReq);
                break;
            case 1:
                mPresenter.getSupportOrder(mSupReq);
                break;
        }
    }

    @Override
    protected void initView() {
        mSupportBeans = new ArrayList<>();
        mCompletedBeans = new ArrayList<>();
        mSupReq = new GrabOrderReq();
        fragmentType = getArguments().getInt("fragment_type");
        orderType = getArguments().getInt("order_type");
        mNotData = findViewById(R.id.not_data);
        mGrabRv = findViewById(R.id.grab_rv);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mPushAdapter = new SupportMineAdapter(R.layout.item_support_order, mSupportBeans);
        mGrabAdapter = new SupportEnterAdapter(R.layout.item_enter_order, mCompletedBeans);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public BasePresenter initPresenter() {
        return SubAccountPresenter.newInstance();
    }

    @Override
    public void supportOrderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans) {
        mSupportBeans.addAll(supportBeans);
        if (mSupportBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGrabRv.setVisibility(View.VISIBLE);
        }
        mPushAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void completedOrderSuccess(ArrayList<SupportOrderBean.DataBean> completedBeans) {
        mCompletedBeans.addAll(completedBeans);
        if (mCompletedBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGrabRv.setVisibility(View.VISIBLE);
        }
        mGrabAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }
}
