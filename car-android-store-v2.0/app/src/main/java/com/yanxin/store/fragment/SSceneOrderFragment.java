package com.yanxin.store.fragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.SceneOrderAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.SceneOrderBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.SceneOrderContract;
import com.yanxin.store.presenter.SceneOrderPresenter;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.fragment_serving_scene_order)
public class SSceneOrderFragment extends BaseMvpFragment<SceneOrderPresenter> implements SceneOrderContract.SceneOrderView {
    private int orderType;
    private GrabOrderReq mSceneOrderReq;
    private int pagerSize = 20;
    private int pagerNum = 1;
    private ArrayList<SupportOrderBean.DataBean> mDataBeans;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mSceneRv;
    private SceneOrderAdapter mSceneOrderAdapter;

    @Override
    protected void initData() {
        super.initData();
        querySceneOrder();
        mSwRefresh.setOnRefreshListener(() -> {
            mDataBeans.clear();
            pagerNum = 1;
            querySceneOrder();
        });
    }

    @Override
    protected void initView() {
        mDataBeans = new ArrayList<>();
        orderType = getArguments().getInt("order_type");
        mSwRefresh = findViewById(R.id.sw_refresh);
        mSceneRv = findViewById(R.id.scene_rv);
        mSceneOrderAdapter = new SceneOrderAdapter(R.layout.item_scene_order, mDataBeans);
        mSceneRv.setAdapter(mSceneOrderAdapter);
        mSceneOrderReq = new GrabOrderReq();
    }

    private void querySceneOrder() {
        mSceneOrderReq.setPageNum(pagerNum);
        mSceneOrderReq.setPageSize(pagerSize);
        mSceneOrderReq.setQueryType(orderType);
        mPresenter.querySceneOrder(mSceneOrderReq);
    }

    @Override
    public BasePresenter initPresenter() {
        return SceneOrderPresenter.newInstance();
    }

    @Override
    public void querySuccess(ArrayList<SupportOrderBean.DataBean> dataBeans) {
        mDataBeans.addAll(dataBeans);
        mSceneOrderAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void queryFailed(String msg) {

    }
}
