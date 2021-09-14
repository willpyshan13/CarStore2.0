package com.yanxin.store.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DtcTypeBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.contract.AddDTCContract;
import com.yanxin.store.presenter.AddDTCPresenter;
import com.yanxin.store.req.AddDTCReq;
import com.yanxin.store.ui.FiltrateWindow;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AppConfig.DTC_TYPE;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_DTC;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;

@XmlLayoutResId(contentId = R.layout.activity_dtc)
public class DtcAddActivity extends BaseMvpActivity<AddDTCPresenter> implements AddDTCContract.AddDTCView {
    private EditText mDtcPrefix;
    private EditText mDtcInfix;
    private EditText mDtcSuffix;
    private Button mDtcSubmit;
    private OptionalEditLayout mDtcDefinition;
    private EditText mDtcExplain;
    private EditText mDtcReasons;
    private EditText mDtcDiagnose;
    private EditText mDtcType;
    private TextView mDtcBrand;
    private TextView mDtcModel;
    private AddDTCReq mAddDTCReq;
    private FiltrateWindow mFiltrateWindow;
    private String queryBrandUuid;
    private String queryModelUuid;
    private String dtcTypeUuid;
    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModelBean;


    @Override
    protected void initMVPData() {
        mDtcSubmit.setOnClickListener(v -> {
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("请选择故障代码品牌");
                return;
            }
            if (queryModelUuid == null || queryModelUuid.isEmpty()) {
                ToastUtils.showShort("请选择故障代码车型");
                return;
            }
            if (BasicUtils.getEditValue(mDtcPrefix).isEmpty()) {
                ToastUtils.showShort("请输入DTC代码前缀");
                return;
            }
            mAddDTCReq.setDtcDefinition(mDtcDefinition.getContentEditValue());
            mAddDTCReq.setDtcBrandUuid(queryBrandUuid);
            mAddDTCReq.setDtcModelUuid(queryModelUuid);
            mAddDTCReq.setDtcCode(BasicUtils.getEditValue(mDtcPrefix));
            mAddDTCReq.setDtcCode2(BasicUtils.getEditValue(mDtcInfix));
            mAddDTCReq.setDtcCode3(BasicUtils.getEditValue(mDtcSuffix));
            mAddDTCReq.setDtcDiagnose(BasicUtils.getEditValue(mDtcDiagnose));
            mAddDTCReq.setDtcType(BasicUtils.getEditValue(mDtcType));
            mAddDTCReq.setDtcCheckSts(0);
            mAddDTCReq.setDtcExplain(BasicUtils.getEditValue(mDtcExplain));
            mAddDTCReq.setDtcReasons(BasicUtils.getEditValue(mDtcReasons));
            mPresenter.uploadDTCInfo(mAddDTCReq);
        });
        mPresenter.queryAllBrand(BRAND_PARENT);
//        mPresenter.queryDtcTypeList(DTC_TYPE);
        mDtcBrand.setOnClickListener(v -> {
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("正在获取品牌数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mDtcBrand, mBrandBean);
            }
        });
        mDtcModel.setOnClickListener(v -> {
            if (mModelBean.size() == 0) {
                ToastUtils.showShort("正在获取车型数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mDtcModel, mModelBean);
            }
        });

//        mDtcType.setOnClickListener(v -> {
//            if (mDtcTypeBean.size() == 0) {
//                ToastUtils.showShort("正在获取DTC类型，请稍后");
//            } else {
//                showPOPWindow(FILTRATE_TYPE_DTC, mDtcBrand, mDtcTypeBean);
//            }
//        });
    }

    @Override
    protected void initMVPView() {
        mBrandBean = new ArrayList<>();
        mModelBean = new ArrayList<>();
        mDtcPrefix = findViewById(R.id.dtc_prefix);
        mDtcInfix = findViewById(R.id.dtc_infix);
        mDtcSuffix = findViewById(R.id.dtc_suffix);
        mDtcSubmit = findViewById(R.id.dtc_submit);
        mDtcDefinition = findViewById(R.id.dtc_definition);
        mDtcExplain = findViewById(R.id.dtc_explain);
        mDtcReasons = findViewById(R.id.dtc_reasons);
        mDtcDiagnose = findViewById(R.id.dtc_diagnose);
        mDtcBrand = findViewById(R.id.dtc_brand);
        mDtcModel = findViewById(R.id.dtc_model);
        mDtcType = findViewById(R.id.dtc_type);
        mAddDTCReq = new AddDTCReq();
    }


    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public BasePresenter initPresenter() {
        return AddDTCPresenter.newInstance();
    }

//    @Override
//    public void queryDtcTypeList(ArrayList<DtcTypeBean.DataDTO> dataDTOS) {
//        for (DtcTypeBean.DataDTO dataBean : dataDTOS) {
//            FiltrateBean filtrateBean = new FiltrateBean();
//            filtrateBean.setName(dataBean.getLableDesc());
//            filtrateBean.setUuid(dataBean.getUuid());
//            filtrateBean.setType(FILTRATE_TYPE_DTC);
//            mDtcTypeBean.add(filtrateBean);
//        }
//    }

    @Override
    public void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_BRAND);
            mBrandBean.add(filtrateBean);
        }
    }

    @Override
    public void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_MODEL);
            mModelBean.add(filtrateBean);
        }
    }

    @Override
    public void success(String msg) {
        ToastUtils.showShort("补录成功");
        finish();
    }

    private void showPOPWindow(int tag, View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (tag == FILTRATE_TYPE_BRAND) {
                        queryBrandUuid = filtrateBean.getUuid();
                        queryModelUuid = null;
                        mDtcModel.setText("");
                        mDtcBrand.setText(filtrateBean.getName());
                        mModelBean.clear();
                        mPresenter.queryAllModel(queryBrandUuid);
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        queryModelUuid = filtrateBean.getUuid();
                        mDtcModel.setText(filtrateBean.getName());
                    }
                })
                .build(DtcAddActivity.this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }
}
