package com.yanxin.store;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.will.habit.base.BaseApplication;
import com.will.habit.utils.KLog;
import com.will.habit.utils.Utils;
import com.yanxin.store.commont.Constant;
import com.yanxin.store.commont.PayStatusConfig;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends BaseApplication {
    private static Application mApplication;
    public static String BASE_URL = BuildConfig.SERVER_URL;
//        public static String BASE_URL = "https://dev.api.dlvehicle.com/";
    private static String APP_ID = "wxe7d526a5f123af21";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private static IWXAPI wxApi;
    private static boolean isX5Success;
    private static double longitude;
    private static double latitude;
    private static PayStatusConfig payStatusConfig = new PayStatusConfig();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        wxApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        wxApi.registerApp(APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                wxApi.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
        KLog.init(true);
        Utils.init(this);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e(" onViewInitFinished is " + arg0);
                isX5Success = arg0;
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        CrashReport.initCrashReport(getApplicationContext(), "9a0aa8d5e6", true);
    }

    public static IWXAPI getWXApi() {
        return wxApi;
    }

    public static String getWXAppId() {
        return APP_ID;
    }

    public static int getUserType() {
        return SPUtils.getInstance().getInt("user_type");
    }

    public static int getStoreAccount() {
        return SPUtils.getInstance().getInt("user_sub_account");
    }

    public static boolean isStore() {
        return SPUtils.getInstance().getInt("user_type") == Constant.AsynchronousStatus.USER_TYPE_STORE;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static String getUserToken() {
        return SPUtils.getInstance().getString("user_token");
    }

    public static String getUserUuid() {
        return SPUtils.getInstance().getString("user_uuid");
    }

    public static boolean isX5Success() {
        return isX5Success;
    }

    public static int getPayStatusType() {
        return payStatusConfig.getPayType();
    }

    public static String getPayOrderUuid() {
        return payStatusConfig.getOrderUuid();
    }

    public static void setPayStatusType(int payType) {
        payStatusConfig.setPayType(payType);
    }

    public static void setPayOrderUuid(String orderUuid) {
        payStatusConfig.setOrderUuid(orderUuid);
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        MyApplication.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        MyApplication.latitude = latitude;
    }
}
