package com.yanxin.store.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class UIProportionalUtils {

    public static RelativeLayout.LayoutParams adaptationLayout(Activity mActivity, LinearLayout mlayout) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm); //获取手机屏幕的大小
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) mlayout.getLayoutParams();
        linearParams.height = (int) (dm.heightPixels * 0.5);
        return linearParams;
    }
}
