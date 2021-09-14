package com.yanxin.store.req;

/**
 * @Description: 商城tab页请求数据bean
 * @Author: fengzeyuan
 * @Date: 2021/7/23 7:18 下午
 */
public class MallTabReq {
    private int mTabType;
    private int mPagerNum= 1;
    private int mPagerSize= 20;

    public int getPagerNum() {
        return mPagerNum;
    }

    public int getPagerSize() {
        return mPagerSize;
    }

    public MallTabReq(String tabTypeStr) {
        mTabType = getTabTypeByStr(tabTypeStr);
    }


    public void setPageNum(int pagerNum) {
        mPagerNum = pagerNum;
    }

    public void setPageSize(int pagerSize) {

        mPagerSize = pagerSize;
    }

    private int getTabTypeByStr(String tabTypeStr) {
        switch (tabTypeStr) {
            case "商品":
                return 1;
            case "拼团活动":
                return 2;
            case "订单":
                return 3;
        }
        return 0;
    }

    public int getTabType() {
        return mTabType;
    }
}
