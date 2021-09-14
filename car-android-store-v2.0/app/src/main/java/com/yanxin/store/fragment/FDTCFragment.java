package com.yanxin.store.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskEveryoneActivity;
import com.yanxin.store.activity.DtcAddActivity;
import com.yanxin.store.activity.DtcDetailActivity;
import com.yanxin.store.activity.DtcMyBuyActivity;
import com.yanxin.store.activity.MainActivity;
import com.yanxin.store.activity.PayActivity;
import com.yanxin.store.adapter.rvadapter.DTCAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.contract.FDTCContract;
import com.yanxin.store.presenter.FDTCPresenter;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.ui.FiltrateWindow;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;

/**
 * 发现页-技术案例
 */
@XmlLayoutResId(contentId = R.layout.fragment_f_dtc)
public class FDTCFragment extends BaseMvpFragment<FDTCPresenter> implements FDTCContract.FDTCView {

    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mDtcRv;
    private ArrayList<DTCBean.DataBean> mDtcBean;
    private DTCAdapter mDtcAdapter;
    private DTCReq mDtcReq;
    private String dtcCode;
    private String dtcBrand;
    private String dtcBrandUuid;
    private final int dtcSize = 50;
    private int dicNum = 1;
    private ImageView mAddDtc;
    private ImageView mMyBuyDtc;
    private ArrayList<FiltrateBean> mBrandBean;
    private FiltrateWindow mFiltrateWindow;

    @Override
    protected void initData() {
        super.initData();
        queryDtc();
        mSwRefresh.setOnRefreshListener(() -> {
            dicNum = 1;
            mDtcBean.clear();
            queryDtc();
        });
        mDtcRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == mDtcAdapter.getItemCount() && mDtcAdapter.getItemCount() >= 20) {
                        dicNum++;
                        queryDtc();
                    }
                }
            }
        });
        mAddDtc.setOnClickListener(v -> startActivity(new Intent(mContext, DtcAddActivity.class)));
        mMyBuyDtc.setOnClickListener(v -> startActivity(new Intent(mContext, DtcMyBuyActivity.class)));
        mDtcAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DTCBean.DataBean dataBean = mDtcBean.get(position);
            if (dataBean.getNeedBuy() == 1) {
                CreateDTCReq createDTCReq = new CreateDTCReq();
                createDTCReq.setDtcUuid(dataBean.getUuid());
                mPresenter.createDTCOrder(createDTCReq);
            } else {
                Intent dtcDetailIntent = new Intent(mContext, DtcDetailActivity.class);
                dtcDetailIntent.putExtra("dtc_uuid", mDtcBean.get(position).getUuid());
                dtcDetailIntent.putExtra("is_order", true);
                startActivity(dtcDetailIntent);
            }
        });
        mPresenter.queryAllBrand(BRAND_PARENT);
    }

    private void queryDtc() {
        mDtcReq.setDtcCode(dtcCode);
        mDtcReq.setPageNum(dicNum);
        mDtcReq.setPageSize(dtcSize);
        mDtcReq.setDtcBrandUuid(dtcBrandUuid);
        mPresenter.getDtcList(mDtcReq);
    }

    @Override
    protected void initView() {
        mDtcReq = new DTCReq();
        mDtcBean = new ArrayList<>();
        mBrandBean = new ArrayList<>();
        mSwRefresh = findViewById(R.id.sw_refresh);
        mDtcRv = findViewById(R.id.dtc_rv);
        mAddDtc = findViewById(R.id.add_dtc);
        mMyBuyDtc = findViewById(R.id.my_buy_dtc);
        mDtcAdapter = new DTCAdapter(R.layout.item_dtc, mDtcBean);
        mDtcRv.setAdapter(mDtcAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return FDTCPresenter.newInstance();
    }

    @Override
    public void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_BRAND);
            mBrandBean.add(filtrateBean);
        }
        if (mBrandBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("全部品牌");
            mBrandBean.add(0, filtrateBean);
        }
    }

    @Override
    public void getDTCSuccess(ArrayList<DTCBean.DataBean> dataBeans) {
        for (int i = 0; i < dataBeans.size(); i++) {
            DTCBean.DataBean dataBean = dataBeans.get(i);
            if (i % 2 == 0) {
                dataBean.setWhite(true);
            }
            mDtcBean.add(dataBean);
        }
        mDtcAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
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
        ToastUtils.showShort(msg);
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void failed(String msg) {
        mSwRefresh.setRefreshing(false);
    }

    public void setDtcSearchStr(String str) {
        dtcCode = str;
        mDtcBean.clear();
        queryDtc();
    }

    public void showDtcBrand(TextView mDtcBrand) {
        showPOPWindow(mDtcBrand,mBrandBean);
    }

    private void showPOPWindow(View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (filtrateBean.getName().equals("全部品牌")) {
                        dtcBrandUuid = null;
                        dtcBrand = null;
                        ((MainActivity) mActivity).setDtcBrandValue("按品牌");
                    } else {
                        dtcBrandUuid = filtrateBean.getUuid();
                        dtcBrand = filtrateBean.getName();
                        ((MainActivity) mActivity).setDtcBrandValue(dtcBrand);
                    }
                    mDtcBean.clear();
                    queryDtc();
                })
                .build(mActivity);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }
}
