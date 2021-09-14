package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskDetailActivity;
import com.yanxin.store.activity.RushDetailActivity;
import com.yanxin.store.adapter.rvadapter.RushOrderAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.MyProblemBean;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.ReplyOrderBean;
import com.yanxin.store.bean.RushOrderBean;
import com.yanxin.store.contract.RushOrderContract;
import com.yanxin.store.presenter.RushOrderPresenter;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.req.ReplyOrderReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_rush_order)
public class RushOrderFragment extends BaseMvpFragment<RushOrderPresenter> implements RushOrderContract.RushOrderView {
    private SwipeRefreshLayout mSwRefresh;
    private ImageView mNotData;
    private RecyclerView mRushRv;
    private MyRushReq myRushReq;
    private AuditReq auditReq;
    private ReplyOrderReq replyOrderReq;
    private ArrayList<RushOrderBean> rushBean;
    private ArrayList<RushOrderBean> auditBean;
    private ArrayList<RushOrderBean> replyBean;
    private RushOrderAdapter mRushAdapter;
    private RushOrderAdapter mAuditAdapter;
    private RushOrderAdapter mReplyAdapter;
    private int type;
    private boolean isOrder;

    @Override
    protected void initData() {
        super.initData();
        switch (type) {
            case 0:
                if (isOrder) {
                    myRushReq = new MyRushReq();
                    myRushReq.setQuizzer(2);
                    mPresenter.queryMineRushOrder(myRushReq);
                } else {
                    mPresenter.queryMineRushOrder(1);
                }
                break;
            case 1:
//                if (isOrder) {
                auditReq = new AuditReq();
                auditReq.setConsultType(4);
                auditReq.setCarOwnerUuid(MyApplication.getUserUuid());
                mPresenter.queryAuditOrder(auditReq);
//                } else {
//                    mPresenter.queryMineRushOrder(2);
//                }
                break;
            case 2:
                if (isOrder) {
                    replyOrderReq = new ReplyOrderReq();
                    mPresenter.queryReplyOrder(replyOrderReq);
                } else {
                    mPresenter.queryMineRushOrder(3);
                }
                break;
        }
        mSwRefresh.setOnRefreshListener(() -> {
            switch (type) {
                case 0:
                    rushBean.clear();
                    if (isOrder) {
                        mPresenter.queryMineRushOrder(myRushReq);
                    } else {
                        mPresenter.queryMineRushOrder(1);
                    }
                    break;
                case 1:
                    auditBean.clear();
//                    if (isOrder) {
                    mPresenter.queryAuditOrder(auditReq);
//                    } else {
//                        mPresenter.queryMineRushOrder(2);
//                    }
                    break;
                case 2:
                    replyBean.clear();
                    if (isOrder) {
                        mPresenter.queryReplyOrder(replyOrderReq);
                    } else {
                        mPresenter.queryMineRushOrder(3);
                    }
                    break;
            }
        });
        mRushAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (isOrder) {
                startActivity(new Intent(getContext(), AskDetailActivity.class)
                        .putExtra("order_uuid", rushBean.get(position).getUuid())
                        .putExtra("is_order", isOrder)
                        .putExtra("is_posted", true));
            } else {
                startActivity(new Intent(getContext(), RushDetailActivity.class)
                        .putExtra("order_uuid", rushBean.get(position).getUuid())
                        .putExtra("is_order", isOrder));
            }
        });
        mAuditAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            if (isOrder) {
//                startActivity(new Intent(getContext(), AskDetailActivity.class)
//                        .putExtra("order_uuid", auditBean.get(position).getUuid())
//                        .put  Extra("is_order", isOrder));
//            } else {
            startActivity(new Intent(getContext(), RushDetailActivity.class)
                    .putExtra("order_uuid", auditBean.get(position).getUuid())
                    .putExtra("is_order", true)
                    .putExtra("is_pt", true));
//            }
        });
        mReplyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (isOrder) {
                startActivity(new Intent(getContext(), AskDetailActivity.class)
                        .putExtra("order_uuid", replyBean.get(position).getUuid())
                        .putExtra("is_order", isOrder));
            } else {
                startActivity(new Intent(getContext(), RushDetailActivity.class)
                        .putExtra("order_uuid", replyBean.get(position).getUuid())
                        .putExtra("is_order", isOrder));
            }
        });
    }

    @Override
    protected void initView() {
        type = getArguments().getInt("type");
        isOrder = getArguments().getBoolean("is_order");
        rushBean = new ArrayList<>();
        auditBean = new ArrayList<>();
        replyBean = new ArrayList<>();
        mSwRefresh = findViewById(R.id.sw_refresh);
        mNotData = findViewById(R.id.not_data);
        mRushRv = findViewById(R.id.rush_rv);
        mRushAdapter = new RushOrderAdapter(R.layout.item_rush_order, rushBean);
        mAuditAdapter = new RushOrderAdapter(R.layout.item_rush_order, auditBean);
        mReplyAdapter = new RushOrderAdapter(R.layout.item_rush_order, replyBean);
        switch (type) {
            case 0:
                mRushRv.setAdapter(mRushAdapter);
                break;
            case 1:
                mRushRv.setAdapter(mAuditAdapter);
                break;
            case 2:
                mRushRv.setAdapter(mReplyAdapter);
                break;
        }
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return RushOrderPresenter.newInstance();
    }

    @Override
    public void querySqOrder(ArrayList<MyRushBean.DataBean> dataBeans) {

    }

    @Override
    public void queryMineOrderRushList(ArrayList<MyProblemBean.DataBean> dataBeans) {
        for (MyProblemBean.DataBean dataBean : dataBeans) {
            RushOrderBean rushOrderBean = new RushOrderBean();
            rushOrderBean.setUuid(dataBean.getUuid());
            ArrayList<String> imgUrl = dataBean.getImgurl();
            if (imgUrl != null) {
                rushOrderBean.setConsultImg(imgUrl);
            } else {
                rushOrderBean.setConsultImg(new ArrayList<>());
            }
            rushOrderBean.setDesc(dataBean.getConsultDesc());
            rushOrderBean.setOrderSts(dataBean.getOrderSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            rushOrderBean.setAmount(dataBean.getConsultAmt());
            switch (type) {
                case 0:
                    rushBean.add(rushOrderBean);
                    break;
                case 1:
                    auditBean.add(rushOrderBean);
                    break;
                case 2:
                    replyBean.add(rushOrderBean);
                    break;
            }
        }

        switch (type) {
            case 0:
                if (rushBean.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                    mRushRv.setVisibility(View.GONE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mRushRv.setVisibility(View.VISIBLE);
                }
                mRushAdapter.notifyDataSetChanged();
                break;
            case 1:
                if (auditBean.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                    mRushRv.setVisibility(View.GONE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mRushRv.setVisibility(View.VISIBLE);
                }
                mAuditAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (replyBean.size() == 0) {
                    mNotData.setVisibility(View.VISIBLE);
                    mRushRv.setVisibility(View.GONE);
                } else {
                    mNotData.setVisibility(View.GONE);
                    mRushRv.setVisibility(View.VISIBLE);
                }
                mReplyAdapter.notifyDataSetChanged();
                break;
        }
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void queryMineRushList(ArrayList<MyRushBean.DataBean> dataBeans) {
        for (MyRushBean.DataBean dataBean : dataBeans) {
            RushOrderBean rushOrderBean = new RushOrderBean();
            rushOrderBean.setUuid(dataBean.getUuid());
            ArrayList<String> imgUrl = dataBean.getImgUrl();
            if (imgUrl != null) {
                rushOrderBean.setConsultImg(imgUrl);
            } else {
                rushOrderBean.setConsultImg(new ArrayList<>());
            }
            rushOrderBean.setDesc(dataBean.getConsultDesc());
            rushOrderBean.setOrderSts(dataBean.getOrderSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            rushOrderBean.setAmount(dataBean.getOrderAmount());
            rushBean.add(rushOrderBean);
        }
        if (rushBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mRushRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mRushRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
        mRushAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryAuditOrder(ArrayList<AuditBean.DataBean> dataBeans) {
        for (AuditBean.DataBean dataBean : dataBeans) {
            RushOrderBean rushOrderBean = new RushOrderBean();
            rushOrderBean.setUuid(dataBean.getUuid());
            ArrayList<String> imgUrl = dataBean.getImgs();
            if (imgUrl != null) {
                rushOrderBean.setConsultImg(imgUrl);
            } else {
                rushOrderBean.setConsultImg(new ArrayList<>());
            }
            rushOrderBean.setDesc("");
            rushOrderBean.setOrderSts(dataBean.getOrderSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            auditBean.add(rushOrderBean);
        }
        if (auditBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mRushRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mRushRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
        mAuditAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryReplyOrder(ArrayList<ReplyOrderBean.DataBean> dataBeans) {
        for (ReplyOrderBean.DataBean dataBean : dataBeans) {
            RushOrderBean rushOrderBean = new RushOrderBean();
            rushOrderBean.setUuid(dataBean.getUuid());
            ArrayList<String> imgUrl = dataBean.getImgUrl();
            if (imgUrl != null) {
                rushOrderBean.setConsultImg(imgUrl);
            } else {
                rushOrderBean.setConsultImg(new ArrayList<>());
            }
            rushOrderBean.setDesc(dataBean.getAnswerSts() == 0 ? "等待答复" : "已答复");
            rushOrderBean.setOrderSts(dataBean.getOrderSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            replyBean.add(rushOrderBean);
        }
        if (replyBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mRushRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mRushRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
        mReplyAdapter.notifyDataSetChanged();
    }
}
