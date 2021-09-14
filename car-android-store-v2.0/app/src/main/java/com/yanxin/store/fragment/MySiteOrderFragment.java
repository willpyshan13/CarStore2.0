package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanxin.store.R;
import com.yanxin.store.activity.GrabOrderDetailActivity;
import com.yanxin.store.adapter.rvadapter.MySiteAdapter;
import com.yanxin.store.adapter.rvadapter.SupportWaitAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.MySiteContract;
import com.yanxin.store.presenter.MySitePresenter;
import com.yanxin.store.req.GrabOrderReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_my_site)
public class MySiteOrderFragment extends BaseMvpFragment<MySitePresenter> implements MySiteContract.MySiteView {
    private int type;  //0:全部   1:进行中   2:已完成
    private SwipeRefreshLayout mSwRefresh;
    private MySiteAdapter mySiteAdapter;
    private ImageView mNotData;
    private RecyclerView mSiteRv;
    private GrabOrderReq mSupReq;
    private int pageSize = 50;
    private int pageNum = 1;
    private ArrayList<SupportOrderBean.DataBean> mBeans;


    @Override
    protected void initData() {
        super.initData();
        switch (type) {
            case 0:
                mSupReq.setStatus(null);
                break;
            case 1:
            case 2:
                mSupReq.setStatus(type);
                break;
        }
        querySiteOrder();
        mSwRefresh.setOnRefreshListener(() -> {
            pageNum = 1;
            mBeans.clear();
            querySiteOrder();
        });
        mySiteAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getContext(), GrabOrderDetailActivity.class);
            intent.putExtra("grab_uuid", mBeans.get(position).getUuid());
            startActivity(intent);
        });
    }

    private void querySiteOrder() {
        mSupReq.setPageNum(pageNum);
        mSupReq.setPageSize(pageSize);
        mPresenter.getSiteOrder(mSupReq);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        mSwRefresh.setRefreshing(false);
        ToastUtils.showShort(msg);
    }

    @Override
    public BasePresenter initPresenter() {
        return MySitePresenter.newInstance();
    }

    @Override
    protected void initView() {
        mBeans = new ArrayList<>();
        mySiteAdapter = new MySiteAdapter(R.layout.item_my_site, mBeans);
        type = getArguments().getInt("type");
        mSwRefresh = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        mNotData = (ImageView) findViewById(R.id.not_data);
        mSiteRv = (RecyclerView) findViewById(R.id.site_rv);
        mSupReq = new GrabOrderReq();
        mSupReq.setQueryType(1);
        mSiteRv.setAdapter(mySiteAdapter);
    }

    @Override
    public void orderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans) {
        mBeans.addAll(supportBeans);
        if (mBeans.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mSiteRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mSiteRv.setVisibility(View.VISIBLE);
        }
        mSwRefresh.setRefreshing(false);
        mySiteAdapter.notifyDataSetChanged();
    }
}
