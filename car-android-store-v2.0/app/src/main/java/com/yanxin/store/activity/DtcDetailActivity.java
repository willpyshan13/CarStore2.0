package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.DtcDetailBean;
import com.yanxin.store.bean.DtcOrderDetailBean;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.event.DtcMyBuyEvent;
import com.yanxin.store.event.MyDtcEvent;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

@XmlLayoutResId(contentId = R.layout.activity_dtc_detail)
public class DtcDetailActivity extends BaseActivity {
    private String dtcUuid;
    private OptionalEditLayout mDtcCode;
    private OptionalEditLayout mDtcTitle;
    private OptionalEditLayout mDtcBrand;
    private OptionalEditLayout mDtcType;
    private OptionalEditLayout mDtcModel;
    private TextView mDtcInstruction;
    private TextView mDtcPossibleReason;
    private TextView mDtcDiagnosticAssistance;
    private boolean isOrder;

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryDtcDetail(MyApplication.getUserToken(), dtcUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(dtcDetailBean -> {
                            if (dtcDetailBean.isSuccess()) {
                                EventBus.getDefault().post(new MyDtcEvent());
                                EventBus.getDefault().post(new DtcMyBuyEvent());
                                initDtcDetail(dtcDetailBean.getData());
                            }
                        }
                        , throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    private void initDtcOrderDetail(DtcOrderDetailBean.DataBean data) {
        EventBus.getDefault().post(new MyDtcEvent());
        mDtcCode.setContentTextView(data.getDtcCode() + data.getDtcCode2() + data.getDtcCode2());
//        mDtcModel.setContentTextView(data.get);
        mDtcTitle.setContentTextView(data.getDtcDefinition());
        mDtcBrand.setContentTextView(data.getDtcBrandName());
        mDtcModel.setContentTextView(data.getDtcModelUuidName());
        mDtcType.setContentTextView(data.getDtcType());
        mDtcInstruction.setText(data.getDtcExplain());
        mDtcPossibleReason.setText(data.getDtcReasons());
        mDtcDiagnosticAssistance.setText(data.getDtcDiagnose());
    }


    private void initDtcDetail(DtcDetailBean.DataBean data) {
        mDtcCode.setContentTextView(data.getDtcCode() + data.getDtcCode2() + data.getDtcCode2());
        mDtcModel.setContentTextView(data.getDtcModelUuidName());
        mDtcTitle.setContentTextView(data.getDtcDefinition());
        mDtcBrand.setContentTextView(data.getDtcBrandUuidName());
        mDtcType.setContentTextView(data.getDtcType());
        mDtcInstruction.setText(data.getDtcExplain());
        mDtcPossibleReason.setText(data.getDtcReasons());
        mDtcDiagnosticAssistance.setText(data.getDtcDiagnose());
    }

    @Override
    protected void initView() {
        dtcUuid = getIntent().getStringExtra("dtc_uuid");
        isOrder = getIntent().getBooleanExtra("is_order", false);
        mDtcCode = findViewById(R.id.dtc_code);
        mDtcTitle = findViewById(R.id.dtc_title);
        mDtcBrand = findViewById(R.id.dtc_brand);
        mDtcType = findViewById(R.id.dtc_type);
        mDtcInstruction = findViewById(R.id.dtc_instruction);
        mDtcPossibleReason = findViewById(R.id.dtc_possible_reason);
        mDtcDiagnosticAssistance = findViewById(R.id.dtc_diagnostic_assistance);
        mDtcModel = findViewById(R.id.dtc_model);
    }
}
