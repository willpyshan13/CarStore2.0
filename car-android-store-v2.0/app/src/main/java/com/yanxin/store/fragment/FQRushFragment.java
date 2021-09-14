package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskDetailActivity;
import com.yanxin.store.adapter.rvadapter.FQRushAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.RushAnswerBean;
import com.yanxin.store.contract.FQRushContract;
import com.yanxin.store.event.FQEvent;
import com.yanxin.store.presenter.FQRushPresenter;
import com.yanxin.store.req.DefaultReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * 发现页-知识问答  可抢答页
 */
@XmlLayoutResId(contentId = R.layout.fragment_fq_rush)
public class FQRushFragment extends BaseMvpFragment<FQRushPresenter> implements FQRushContract.FQRushView {
    private FQRushAdapter mRushAdapter;
    private ArrayList<RushAnswerBean.DataBean> mRushAnswerBean;
    private SwipeRefreshLayout mSwRefresh;
    private ImageView mNotData;
    private RecyclerView mRushRv;
    private DefaultReq mDefaultReq;
    private final int pageSize = 50;
    private int pageNum = 1;

    @Override
    protected void initData() {
        super.initData();
        queryAnswerAll();
        mSwRefresh.setOnRefreshListener(() -> {
            mRushAnswerBean.clear();
            pageNum = 1;
            queryAnswerAll();
        });
        mRushAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent rushDetailIntent = new Intent(mContext, AskDetailActivity.class);
            rushDetailIntent.putExtra("rush_uuid", mRushAnswerBean.get(position).getOrderUuid());
            rushDetailIntent.putExtra("order_uuid", mRushAnswerBean.get(position).getUuid());
            rushDetailIntent.putExtra("is_grab", true);
            rushDetailIntent.putExtra("is_order", true);
            startActivity(rushDetailIntent);
        });
    }

    private void queryAnswerAll() {
        mDefaultReq.setPageNum(pageNum);
        mDefaultReq.setPageSize(pageSize);
        mPresenter.queryAnswerAll(mDefaultReq);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRushAnswerBean = new ArrayList<>();
        mDefaultReq = new DefaultReq();
        mRushAdapter = new FQRushAdapter(R.layout.item_square, mRushAnswerBean);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mNotData = findViewById(R.id.not_data);
        mRushRv = findViewById(R.id.rush_rv);
        mRushRv.setAdapter(mRushAdapter);
    }

    @Subscribe
    public void eventRefresh(FQEvent fqEvent){
        mRushAnswerBean.clear();
        pageNum = 1;
        queryAnswerAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return FQRushPresenter.newInstance();
    }

    @Override
    public void queryAnswerSuccess(ArrayList<RushAnswerBean.DataBean> dataBeans) {
        mRushAnswerBean.addAll(dataBeans);
        if(mRushAnswerBean.size() == 0){
            mNotData.setVisibility(View.VISIBLE);
            mRushRv.setVisibility(View.GONE);
        }else{
            mNotData.setVisibility(View.GONE);
            mRushRv.setVisibility(View.VISIBLE);
        }
        mRushAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        mNotData.setVisibility(View.VISIBLE);
        mRushRv.setVisibility(View.GONE);
        mSwRefresh.setRefreshing(false);
    }
}
