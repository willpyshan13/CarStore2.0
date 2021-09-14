package com.yanxin.store.activity;

import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.subactivity.SubAccountActivity;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.UserLoginBean;
import com.yanxin.store.commont.Constant;
import com.yanxin.store.commont.Constant.AsynchronousStatus;
import com.yanxin.store.contract.LoginContract;
import com.yanxin.store.listener.ITimeEndCallBack;
import com.yanxin.store.presenter.LoginPresenter;
import com.yanxin.store.req.UserLoginReq;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.TimeVerification;

import cn.jpush.android.api.JPushInterface;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_TECHNICIAN;

@XmlLayoutResId(contentId = R.layout.activity_login)
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.LoginView {
    private EditText mLoginPhone;
    private EditText mLoginVerificationCode;
    private TextView mLoginSendCode;
    private TextView mStoreRegister;
    private TextView mTechnicianRegister;
    private TextView mServingProtocol;
    private TextView mPrivacy;
    private TextView mCopyright;
    private CheckBox mProtocol;
    private Button mLoginSubmit;

    @Override
    protected void initMVPData() {
        mLoginSubmit.setOnClickListener(view -> {
            String mPhoneValue = mLoginPhone.getText().toString().trim();
            String mVerificationCode = mLoginVerificationCode.getText().toString().trim();
            if (!RegexUtils.isMobileExact(mPhoneValue)) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            if (BasicUtils.strIsEmpty(mVerificationCode)) {
                ToastUtils.showShort("请输入验证码");
                return;
            }
            KeyboardUtils.hideSoftInput(this);
            if (!mProtocol.isChecked()) {
                ToastUtils.showShort("请先同意以下协议内容");
                return;
            }
            UserLoginReq userLoginReq = new UserLoginReq();
            userLoginReq.setPhoneValue(mPhoneValue);
            userLoginReq.setCode(mVerificationCode);
            mPresenter.login(userLoginReq);
        });
        mLoginSendCode.setOnClickListener(view -> {
            String mPhoneValue = mLoginPhone.getText().toString().trim();
            if (BasicUtils.strIsEmpty(mPhoneValue)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
            KeyboardUtils.hideSoftInput(this);
            mPresenter.getVerificationCode(mPhoneValue);
        });
        mPrivacy.setOnClickListener(view -> startAgreeActivity(Constant.WebDocumentTag.TAG_PRIVATE_AGREE));
        mServingProtocol.setOnClickListener(view -> startAgreeActivity(Constant.WebDocumentTag.TAG_SERVICE_AGREE));
        mCopyright.setOnClickListener(view -> startAgreeActivity(Constant.WebDocumentTag.TAG_COPYRIGHT_AGREE));
        //去店铺注册
        mStoreRegister.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), StoreRegisterActivity.class)));
        //技师注册
        mTechnicianRegister.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), TechnicianRegisterActivity.class)));
    }

    @Override
    protected void initMVPView() {
        mLoginPhone = findViewById(R.id.login_phone);
        mLoginVerificationCode = findViewById(R.id.login_verification_code);
        mLoginSendCode = findViewById(R.id.login_send_code);
        mTechnicianRegister = findViewById(R.id.login_technician_register);
        mStoreRegister = findViewById(R.id.login_store_register);
        mServingProtocol = findViewById(R.id.login_serving_protocol);
        mPrivacy = findViewById(R.id.login_privacy);
        mCopyright = findViewById(R.id.login_copyright);
        mProtocol = findViewById(R.id.login_protocol);
        mLoginSubmit = findViewById(R.id.login_submit);
        if (!MyApplication.getUserToken().isEmpty()) {
            int userSubAccount = SPUtils.getInstance().getInt("user_sub_account");
            if (userSubAccount == 0) {
                startActivity(new Intent(getBaseContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                startActivity(new Intent(getBaseContext(), SubAccountActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            finish();
        }
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }

    /**
     * 跳到隐私政策等协议页面
     *
     * @param agreeType
     */
    private void startAgreeActivity(int agreeType) {
        startActivity(new Intent(getBaseContext(), AgreementActivity.class).putExtra("agree_tag", agreeType));
    }

    @Override
    public void verificationSuccess(String msg) {
        ToastUtils.showShort(msg);
        TimeVerification.getInstence().setOnTimeEndCallBack(new ITimeEndCallBack() {
            @Override
            public void update(int time) {
                mLoginSendCode.setEnabled(false);
                mLoginSendCode.setTextColor(Color.parseColor("#999999"));
                mLoginSendCode.setText(time + "s后重新发送");
            }

            @Override
            public void exit() {
                mLoginSendCode.setEnabled(true);
                mLoginSendCode.setTextColor(Color.parseColor("#0091FF"));
                mLoginSendCode.setText("重新发送");
            }
        }).updateTime();
    }

    @Override
    public void loginSuccess(UserLoginBean.DataBean dataBean) {
        //这里不需要区分待审核和审核失败，只需要知道是不是成功就行。
        if (dataBean.getCheckSts() == AsynchronousStatus.REGISTER_SUCCESS) {
            if (dataBean.getIsSubAccount() == 0) {
                SPUtils.getInstance().put("user_token", dataBean.getToken());
                SPUtils.getInstance().put("user_uuid", dataBean.getUuid());
                SPUtils.getInstance().put("user_sub_account", dataBean.getIsSubAccount());
                SPUtils.getInstance().put("user_type", dataBean.getUserType());
                JPushInterface.setAlias(getBaseContext(), 0, dataBean.getUuid());
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            } else {
                SPUtils.getInstance().put("user_token", dataBean.getToken());
                SPUtils.getInstance().put("user_uuid", dataBean.getUuid());
                SPUtils.getInstance().put("user_sub_account", dataBean.getIsSubAccount());
                SPUtils.getInstance().put("user_type", dataBean.getUserType());
                JPushInterface.setAlias(getBaseContext(), 0, dataBean.getUuid());
                startActivity(new Intent(getBaseContext(), SubAccountActivity.class));
                finish();
            }
        } else {
            if (dataBean.getUserType() == USER_TYPE_TECHNICIAN) {
                startActivity(new Intent(getBaseContext(), TechnicianRegisterActivity.class)
                        .putExtra("is_login", true) //用来区分是主动进入还是登陆后被动进入，被动进入需要填充内容
                        .putExtra("user_token", dataBean.getToken())
                        .putExtra("user_uuid", dataBean.getUuid())
                        .putExtra("user_register_type", dataBean.getCheckSts()));
            } else {
                startActivity(new Intent(getBaseContext(), StoreRegisterActivity.class)
                        .putExtra("is_login", true) //用来区分是主动进入还是登陆后被动进入，被动进入需要填充内容
                        .putExtra("user_token", dataBean.getToken())
                        .putExtra("user_uuid", dataBean.getUuid())
                        .putExtra("user_register_type", dataBean.getCheckSts()));
            }
        }
    }

    @Override
    public void failed(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimeVerification.getInstence().onExit();
    }
}
