package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskDetailActivity;
import com.yanxin.store.activity.MainActivity;
import com.yanxin.store.adapter.rvadapter.MyRushAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.contract.FQMyRushContract;
import com.yanxin.store.presenter.FQMyRushPresenter;
import com.yanxin.store.req.MyRushReq;

import java.util.ArrayList;

/**
 * 发现页-知识问答  已抢答
 */
@XmlLayoutResId(contentId = R.layout.fragment_fq_my_rush)
public class FQMineRushFragment extends BaseMvpFragment<FQMyRushPresenter> implements FQMyRushContract.FQMyRushView {
    private int rushType;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mRushRv;
    private ArrayList<MyRushBean.DataBean> mDataDTOS;
    private MyRushAdapter myRushAdapter;
    private MyRushReq myRushReq;
    private ImageView mNotData;
    private final int queryPageSize = 50;
    private int queryPageNum = 1;

    @Override
    protected void initData() {
        super.initData();
        queryMineRushAll();
        mSwRefresh.setOnRefreshListener(() -> {
            queryPageNum = 1;
            mDataDTOS.clear();
            queryMineRushAll();
        });
        myRushAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent rushDetailIntent = new Intent(mContext, AskDetailActivity.class);
            rushDetailIntent.putExtra("order_uuid", mDataDTOS.get(position).getUuid());
            rushDetailIntent.putExtra("rush_uuid", mDataDTOS.get(position).getUuid());
            rushDetailIntent.putExtra("is_grab", true);
            rushDetailIntent.putExtra("is_order", true);
            startActivity(rushDetailIntent);
        });
    }

    private void queryMineRushAll() {
        myRushReq.setPageSize(queryPageSize);
        myRushReq.setPageNum(queryPageNum);
        myRushReq.setQuizzer(3);
        myRushReq.setState(rushType);
        mPresenter.getMineRush(myRushReq);
    }

    @Override
    protected void initView() {
        mDataDTOS = new ArrayList<>();
        myRushReq = new MyRushReq();
        rushType = getArguments().getInt("rush_type");
        myRushAdapter = new MyRushAdapter(R.layout.item_square, mDataDTOS);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mRushRv = findViewById(R.id.rush_rv);
        mNotData = findViewById(R.id.not_data);
        mRushRv.setAdapter(myRushAdapter);
    }


    @Override
    public BasePresenter initPresenter() {
        return FQMyRushPresenter.newInstance();
    }

    @Override
    public void mineRushSuccess(ArrayList<MyRushBean.DataBean> dtos) {
        mDataDTOS.addAll(dtos);
        if (mDataDTOS.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mRushRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mRushRv.setVisibility(View.VISIBLE);
        }
        ((MainActivity)mActivity).setMineTitle(mDataDTOS.size());
        myRushAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void failed(String msg) {
        mNotData.setVisibility(View.VISIBLE);
        mRushRv.setVisibility(View.GONE);
        ToastUtils.showShort(msg);
        mSwRefresh.setRefreshing(false);
    }
}
