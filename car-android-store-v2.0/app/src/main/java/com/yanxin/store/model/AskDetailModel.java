package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.AskOrderDetailBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.AskDetailContract;
import com.yanxin.store.req.ReplyRushReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AskDetailModel extends BaseModel implements AskDetailContract.AskDetailModel {
    public static AskDetailModel getInstance() {
        return new AskDetailModel();
    }

    @Override
    public Observable<AskOrderDetailBean> queryRushDetail(String orderUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryAskOrderDetail(MyApplication.getUserToken(), orderUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<DefaultBean> grabRushOrder(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .grabRushOrder(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(defaultBean -> defaultBean);
    }

    @Override
    public Observable<DefaultBean> putAskOrderCancel(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .putAskOrderCancel(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(defaultBean -> defaultBean);
    }

    @Override
    public Observable<UploadFileBean> uploadImgFile(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .uploadFile(part, "other")
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }

    @Override
    public Observable<DefaultBean> replyRush(ReplyRushReq replyRushReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .replyRush(MyApplication.getUserToken(), replyRushReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(defaultBean -> defaultBean);
    }

}
