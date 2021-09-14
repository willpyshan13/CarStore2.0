package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.CaseOrderDetailActivity;
import com.yanxin.store.adapter.rvadapter.BuyCaseAdapter;
import com.yanxin.store.adapter.rvadapter.MyBuyCaseAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BuyCaseBean;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyOrderBean;
import com.yanxin.store.contract.CaseContract;
import com.yanxin.store.presenter.CasePresenter;
import com.yanxin.store.req.DefaultReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_my_case)
public class MyBuyCaseFragment extends BaseMvpFragment<CasePresenter> implements CaseContract.CaseView {
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mCaseRv;
    private MyBuyCaseAdapter myBuyCaseAdapter;
    private BuyCaseAdapter buyCaseAdapter;
    private ArrayList<MyOrderBean.DataBean> mMyBuyCaseBean;
    private ArrayList<BuyCaseBean.DataBean> mBuyCaseBean;
    private int mCaseType;
    private DefaultReq mDefaultReq;
    private final int pageSize = 50;
    private int pageNum = 1;
    private ImageView mNotData;

    @Override
    protected void initData() {
        super.initData();
        myBuyCaseAdapter = new MyBuyCaseAdapter(R.layout.item_case_my, mMyBuyCaseBean);
        buyCaseAdapter = new BuyCaseAdapter(R.layout.item_case_buy, mBuyCaseBean);
        if (mCaseType == 0) {
            mCaseRv.setAdapter(myBuyCaseAdapter);
        } else {
            mCaseRv.setAdapter(buyCaseAdapter);
        }
        queryCase();
        mRefreshView.setOnRefreshListener(() -> {
            if (mCaseType == 0) {
                pageNum = 1;
                mMyBuyCaseBean.clear();
                queryCase();
            } else {
                pageNum = 1;
                mBuyCaseBean.clear();
                queryCase();
            }
        });
        myBuyCaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent caseDetailIntent = new Intent(mContext, CaseOrderDetailActivity.class);
            caseDetailIntent.putExtra("case_uuid", mMyBuyCaseBean.get(position).getUuid());
            caseDetailIntent.putExtra("is_order", false);
            startActivity(caseDetailIntent);
        });
        buyCaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent caseDetailIntent = new Intent(mContext, CaseOrderDetailActivity.class);
            caseDetailIntent.putExtra("case_uuid", mBuyCaseBean.get(position).getUuid());
            caseDetailIntent.putExtra("is_order", true);
            startActivity(caseDetailIntent);
        });
    }

    private void queryCase() {
        mDefaultReq.setPageNum(pageNum);
        mDefaultReq.setPageSize(pageSize);
        if (mCaseType == 0) {
            mPresenter.getMyBuyCase(mDefaultReq);
        } else {
            mPresenter.getBuyCase(mDefaultReq);
        }
    }

    @Override
    protected void initView() {
        mMyBuyCaseBean = new ArrayList<>();
        mBuyCaseBean = new ArrayList<>();
        mDefaultReq = new DefaultReq();
        mCaseType = getArguments().getInt("case_type");
        mRefreshView = findViewById(R.id.sw_refresh);
        mCaseRv = findViewById(R.id.case_rv);
        mNotData = findViewById(R.id.not_data);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        mNotData.setVisibility(View.VISIBLE);
        mCaseRv.setVisibility(View.GONE);
        mRefreshView.setRefreshing(false);
    }

    @Override
    public BasePresenter initPresenter() {
        return CasePresenter.newInstance();
    }

    @Override
    public void myBuyCaseSuccess(ArrayList<MyOrderBean.DataBean> dataBeans) {
        mMyBuyCaseBean.addAll(dataBeans);
        if (mMyBuyCaseBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mCaseRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mCaseRv.setVisibility(View.VISIBLE);
        }
        myBuyCaseAdapter.notifyDataSetChanged();
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void mineCaseSuccess(ArrayList<MineCaseBean.DataBean> dataBeans) {
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void buyCaseSuccess(ArrayList<BuyCaseBean.DataBean> dataBeans) {
        mBuyCaseBean.addAll(dataBeans);
        if (mBuyCaseBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mCaseRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mCaseRv.setVisibility(View.VISIBLE);
        }
        buyCaseAdapter.notifyDataSetChanged();
        mRefreshView.setRefreshing(false);
    }
}
