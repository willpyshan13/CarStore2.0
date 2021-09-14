package com.yanxin.store.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.CaseDetailActivity;
import com.yanxin.store.activity.CaseOrderDetailActivity;
import com.yanxin.store.activity.PushCaseActivity;
import com.yanxin.store.adapter.rvadapter.BuyCaseAdapter;
import com.yanxin.store.adapter.rvadapter.MineCaseAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BuyCaseBean;
import com.yanxin.store.bean.CaseOrderDetailBean;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyOrderBean;
import com.yanxin.store.contract.CaseContract;
import com.yanxin.store.presenter.CasePresenter;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

@XmlLayoutResId(contentId = R.layout.fragment_my_case)
public class MyCaseFragment extends BaseMvpFragment<CasePresenter> implements CaseContract.CaseView {
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mCaseRv;
    private MineCaseAdapter mineCaseAdapter;
    private BuyCaseAdapter buyCaseAdapter;
    private ArrayList<MineCaseBean.DataBean> mMineCaseBean;
    private ArrayList<BuyCaseBean.DataBean> mBuyCaseBean;
    private int mCaseType;
    private DefaultReq mDefaultReq;
    private final int pageSize = 50;
    private int pageNum = 1;
    private ImageView mNotData;

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        super.initData();
        mineCaseAdapter = new MineCaseAdapter(R.layout.item_case_my, mMineCaseBean);
        buyCaseAdapter = new BuyCaseAdapter(R.layout.item_case_buy, mBuyCaseBean);
        if (mCaseType == 0) {
            mCaseRv.setAdapter(mineCaseAdapter);
        } else {
            mCaseRv.setAdapter(buyCaseAdapter);
        }
        queryCase();
        mRefreshView.setOnRefreshListener(() -> {
            if (mCaseType == 0) {
                pageNum = 1;
                mMineCaseBean.clear();
                queryCase();
            } else {
                pageNum = 1;
                mBuyCaseBean.clear();
                queryCase();
            }
        });
        mineCaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent caseDetailIntent;
            if (mMineCaseBean.get(position).getCheckStatus() == 2) {
                caseDetailIntent = new Intent(mContext, PushCaseActivity.class);
                caseDetailIntent.putExtra("case_uuid", mMineCaseBean.get(position).getCaseUuid());
                caseDetailIntent.putExtra("remarks", mMineCaseBean.get(position).getRemarks());
                caseDetailIntent.putExtra("is_remarks", true);
            } else {
                caseDetailIntent = new Intent(mContext, CaseDetailActivity.class);
                caseDetailIntent.putExtra("case_uuid", mMineCaseBean.get(position).getCaseUuid());
                caseDetailIntent.putExtra("is_order", false);
            }
            startActivity(caseDetailIntent);
        });
        buyCaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                    .queryCaseOrderDetail(MyApplication.getUserToken(), mBuyCaseBean.get(position).getUuid())
                    .compose(RxHelper.rxSchedulerHelper())
                    .subscribe(caseOrderDetailBean -> {
                        Intent caseDetailIntent = new Intent(mContext, CaseDetailActivity.class);
                        caseDetailIntent.putExtra("case_uuid", caseOrderDetailBean.getData().getCaseInfoListRes().get(0).getCaseUuid());
                        caseDetailIntent.putExtra("is_order", false);
                        startActivity(caseDetailIntent);
                    });
        });
    }

    private void queryCase() {
        mDefaultReq.setPageNum(pageNum);
        mDefaultReq.setPageSize(pageSize);
        if (mCaseType == 0) {
            mPresenter.getMineCase(mDefaultReq);
        } else {
            mPresenter.getBuyCase(mDefaultReq);
        }
    }

    @Override
    protected void initView() {
        mMineCaseBean = new ArrayList<>();
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
    public void mineCaseSuccess(ArrayList<MineCaseBean.DataBean> dataBeans) {
        mMineCaseBean.addAll(dataBeans);
        if (mMineCaseBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mCaseRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mCaseRv.setVisibility(View.VISIBLE);
        }
        mineCaseAdapter.notifyDataSetChanged();
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void myBuyCaseSuccess(ArrayList<MyOrderBean.DataBean> dataBeans) {

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
