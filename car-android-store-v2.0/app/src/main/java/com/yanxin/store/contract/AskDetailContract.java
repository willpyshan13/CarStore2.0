package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.AskOrderDetailBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.ReplyRushReq;

import java.io.File;

import io.reactivex.Observable;

public interface AskDetailContract {
    abstract class AskDetailPresenter extends BasePresenter<AskDetailModel, AskDetailView> {
        public abstract void queryRushDetail(String orderUuid);

        public abstract void putAskOrderCancel(String uuid);

        public abstract void grabRushOrder(String uuid);

        public abstract void uploadImgFile(File file);

        public abstract void replyRush(ReplyRushReq replyRushReq);

    }

    interface AskDetailModel extends IBaseModel {
        Observable<AskOrderDetailBean> queryRushDetail(String orderUuid);

        Observable<DefaultBean> grabRushOrder(String uuid);

        Observable<DefaultBean> putAskOrderCancel(String uuid);

        Observable<UploadFileBean> uploadImgFile(File file);

        Observable<DefaultBean> replyRush(ReplyRushReq replyRushReq);

    }

    interface AskDetailView extends IBaseActivity {
        void queryAskDetail(AskOrderDetailBean.DataBean dataBean);

        void grabRushOrder(String uuid);

        void putAskOrderCancel(String uuid);

        void uploadImgFile(String path);

        void replyRush(String msg);

    }
}
