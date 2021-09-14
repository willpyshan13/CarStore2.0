package com.yanxin.store.listener;

/***
 *  验证码倒计时的回调
 */
public interface ITimeEndCallBack {
    //更新时间
    void update(int time);
    
    //计时结束
    void exit();
}
