package com.yanxin.store.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.will.habit.utils.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.MallGroupBuyAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.contract.MallGroupBuyContract;
import com.yanxin.store.presenter.MallGroupBuyPresenter;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_mall_goods)
public class MallGroupBuyFragment extends BaseMvpFragment<MallGroupBuyPresenter> implements MallGroupBuyContract.MallGroupBuyView {
    private SwipeRefreshLayout mSwRefresh;
    private ArrayList<GroupCreateBean.DataBean> mGoodsBuyBean;
    private MallGroupBuyAdapter mallGroupBuyAdapter;
    private RecyclerView mGoodsRv;
    private ImageView mNotData;
    private GroupBuyReq mGroupBuyReq;
    private int goodsType;

    @Override
    protected void initData() {
        super.initData();
        mGroupBuyReq = new GroupBuyReq();
        ArrayList<Integer> list = new ArrayList<>();
        switch (goodsType) {
            case 0:
                break;
            case 1:
                list.add(0);
                mGroupBuyReq.setGroupSts(list);
                break;
            case 2:
                list.add(1);
                mGroupBuyReq.setGroupSts(list);
                break;
            case 3:
                list.add(2);
                mGroupBuyReq.setGroupSts(list);
                break;
        }
        mPresenter.queryAllGroupMall(mGroupBuyReq);
        mSwRefresh.setOnRefreshListener(() -> {
            mGoodsBuyBean.clear();
            mGroupBuyReq.setPageNum(1);
            mPresenter.queryAllGroupMall(mGroupBuyReq);
        });
    }

    @Override
    protected void initView() {
        goodsType = getArguments().getInt("goods_type");
        mSwRefresh = findViewById(R.id.sw_refresh);
        mGoodsRv = findViewById(R.id.goods_rv);
        mNotData = findViewById(R.id.not_data);
        mGoodsBuyBean = new ArrayList<>();
        mallGroupBuyAdapter = new MallGroupBuyAdapter(R.layout.item_mall_tab_order, mGoodsBuyBean);
        mGoodsRv.setAdapter(mallGroupBuyAdapter);
    }

    @Override
    public BasePresenter initPresenter() {
        return MallGroupBuyPresenter.newInstance();
    }

    @Override
    public void queryGroupBuyList(ArrayList<GroupCreateBean.DataBean> dataBeans) {
        mGoodsBuyBean.addAll(dataBeans);
        mallGroupBuyAdapter.notifyDataSetChanged();
        if (mGoodsBuyBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGoodsRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void queryFailed(String msg) {
        mGoodsRv.setVisibility(View.GONE);
        mNotData.setVisibility(View.VISIBLE);
        mSwRefresh.setRefreshing(false);
//        ToastUtils.showLong(msg);
    }
}
