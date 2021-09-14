package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.OrderInfoBean;
import com.yanxin.store.bean.PayOrderBean;
import com.yanxin.store.commont.PayResult;
import com.yanxin.store.req.PayOrderReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.alipay.sdk.app.EnvUtils.EnvEnum.SANDBOX;

@XmlLayoutResId(contentId = R.layout.activity_pay)
public class PayActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.e(payResult.toString());
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        startActivity(new Intent(getBaseContext(), PayStatusActivity.class).putExtra("is_success", true));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        startActivity(new Intent(getBaseContext(), PayStatusActivity.class).putExtra("is_success", false));
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private TextView mPayAmount;
    private SuperTextView mPayWx;
    private SuperTextView mPayAli;
    private Button mSubmitPay;
    private String orderUuid;
    private String payType;

    @Override
    protected void initData() {
        queryOrderInfo(orderUuid);
    }

    @Override
    protected void initView() {
        orderUuid = getIntent().getStringExtra("order_uuid");
        mPayAmount = findViewById(R.id.pay_amount);
        mPayWx = findViewById(R.id.pay_wx);
        mPayAli = findViewById(R.id.pay_ali);
        mSubmitPay = findViewById(R.id.submit_pay);
        mPayWx.setCheckBoxCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayAli.setCbChecked(false);
            }
        });
        mPayAli.setCheckBoxCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayWx.setCbChecked(false);
            }
        });
        mPayWx.setOnSuperTextViewClickListener(superTextView -> {
            mPayWx.setCbChecked(true);
            mPayAli.setCbChecked(false);
        });
        mPayAli.setOnSuperTextViewClickListener(superTextView -> {
            mPayWx.setCbChecked(false);
            mPayAli.setCbChecked(true);
        });
        mSubmitPay.setOnClickListener(v -> {
            PayOrderReq payOrderReq = new PayOrderReq();
            if (mPayWx.getCbisChecked()) {
                payType = "weixin";
            } else {
                payType = "alipay";
                payOrderReq.setReturnUrl("alipay");
            }
            payOrderReq.setChannelType(payType);
            payOrderReq.setOrderUuid(orderUuid);
            payOrderReq.setPaymentType("ANDROID");
            createPayOrderInfo(payOrderReq);
        });

    }

    @SuppressLint("CheckResult")
    private void queryOrderInfo(String orderUuid) {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderInfo(MyApplication.getUserToken(), orderUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(orderInfoBean -> {
                            if (orderInfoBean.isSuccess()) {
                                SpanUtils.with(mPayAmount).append("¥")
                                        .setFontSize(ConvertUtils.dp2px(20))
                                        .append(orderInfoBean.getData().getOrderAmount() + "")
                                        .setFontSize(ConvertUtils.dp2px(30))
                                        .create();
                            } else {
                                ToastUtils.showShort(orderInfoBean.getMsg());
                                finish();
                            }
                        }
                        , throwable -> {
                            ToastUtils.showShort(throwable.getMessage());
                            finish();
                        });
    }

    @SuppressLint("CheckResult")
    private void createPayOrderInfo(PayOrderReq payOrderReq) {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .payOrder(MyApplication.getUserToken(), payOrderReq)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(payOrderBean -> toAliPay(payOrderBean)
                        , throwable -> ToastUtils.showShort(throwable.getMessage()));
    }

    private void toAliPay(PayOrderBean payOrderBean) {
        if (payType.equals("weixin")) {
            PayOrderBean.DataBean data = payOrderBean.getData();
            PayReq payReq = new PayReq();
            payReq.appId = MyApplication.getWXAppId();
            payReq.partnerId = data.getPartnerId();
            payReq.prepayId = data.getPrepayId();
            payReq.packageValue = "Sign=WXPay";
            payReq.nonceStr = data.getNonceStr();
            payReq.timeStamp = data.getTimeStamp();
            payReq.sign = data.getPaySign();
            MyApplication.getWXApi().sendReq(payReq);
        } else {
            Runnable payRunnable = () -> {
//                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(payOrderBean.getData().getBody(), true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }
}
