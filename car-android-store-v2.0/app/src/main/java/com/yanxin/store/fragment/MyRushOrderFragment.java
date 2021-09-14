package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
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

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.fragment_my_rush_order)
public class MyRushOrderFragment extends BaseMvpFragment<RushOrderPresenter> implements RushOrderContract.RushOrderView {
    private SwipeRefreshLayout mSwRefresh;
    private ImageView mNotData;
    private RecyclerView mRushRv;
    private MyRushReq myRushReq;
    private AuditReq auditReq;
    private ArrayList<RushOrderBean> rushBean;
    private ArrayList<RushOrderBean> auditBean;
    private RushOrderAdapter mRushAdapter;
    private RushOrderAdapter mAuditAdapter;
    private int orderType;
    private int orderStatus;

    @Override
    protected void initData() {
        super.initData();
        switch (orderType) {
            case 0:
                myRushReq = new MyRushReq();
                myRushReq.setAnswerCheckSts(orderStatus);
                myRushReq.setQuizzer(2);
                mPresenter.querySqOrder(myRushReq);
                break;
            case 1:
                auditReq = new AuditReq();
                auditReq.setConsultType(4);
                auditReq.setCarOwnerUuid(MyApplication.getUserUuid());
                mPresenter.queryAuditOrder(auditReq);
                break;
        }
        mSwRefresh.setOnRefreshListener(() -> {
            switch (orderType) {
                case 0:
                    rushBean.clear();
                    mPresenter.querySqOrder(myRushReq);
                    break;
                case 1:
                    auditBean.clear();
                    mPresenter.queryAuditOrder(auditReq);
                    break;
            }
        });
        mRushAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            startActivity(new Intent(getContext(), AskDetailActivity.class)
                    .putExtra("order_uuid", rushBean.get(position).getUuid())
                    .putExtra("is_order", true)
                    .putExtra("is_posted", true));
        });
        mAuditAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            startActivity(new Intent(getContext(), RushDetailActivity.class)
                    .putExtra("order_uuid", auditBean.get(position).getUuid())
                    .putExtra("is_order", true)
                    .putExtra("is_pt", true));
        });
    }

    @Override
    protected void initView() {
        orderType = getArguments().getInt("order_type");
        orderStatus = getArguments().getInt("order_status");
        rushBean = new ArrayList<>();
        auditBean = new ArrayList<>();
        mSwRefresh = findViewById(R.id.sw_refresh);
        mNotData = findViewById(R.id.not_data);
        mRushRv = findViewById(R.id.rush_rv);
        mRushAdapter = new RushOrderAdapter(R.layout.item_rush_order, rushBean);
        mAuditAdapter = new RushOrderAdapter(R.layout.item_rush_order, auditBean);
        switch (orderType) {
            case 0:
                mRushRv.setAdapter(mRushAdapter);
                break;
            case 1:
                mRushRv.setAdapter(mAuditAdapter);
                break;
        }
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public BasePresenter initPresenter() {
        return RushOrderPresenter.newInstance();
    }

    @Override
    public void querySqOrder(ArrayList<MyRushBean.DataBean> dataBeans) {
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
            rushOrderBean.setAnswerSts(dataBean.getAnswerSts());
            rushOrderBean.setConsultCheckSts(dataBean.getConsultCheckSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            rushOrderBean.setAmount(dataBean.getOrderAmount());
            switch (dataBean.getOrderSts()) {
                case 0:
                    rushOrderBean.setStatusTitle("待支付");
                    break;
                case 1:
                    switch (dataBean.getConsultCheckSts()) {
                        case 0:
                            rushOrderBean.setStatusTitle("待审核");
                            break;
                        case 1:
                            if (dataBean.getAnswerSts() == 0) {
                                rushOrderBean.setStatusTitle("待答复");
                            } else {
                                rushOrderBean.setStatusTitle("已答复");
                            }
                            break;
                        case 2:
                            rushOrderBean.setStatusTitle("审核驳回");
                            break;
                    }
                    break;
                case 2:
                    rushOrderBean.setStatusTitle("已取消");
                    break;
                case 3:
                    rushOrderBean.setStatusTitle("退款中");
                    break;
                case 4:
                    rushOrderBean.setStatusTitle("退款成功");
                    break;
                case 5:
                    rushOrderBean.setStatusTitle("退款失败");
                    break;
                case 6:
                    rushOrderBean.setStatusTitle("已完成");
                    break;
            }

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
    public void queryMineOrderRushList(ArrayList<MyProblemBean.DataBean> dataBeans) {
//        for (MyProblemBean.DataBean dataBean : dataBeans) {
//            RushOrderBean rushOrderBean = new RushOrderBean();
//            rushOrderBean.setUuid(dataBean.getUuid());
//            ArrayList<String> imgUrl = dataBean.getImgurl();
//            if (imgUrl != null) {
//                rushOrderBean.setConsultImg(imgUrl);
//            } else {
//                rushOrderBean.setConsultImg(new ArrayList<>());
//            }
//            rushOrderBean.setDesc(dataBean.getConsultDesc());
//            rushOrderBean.setStatus(dataBean.getOrderSts());
//            rushOrderBean.setTitle(dataBean.getTitle());
//            switch (orderType) {
//                case 0:
//                    rushBean.add(rushOrderBean);
//                    if (rushBean.size() == 0) {
//                        mNotData.setVisibility(View.VISIBLE);
//                        mRushRv.setVisibility(View.GONE);
//                    } else {
//                        mNotData.setVisibility(View.GONE);
//                        mRushRv.setVisibility(View.VISIBLE);
//                    }
//                    mRushAdapter.notifyDataSetChanged();
//                    break;
//                case 1:
//                    auditBean.add(rushOrderBean);
//                    if (auditBean.size() == 0) {
//                        mNotData.setVisibility(View.VISIBLE);
//                        mRushRv.setVisibility(View.GONE);
//                    } else {
//                        mNotData.setVisibility(View.GONE);
//                        mRushRv.setVisibility(View.VISIBLE);
//                    }
//                    mAuditAdapter.notifyDataSetChanged();
//                    break;
//            }
//        }
//        mSwRefresh.setRefreshing(false);
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
            rushOrderBean.setDesc(dataBean.getConsultDesc());
            rushOrderBean.setOrderSts(dataBean.getOrderSts());
            rushOrderBean.setTitle(dataBean.getTitle());
            rushOrderBean.setStatusTitle("已旁听");
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

    }
}
