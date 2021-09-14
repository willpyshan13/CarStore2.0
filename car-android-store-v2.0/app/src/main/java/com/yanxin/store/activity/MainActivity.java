package com.yanxin.store.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.FindFragment;
import com.yanxin.store.fragment.MallFragment;
import com.yanxin.store.fragment.ServingFragment;
import com.yanxin.store.mvvm.ui.fragment.MessageFragment;
import com.yanxin.store.mvvm.ui.fragment.MineFragmentKt;
import com.yanxin.store.ui.NoScrollViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_STORE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_FIND_LOCATION;

@XmlLayoutResId(contentId = R.layout.activity_main)
public class MainActivity extends BaseActivity implements AMapLocationListener, EasyPermissions.PermissionCallbacks {
    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private NoScrollViewPager mMainViewPager;
    private RadioGroup mMainGroup;
    private MainFragmentPagerAdapter mPagerAdapter;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private ArrayList<Fragment> mMainFragments;
    private int index;
    private boolean isBack = false;
    private Timer timer = new Timer();

    @Override
    protected void initData() {
        FindFragment mFindFragment = new FindFragment();
        MallFragment mMallFragment = new MallFragment();
        ServingFragment mServingFragment = new ServingFragment();
        MessageFragment mMsgFragment = new MessageFragment();
        MineFragmentKt mMineFragment = new MineFragmentKt();
        mMainFragments.add(mFindFragment);
        mMainFragments.add(mServingFragment);
        if (MyApplication.getUserType() == USER_TYPE_STORE) {
            mMainFragments.add(mMallFragment);
        }
        if (MyApplication.getUserType() != USER_TYPE_STORE) {
            mMainGroup.removeViewAt(2);
        }
        mMainFragments.add(mMsgFragment);
        mMainFragments.add(mMineFragment);
        mPagerAdapter.notifyDataSetChanged();
        int childCount = mMainGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mMainGroup.getChildAt(i);
            int finalCurrentPosition = i;
            childAt.setOnClickListener(view -> mMainViewPager.setCurrentItem(finalCurrentPosition));
        }
        mMainViewPager.setCurrentItem(index);
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (EasyPermissions.hasPermissions(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
            startLocation();
        } else {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this,
                    PER_FIND_LOCATION,
                    permissions).build());
        }

    }

    private void startLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(60000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 2000);
            ToastUtils.showShort("请再按一次退出App");
        }
    }

    @Override
    protected void initView() {
        index = getIntent().getIntExtra("index", 0);
        mMainFragments = new ArrayList<>();
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mMainFragments);
        mMainViewPager = findViewById(R.id.main_vp);
        mMainGroup = findViewById(R.id.main_btn_group);
        mMainViewPager.setAdapter(mPagerAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void setAskTitle(int size) {
        ((FindFragment) mMainFragments.get(0)).setAskTitle(size);
    }

    @SuppressLint("SetTextI18n")
    public void setMineTitle(int size) {
        ((FindFragment) mMainFragments.get(0)).setMineTitle(size);
    }

    @SuppressLint("SetTextI18n")
    public void setDtcBrandValue(String value) {
        ((FindFragment) mMainFragments.get(0)).setDtcBrandValue(value);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                MyApplication.setLatitude(amapLocation.getLatitude());            //获取纬度
                MyApplication.setLongitude(amapLocation.getLongitude());            //获取经度
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.showShort("请开启定位权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.onDestroy();
    }
}