package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.MallDetailActivity;
import com.yanxin.store.activity.MallGoodsCreateActivity;
import com.yanxin.store.adapter.rvadapter.MallGoodsAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.contract.MallGoodsContract;
import com.yanxin.store.event.MallGoodsEvent;
import com.yanxin.store.presenter.MallGoodsPresenter;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.GoodsReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_mall_goods)
public class MallGoodsFragment extends BaseMvpFragment<MallGoodsPresenter> implements MallGoodsContract.MallGoodsView {
    private SwipeRefreshLayout mSwRefresh;
    private ArrayList<GoodsBean.DataBean> mGoodsBean;
    private MallGoodsAdapter mallGoodsAdapter;
    private RecyclerView mGoodsRv;
    private ImageView mNotData;
    private GoodsReq mGoodsReq;
    private int goodsType;

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().unregister(this);
        mGoodsReq = new GoodsReq();
        switch (goodsType) {
            case 0:
                break;
            case 1:
                ArrayList<Integer> shangjiaList = new ArrayList<>();
                shangjiaList.add(1);
                mGoodsReq.setCheckSts(shangjiaList);
                mGoodsReq.setSellSts(1);
                break;
            case 2:
                ArrayList<Integer> xiajiaList = new ArrayList<>();
                xiajiaList.add(1);
                mGoodsReq.setCheckSts(xiajiaList);
                mGoodsReq.setSellSts(0);
                break;
            case 3:
                ArrayList<Integer> list = new ArrayList<>();
                list.add(0);
                list.add(2);
                mGoodsReq.setCheckSts(list);
                break;
        }
        mPresenter.queryGoodsData(mGoodsReq);
        mSwRefresh.setOnRefreshListener(() -> {
            mGoodsBean.clear();
            mGoodsReq.setPageNum(1);
            mPresenter.queryGoodsData(mGoodsReq);
        });
        mallGoodsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.mall_tab_item_online:
                    CreateMallReq createMallReq = new CreateMallReq();
                    createMallReq.setUuid(mGoodsBean.get(position).getUuid());
                    if (mGoodsBean.get(position).getSellSts() == 0) {
                        //上架
                        createMallReq.setSellSts(1);
                    } else {
                        //下架
                        createMallReq.setSellSts(0);
                    }
                    mPresenter.updateGoodsStatus(createMallReq);
                    break;
                case R.id.mall_tab_item_edit:
                    startActivity(new Intent(mContext, MallGoodsCreateActivity.class)
                            .putExtra("is_editor", true)
                            .putExtra("mall_uuid", mGoodsBean.get(position).getUuid())
                    );
                    break;
                case R.id.parent_layout:
                    startActivity(new Intent(mContext, MallDetailActivity.class)
                            .putExtra("mall_uuid", mGoodsBean.get(position).getUuid()));
                    break;
            }
        });
    }

    @Override
    protected void initView() {
        goodsType = getArguments().getInt("goods_type");
        mSwRefresh = findViewById(R.id.sw_refresh);
        mGoodsRv = findViewById(R.id.goods_rv);
        mNotData = findViewById(R.id.not_data);
        mGoodsBean = new ArrayList<>();
        mallGoodsAdapter = new MallGoodsAdapter(R.layout.item_mall_tab_order, mGoodsBean);
        mGoodsRv.setAdapter(mallGoodsAdapter);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void eventRefresh(MallGoodsEvent mallGoodsEvent) {
        mGoodsBean.clear();
        mGoodsReq.setPageNum(1);
        mPresenter.queryGoodsData(mGoodsReq);
    }

    @Override
    public BasePresenter initPresenter() {
        return MallGoodsPresenter.newInstance();
    }

    @Override
    public void updateGoodsStatus(GoodsDetailBean.DataBean bean) {
        EventBus.getDefault().post(new MallGoodsEvent());
    }

    @Override
    public void queryGoodsList(ArrayList<GoodsBean.DataBean> goodsReq) {
        mGoodsBean.addAll(goodsReq);
        if (mGoodsBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
        } else {
            mNotData.setVisibility(View.GONE);
            mGoodsRv.setVisibility(View.VISIBLE);
        }
        mallGoodsAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }


    @Override
    public void queryFailed(String msg) {
        mGoodsRv.setVisibility(View.GONE);
        mNotData.setVisibility(View.VISIBLE);
        mSwRefresh.setRefreshing(false);
        ToastUtils.showLong(msg);
    }
}
