package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.PayActivity;
import com.yanxin.store.activity.RushDetailActivity;
import com.yanxin.store.adapter.rvadapter.SquareAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.contract.FQSquareContract;
import com.yanxin.store.presenter.FQSquarePresenter;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.PayAuditReq;

import java.util.ArrayList;

/**
 * 发现页-知识问答  广场
 */
@XmlLayoutResId(contentId = R.layout.fragment_fq_square_list)
public class FQSquareListFragment extends BaseMvpFragment<FQSquarePresenter> implements FQSquareContract.FQSquareView {
    private int mAnswerType;
    private AuditReq mAuditReq;
    private int pageNum = 1;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mSquareRv;
    private SquareAdapter mSquareAdapter;
    private ArrayList<AuditBean.DataBean> mDataBeans;
    private ImageView mNotData;

    @Override
    protected void initData() {
        super.initData();
        if (mAnswerType == 1) {
            queryAnswerAll(0);
        } else {
            queryAnswerAll(4);
        }
        mSwRefresh.setOnRefreshListener(() -> {
            pageNum = 1;
            mDataBeans.clear();
            if (mAnswerType == 1) {
                queryAnswerAll(0);
            } else {
                queryAnswerAll(4);
            }
        });
        mSquareAdapter.setOnItemChildClickListener((adapter, view, position) ->
        {
            if (mDataBeans.get(position).getYesOrNo() == 1) {
                startActivity(new Intent(mContext, RushDetailActivity.class).putExtra("order_uuid", mDataBeans.get(position).getUuid())
                        .putExtra("is_order", true)
                        .putExtra("is_pt", true));
            } else {
                PayAuditReq payAuditReq = new PayAuditReq();
                payAuditReq.setOrderUuid(mDataBeans.get(position).getUuid());
                mPresenter.addAuditorOrder(payAuditReq);
            }
        });
        mSquareAdapter.setPayCallBack(payAuditReq -> mPresenter.addAuditorOrder(payAuditReq));
    }

    private void queryAnswerAll(int consultType) {
        mAuditReq.setPageNum(pageNum);
        mAuditReq.setConsultType(consultType);
        mAuditReq.setCarOwnerUuid(MyApplication.getUserUuid());
        mPresenter.queryAnswerAll(mAuditReq);
    }

    @Override
    protected void initView() {
        mAnswerType = getArguments().getInt("answer_type");
        mAuditReq = new AuditReq();
        mDataBeans = new ArrayList<>();
        mSwRefresh = findViewById(R.id.sw_refresh);
        mSquareRv = findViewById(R.id.square_rv);
        mNotData = findViewById(R.id.not_data);
        mSquareAdapter = new SquareAdapter(R.layout.item_square, mDataBeans);
        mSquareRv.setAdapter(mSquareAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return FQSquarePresenter.newInstance();
    }


    @Override
    public void paySuccess(String uuid) {
        MyApplication.setPayOrderUuid(uuid);
        MyApplication.setPayStatusType(2);
        Intent payIntent = new Intent(mContext, PayActivity.class);
        payIntent.putExtra("order_uuid", uuid);
        startActivity(payIntent);
    }

    @Override
    public void queryAnswerSuccess(ArrayList<AuditBean.DataBean> dataBeans) {
        mDataBeans.addAll(dataBeans);
        if (mDataBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mSquareRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mSquareRv.setVisibility(View.VISIBLE);
        }
        mSquareAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        mNotData.setVisibility(View.VISIBLE);
        mSquareRv.setVisibility(View.GONE);
        mSwRefresh.setRefreshing(false);
    }
}
