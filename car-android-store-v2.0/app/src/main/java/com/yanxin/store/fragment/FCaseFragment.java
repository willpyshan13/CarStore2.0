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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.CaseDetailActivity;
import com.yanxin.store.activity.MyCaseActivity;
import com.yanxin.store.activity.CaseOrderDetailActivity;
import com.yanxin.store.activity.PushCaseActivity;
import com.yanxin.store.adapter.rvadapter.CaseVehicleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CaseVehicleBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.contract.FCaseContract;
import com.yanxin.store.presenter.FCasePresenter;
import com.yanxin.store.req.CaseQueryVehicleReq;
import com.yanxin.store.ui.FiltrateWindow;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AppConfig.ATTACH_SYS;
import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_SYS;

/**
 * 发现页-技术案例
 */
@XmlLayoutResId(contentId = R.layout.fragment_f_case)
public class FCaseFragment extends BaseMvpFragment<FCasePresenter> implements FCaseContract.FCaseView {
    private TextView mCaseBrand;
    private TextView mCaseCar;
    private TextView mCaseSystem;
    private RecyclerView mCaseRv;
    private ImageView mMineCase;
    private ImageView mAddCase;
    private ImageView mNotDate;
    private SwipeRefreshLayout mRefresh;
    private ArrayList<CaseVehicleBean.DataBean> mVehicleArray;
    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModelBean;
    private ArrayList<FiltrateBean> mSystemBean;
    private CaseVehicleAdapter caseVehicleAdapter;
    private CaseQueryVehicleReq caseQueryVehicleReq;
    private FiltrateWindow mFiltrateWindow;
    private String queryBrandUuid;
    private String queryModel;
    private String queryAttachSys;
    private int queryPageSize = 20;
    private int queryPageNum = 1;


    @Override
    protected void initData() {
        super.initData();
        queryCaseForVehicleList();
        mPresenter.queryAllBrand(BRAND_PARENT);
        mPresenter.queryAllSystem(ATTACH_SYS);
        mCaseBrand.setOnClickListener(v -> {
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("正在获取品牌数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mCaseBrand, mBrandBean);
            }
        });
        mCaseCar.setOnClickListener(v -> {
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("请先选择车辆品牌");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mCaseBrand, mModelBean);
            }
        });
        mCaseSystem.setOnClickListener(v -> showPOPWindow(FILTRATE_TYPE_SYS, mCaseBrand, mSystemBean));
        mRefresh.setOnRefreshListener(() -> {
            queryPageNum = 1;
            mVehicleArray.clear();
            queryCaseForVehicleList();
        });
        mCaseRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == caseVehicleAdapter.getItemCount() && caseVehicleAdapter.getItemCount() >= 20) {
                        queryPageNum++;
                        queryCaseForVehicleList();
                    }
                }
            }
        });
        mAddCase.setOnClickListener(v -> startActivity(new Intent(mActivity, PushCaseActivity.class)));
        mMineCase.setOnClickListener(v -> {
            LogUtils.e(MyApplication.getUserUuid());
            startActivity(new Intent(mActivity, MyCaseActivity.class));
        });
        caseVehicleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (mRefresh.isRefreshing()) {
                return;
            }
            Intent caseDetailIntent = new Intent(mContext, CaseDetailActivity.class);
            caseDetailIntent.putExtra("case_uuid", mVehicleArray.get(position).getUuid());
            caseDetailIntent.putExtra("is_order", false);
            startActivity(caseDetailIntent);
        });
    }

    private void queryCaseForVehicleList() {
        caseQueryVehicleReq.setBrandUuid(queryBrandUuid);
        caseQueryVehicleReq.setModel(queryModel);
        caseQueryVehicleReq.setAttachSys(queryAttachSys);
        caseQueryVehicleReq.setPageNum(queryPageNum);
        caseQueryVehicleReq.setPageSize(queryPageSize);
        mPresenter.queryCaseForVehicleList(caseQueryVehicleReq);
    }

    @Override
    protected void initView() {
        mCaseBrand = findViewById(R.id.case_brand);
        mCaseCar = findViewById(R.id.case_car);
        mCaseSystem = findViewById(R.id.case_system);
        mCaseRv = findViewById(R.id.case_rv);
        mMineCase = findViewById(R.id.mine_case);
        mAddCase = findViewById(R.id.add_case);
        mNotDate = findViewById(R.id.not_data);
        mRefresh = findViewById(R.id.sw_refresh);
        mVehicleArray = new ArrayList<>();
        mBrandBean = new ArrayList<>();
        mModelBean = new ArrayList<>();
        mSystemBean = new ArrayList<>();
        caseVehicleAdapter = new CaseVehicleAdapter(R.layout.item_rv_case, mVehicleArray);
        caseQueryVehicleReq = new CaseQueryVehicleReq();
        mCaseRv.setAdapter(caseVehicleAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return FCasePresenter.newInstance();
    }

    @Override
    public void queryAllSystemSuccess(ArrayList<DictBean.DataBean> brandBean) {
        for (DictBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getLableDesc());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_SYS);
            mSystemBean.add(filtrateBean);
        }
        if (mSystemBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("全部系统");
            mSystemBean.add(0, filtrateBean);
        }
    }

    @Override
    public void queryAllSystemFailed(String msg) {

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
    public void queryAllBrandFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        mModelBean.clear();
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_MODEL);
            mModelBean.add(filtrateBean);
        }
        if (mModelBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("全部车型");
            mModelBean.add(0, filtrateBean);
        }
    }

    @Override
    public void queryAllModelFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void queryCaseForVehicleSuccess(ArrayList<CaseVehicleBean.DataBean> vehicleArray) {
        mVehicleArray.addAll(vehicleArray);
        if (mVehicleArray.size() == 0) {
            mNotDate.setVisibility(View.VISIBLE);
            mCaseRv.setVisibility(View.GONE);
        } else {
            mNotDate.setVisibility(View.GONE);
            mCaseRv.setVisibility(View.VISIBLE);
        }
        caseVehicleAdapter.notifyDataSetChanged();
        mRefresh.setRefreshing(false);
    }

    @Override
    public void queryCaseForVehicleFailed(String msg) {
        ToastUtils.showShort(msg);
        mRefresh.setRefreshing(false);
        mNotDate.setVisibility(View.VISIBLE);
        mCaseRv.setVisibility(View.GONE);
    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
    }

    private void showPOPWindow(int tag, View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (tag == FILTRATE_TYPE_BRAND) {
                        if (filtrateBean.getName().equals("全部品牌")) {
                            queryBrandUuid = null;
                            queryModel = null;
                            mCaseCar.setText("按车型");
                            mCaseBrand.setText("按品牌");
                        } else {
                            queryBrandUuid = filtrateBean.getUuid();
                            mPresenter.queryAllModel(queryBrandUuid);
                            mCaseBrand.setText(filtrateBean.getName());
                        }
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        if (filtrateBean.getName().equals("全部品牌")) {
                            queryModel = null;
                            mCaseCar.setText("按车型");
                        } else {
                            queryModel = filtrateBean.getUuid();
                            mCaseCar.setText(filtrateBean.getName());
                        }
                    } else if (tag == FILTRATE_TYPE_SYS) {
                        if (filtrateBean.getName().equals("全部品牌")) {
                            queryAttachSys = null;
                            mCaseSystem.setText("按系统");
                        } else {
                            queryAttachSys = filtrateBean.getUuid();
                            mCaseSystem.setText(filtrateBean.getName());
                        }
                    }
                    mVehicleArray.clear();
                    queryCaseForVehicleList();
                })
                .build(mContext);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }
}
