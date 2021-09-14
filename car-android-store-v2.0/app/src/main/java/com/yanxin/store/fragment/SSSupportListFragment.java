package com.yanxin.store.fragment;

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
import com.yanxin.store.adapter.rvadapter.SupportWaitAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.SupportContract;
import com.yanxin.store.event.SupportEvent;
import com.yanxin.store.presenter.SupportPresenter;
import com.yanxin.store.req.GrabOrderReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_support_list)
public class SSSupportListFragment extends BaseMvpFragment<SupportPresenter> implements SupportContract.SupportView {
    private int supportType;  //0:待抢单列表   1:支持下单/我的下单(我发布的)  2:已抢单列表(我抢别人发布的)
    private int type;  //0:全部    1:进行中   2:已完成
    private SupportWaitAdapter mWaitAdapter;
    private SupportMineAdapter mSupportAdapter;
    private SupportEnterAdapter mCompletedAdapter;
    private ArrayList<SupportOrderBean.DataBean> mWaitBeans;
    private ArrayList<SupportOrderBean.DataBean> mSupportBeans;
    private ArrayList<SupportOrderBean.DataBean> mCompletedBeans;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mSupportRv;
    private ImageView mNotData;
    private final int pageSize = 50;
    private int pageNum = 1;
    private GrabOrderReq mSupReq;

    @Override
    protected void initData() {
        super.initData();
        mSupReq.setQueryType(supportType);
        switch (supportType) {
            case 0:
                mWaitAdapter = new SupportWaitAdapter(R.layout.item_wait_order, mWaitBeans);
                mSupportRv.setAdapter(mWaitAdapter);
                mWaitAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
                    intent.putExtra("grab_uuid", mWaitBeans.get(position).getUuid());
                    startActivity(intent);
                });
                break;
            case 1:
                mCompletedAdapter = new SupportEnterAdapter(R.layout.item_enter_order, mCompletedBeans);
                mSupportRv.setAdapter(mCompletedAdapter);
                mCompletedAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
                    intent.putExtra("grab_uuid", mCompletedBeans.get(position).getUuid());
                    startActivity(intent);
                });
                break;
            case 2:
                mSupportAdapter = new SupportMineAdapter(R.layout.item_support_order, mSupportBeans);
                mSupportRv.setAdapter(mSupportAdapter);
                mSupportAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
                    intent.putExtra("grab_uuid", mSupportBeans.get(position).getUuid());
                    startActivity(intent);
                });
                break;
        }
        mSwRefresh.setOnRefreshListener(() -> {
            pageNum = 1;
            switch (supportType) {
                case 0:
                    mWaitBeans.clear();
                    break;
                case 1:
                    mCompletedBeans.clear();
                    break;
                case 2:
                    mSupportBeans.clear();
                    break;
            }
            queryOrderList();
        });
        queryOrderList();
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        supportType = getArguments().getInt("support_type");
        type = getArguments().getInt("type");
        mSupReq = new GrabOrderReq();
        mSupportBeans = new ArrayList<>();
        mWaitBeans = new ArrayList<>();
        mCompletedBeans = new ArrayList<>();
        mSwRefresh = findViewById(R.id.sw_refresh);
        mSupportRv = findViewById(R.id.support_rv);
        mNotData = findViewById(R.id.not_data);
    }

    @Override
    public void supportOrderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans) {
        mSupportBeans.addAll(supportBeans);
        if (mSupportBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mSupportRv.setVisibility(View.VISIBLE);
        }
        mSupportAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void waitOrderSuccess(ArrayList<SupportOrderBean.DataBean> waitBeans) {
        mWaitBeans.addAll(waitBeans);
        if (mWaitBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mSupportRv.setVisibility(View.VISIBLE);
        }
        mWaitAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void completedOrderSuccess(ArrayList<SupportOrderBean.DataBean> completedBeans) {
        mCompletedBeans.addAll(completedBeans);
        if (mCompletedBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mSupportRv.setVisibility(View.VISIBLE);
        }
        mCompletedAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        switch (supportType) {
            case 0:
                mWaitBeans.clear();
                if (mWaitBeans.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mSupportRv.setVisibility(View.VISIBLE);
                }
                mWaitAdapter.notifyDataSetChanged();
                break;
            case 1:
                mCompletedBeans.clear();
                if (mCompletedBeans.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mSupportRv.setVisibility(View.VISIBLE);
                }
                mCompletedAdapter.notifyDataSetChanged();
                break;
            case 2:
                mSupportBeans.clear();
                if (mSupportBeans.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mSupportRv.setVisibility(View.VISIBLE);
                }
                mSupportAdapter.notifyDataSetChanged();
                break;
        }
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public BasePresenter initPresenter() {
        return SupportPresenter.newInstance();
    }

    @Subscribe
    public void onEventRefresh(SupportEvent supportEvent) {
        pageNum = 1;
        switch (supportType) {
            case 0:
                mWaitBeans.clear();
                break;
            case 1:
                mCompletedBeans.clear();
                break;
            case 2:
                mSupportBeans.clear();
                break;
        }
        queryOrderList();
    }

    private void queryOrderList() {
        mSupReq.setPageNum(pageNum);
        mSupReq.setPageSize(pageSize);
        switch (supportType) {
            case 0:
                mSupReq.setStatus(null);
                mPresenter.getWaitOrder(mSupReq);
                break;
            case 1:
                mSupReq.setStatus(type);
                mPresenter.getCompletedOrder(mSupReq);
                break;
            case 2:
                mSupReq.setStatus(type);
                mPresenter.getSupportOrder(mSupReq);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
