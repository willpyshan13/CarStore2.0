package com.yanxin.store.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yanxin.store.listener.ITimeEndCallBack;


/***
 * 验证码点击之后倒计时
 */
public class TimeVerification {
    private static TimeVerification timeVerification;
    private ITimeEndCallBack mITimeEndCallBack;
    public Runnable mRunnable;

    private TimeVerification() {
    }

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != 0) {
                mITimeEndCallBack.update(msg.what);
                mHandler.postDelayed(mRunnable, 1000);
            } else {
                mITimeEndCallBack.exit();
                timeTemp = 60;
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    public TimeVerification setOnTimeEndCallBack(ITimeEndCallBack iTimeEndCallBack) {
        this.mITimeEndCallBack = iTimeEndCallBack;
        return this;
    }

    /**
     * 重置计时器
     */
    public void resume() {
        if (mITimeEndCallBack != null) {
            mITimeEndCallBack.exit();
            timeTemp = 60;
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 获取计时器实例
     *
     * @return
     */
    public static TimeVerification getInstence() {
        if (timeVerification == null) {
            synchronized (TimeVerification.class) {
                if (timeVerification == null) {
                    timeVerification = new TimeVerification();
                }
            }
        }
        return timeVerification;
    }

    int timeTemp = 60;//默认60s
    private int mDiminished = 1;

    /**
     * 设置时间
     *
     * @param temp
     * @return
     */
    public TimeVerification setTimeTemp(int temp) {
        this.timeTemp = temp;
        return this;
    }

    /**
     * 设置每次递增或者减少的值
     *
     * @param shed
     */
    public TimeVerification setTimeDiminished(int shed) {
        this.mDiminished = shed;
        return this;
    }

    /**
     * 第一次更新时间
     */
    public void updateTime() {
        resume();
        mRunnable = () -> {
            timeTemp -= mDiminished;
            mHandler.sendEmptyMessageAtTime(timeTemp, 1000);
        };
        mHandler.post(mRunnable);
    }

    /**
     * 销毁计时器
     */
    public void onExit() {
        if (mHandler != null && timeVerification != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
            mRunnable = null;
            timeVerification = null;
            timeTemp = 60;//默认60s
            mDiminished = 1;
        }
    }
}
