package com.yanxin.store.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.MallOrderGoodsListAdapter;
import com.yanxin.store.adapter.rvadapter.MallOrderGroupListAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.contract.MallOrderContract;
import com.yanxin.store.presenter.MallOrderPresenter;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_mall_order_child)
public class MallOrderChildFragment extends BaseMvpFragment<MallOrderPresenter> implements MallOrderContract.MallOrderView {
    private int mOrderType;   //0 单品订单  1  拼团订单  2 工位订单
    private int mStatus;   //0 全部  1  进行中  2 已完成
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mGoodsRv;
    private MallOrderGoodsListAdapter mallOrderGoodsListAdapter;
    private MallOrderGroupListAdapter mallOrderGroupListAdapter;
    private ArrayList<GoodsBean.DataBean> mDataBeans;
    private ArrayList<GroupCreateBean.DataBean> mGroupBean;
    private ImageView mNotData;
    private GoodsReq goodsReq;
    private GroupBuyReq groupBuyReq;

    @Override
    protected void initData() {
        super.initData();
        if (mOrderType == 0) {
            goodsReq = new GoodsReq();
            switch (mStatus) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }

//            mPresenter.queryGoodsOrderData();
        } else if (mOrderType == 1) {
            groupBuyReq = new GroupBuyReq();
            switch (mStatus) {
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    }

    @Override
    protected void initView() {
        mOrderType = getArguments().getInt("order_type");
        mStatus = getArguments().getInt("order_status_type");
        mSwRefresh = findViewById(R.id.sw_refresh);
        mGoodsRv = findViewById(R.id.goods_rv);
        mNotData = findViewById(R.id.not_data);
        mDataBeans = new ArrayList<>();
        mGroupBean = new ArrayList<>();
        if (mOrderType == 0) {
            mallOrderGoodsListAdapter = new MallOrderGoodsListAdapter(R.layout.item_mall_tab_employee, mDataBeans);
            mGoodsRv.setAdapter(mallOrderGoodsListAdapter);
        } else if (mOrderType == 1) {
            mallOrderGroupListAdapter = new MallOrderGroupListAdapter(R.layout.item_mall_tab_employee, mGroupBean);
            mGoodsRv.setAdapter(mallOrderGroupListAdapter);
        } else {

        }

    }

    @Override
    public void queryGoodsOrderList(ArrayList<GoodsBean.DataBean> goodsReq) {
        mDataBeans.addAll(goodsReq);
        mallOrderGoodsListAdapter.notifyDataSetChanged();
        if (mDataBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGoodsRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void queryGroupBuyOrderList(ArrayList<GroupCreateBean.DataBean> goodsReq) {
        mGroupBean.addAll(goodsReq);
        mallOrderGroupListAdapter.notifyDataSetChanged();
        if (mGroupBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGoodsRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void queryStationOrderList(ArrayList<GoodsBean.DataBean> goodsReq) {

    }

    @Override
    public void querySuccess(ArrayList<GoodsBean.DataBean> dataBeans) {

    }

    @Override
    public void queryFailed(String msg) {
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public BasePresenter initPresenter() {
        return MallOrderPresenter.newInstance();
    }
}
