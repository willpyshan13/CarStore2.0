package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.DtcDetailActivity;
import com.yanxin.store.activity.PayActivity;
import com.yanxin.store.adapter.rvadapter.MyDtcAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.contract.MyDtcContract;
import com.yanxin.store.event.MyDtcEvent;
import com.yanxin.store.presenter.MyDtcPresenter;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.MyDtcReq;
import com.yanxin.store.utils.BasicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_my_buy_dtc)
public class MyDtcFragment extends BaseMvpFragment<MyDtcPresenter> implements MyDtcContract.MyDtcView {
    private int mDtcType;
    private MyDtcReq myDtcReq;
    private int pageNum = 1;
    private final int pageSize = 50;
    private MyDtcAdapter myDtcAdapter;
    private ArrayList<MyDtcBean.DataDTO> mDtcBean;
    private ArrayList<MyDtcBean.DataDTO> mAllDtcBean;
    private EditText mFindDtc;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mDtcRv;
    private ImageView mSearchView;
    private ImageView mNotData;

    @Override
    protected void initData() {
        super.initData();
        queryDtcList();
        mSwRefresh.setOnRefreshListener(() -> {
            String dtcValue = BasicUtils.getEditValue(mFindDtc);
            if (!dtcValue.isEmpty()) {
                mSwRefresh.setRefreshing(false);
                return;
            }
            pageNum = 1;
            mDtcBean.clear();
            queryDtcList();
        });
        myDtcAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.dtc_count:
                    if (mDtcType == 1) {
                        CreateDTCReq createDTCReq = new CreateDTCReq();
                        createDTCReq.setDtcUuid(mDtcBean.get(position).getDtcUuid());
                        mPresenter.createDTCOrder(createDTCReq);
                    } else {
                        Intent dtcDetailIntent = new Intent(mContext, DtcDetailActivity.class);
                        dtcDetailIntent.putExtra("dtc_uuid", mDtcType == 0 ? mDtcBean.get(position).getDtcUuid() : mDtcBean.get(position).getUuid());
//                        dtcDetailIntent.putExtra("is_order", mDtcType == 0);
                        startActivity(dtcDetailIntent);
                    }
                    break;
                case R.id.dtc_layout:
                    if (mDtcType != 1) {
                        Intent dtcDetailIntent = new Intent(mContext, DtcDetailActivity.class);
                        dtcDetailIntent.putExtra("dtc_uuid", mDtcType == 0 ? mDtcBean.get(position).getDtcUuid() : mDtcBean.get(position).getUuid());
//                        dtcDetailIntent.putExtra("is_order", mDtcType == 0);
                        startActivity(dtcDetailIntent);
                    }
                    break;
            }
        });
        mSearchView.setOnClickListener(v -> {
            String dtcValue = BasicUtils.getEditValue(mFindDtc);
            querySearchDtcList(dtcValue);
        });
    }

    private void querySearchDtcList(String searchText) {
        mDtcBean.clear();
        for (MyDtcBean.DataDTO dataDTO : mAllDtcBean) {
            if (dataDTO.getDtcCode().contains(searchText)) {
                mDtcBean.add(dataDTO);
            }
        }
        if (mDtcBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mDtcRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mDtcRv.setVisibility(View.VISIBLE);
        }
        myDtcAdapter.notifyDataSetChanged();
    }

    private void queryDtcList() {
        myDtcReq.setPageNum(pageNum);
        myDtcReq.setPageSize(pageSize);
        myDtcReq.setDtcType(mDtcType);
        mPresenter.getDtcList(myDtcReq);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mDtcType = getArguments().getInt("dtc_type");
        mDtcBean = new ArrayList<>();
        mAllDtcBean = new ArrayList<>();
        myDtcReq = new MyDtcReq();
        myDtcAdapter = new MyDtcAdapter(R.layout.item_my_dtc, mDtcBean);
        mFindDtc = findViewById(R.id.find_dtc);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mDtcRv = findViewById(R.id.dtc_rv);
        mSearchView = findViewById(R.id.find_search);
        mDtcRv.setAdapter(myDtcAdapter);
        mNotData = findViewById(R.id.not_data);
    }

    @Override
    public BasePresenter initPresenter() {
        return MyDtcPresenter.newInstance();
    }

    @Override
    public void getDTCSuccess(ArrayList<MyDtcBean.DataDTO> dataBeans) {
        mSwRefresh.setRefreshing(false);
        for (MyDtcBean.DataDTO dataBean : dataBeans) {
            dataBean.setDtcType(mDtcType);
            mDtcBean.add(dataBean);
        }
        if (mDtcBean.size() == 0) {
            mNotData.setVisibility(View.VISIBLE);
            mDtcRv.setVisibility(View.GONE);
        } else {
            mNotData.setVisibility(View.GONE);
            mDtcRv.setVisibility(View.VISIBLE);
        }
        mAllDtcBean.addAll(mDtcBean);
        myDtcAdapter.notifyDataSetChanged();
    }

    @Override
    public void createDTCOrder(String uuid) {
        MyApplication.setPayStatusType(4);
        MyApplication.setPayOrderUuid(uuid);
        Intent payIntent = new Intent(mContext, PayActivity.class);
        payIntent.putExtra("order_uuid", uuid);
        startActivity(payIntent);
    }

    @Override
    public void getDTCFailed(String msg) {
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eventRefresh(MyDtcEvent myDtcEvent) {
        String dtcValue = BasicUtils.getEditValue(mFindDtc);
        if (!dtcValue.isEmpty()) {
            return;
        }
        pageNum = 1;
        mDtcBean.clear();
        queryDtcList();
    }
}
