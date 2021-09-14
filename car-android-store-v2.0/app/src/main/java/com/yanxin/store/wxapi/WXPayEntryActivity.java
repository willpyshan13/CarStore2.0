package com.yanxin.store.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yanxin.store.MyApplication;
import com.yanxin.store.activity.PayStatusActivity;

/**
 * 微信支付的回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyApplication.getWXApi().handleIntent(intent, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getWXApi().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    startActivity(new Intent(getBaseContext(), PayStatusActivity.class).putExtra("is_success", true));
                }                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    startActivity(new Intent(getBaseContext(), PayStatusActivity.class).putExtra("is_success", false));
                }
                break;
        }
        finish();
    }
}
