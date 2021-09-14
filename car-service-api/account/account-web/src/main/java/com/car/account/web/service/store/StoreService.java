package com.car.account.web.service.store;

import com.car.account.client.request.store.*;
import com.car.account.client.response.comment.CommentStaticsRes;
import com.car.account.client.response.goods.ext.StoreGoodsClassifyRes;
import com.car.account.client.response.store.*;
import com.car.account.web.model.store.Store;
import com.car.account.web.model.store.StoreServiceRates;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/19
 */
public interface StoreService {

    /**
     * 添加店铺
     * @param addStoreReq
     * @return
     */
    ResultRes<String> addStore(AddStoreReq addStoreReq);

    /**
     * 删除店铺
     * @param uuid
     * @return
     */
    ResultRes<String> deleteStore(String uuid);

    /**
     * 修改店铺
     * @param updateStoreReq
     * @return
     */
    ResultRes<String> updateStore(UpdateStoreReq updateStoreReq);

    /**
     * 修改店铺账户信息
     * @param storeAccountReq
     * @returns
     */
    ResultRes<String> updateStoreAccount(StoreAccountReq storeAccountReq);


    /**
     * 查询店铺列表
     * @param param
     * @return
     */
    PageRes<List<QueryStoreListRes>> queryStoreList(QueryStoreListReq param);

    /**
     * 查询店铺详情
     * @param uuid
     * @return
     */
    ResultRes<StoreDetailRes> queryStoreDetail(String uuid);

    /**
     * 根据token查询店铺详情
     * @param
     * @return
     */
    ResultRes<StoreDetailRes> queryStoreDetail();

    /**
     * 店铺信息导出
     * @param exportReq
     * @param response
     */
    void exportStoreList(QueryStoreListReq exportReq, HttpServletResponse response);

    /**
     * 查询店铺服务种类
     * @param storeUuid
     * @return
     */
    List<String> queryStoreServices(String storeUuid);

    /**
     * 店铺评分统计
     * @param storeUuid
     * @return
     */
    CommentStaticsRes queryStoreCommentStatics(String storeUuid);
    /**
     * 根据登录人获取店铺信息
     * @return
     */
    Store getStore();
    /**
     * 根据店铺联系人获取店铺信息
     * @return
     */
    Store getStore(String userUuid);
    /**
     * 查询店铺商品分类及所有商品列表
     * @param storeUuid
     * @return
     */
    List<StoreGoodsClassifyRes> queryStoreGoodsClassifyRes(String storeUuid);

    /**
     * 查询店铺账户信息
     * @return
     */
    ResultRes<StoreAccountRes> queryStoreAccount();

    /**
     * 根据店铺联系人uuid查询店铺联系人详情
     * @param storeUserUuid
     * @return
     */
    ResultRes<StoreUserRes> queryStoreUserInfo(String storeUserUuid);

    /**
     * 查询共享店铺列表
     * @return
     */
    ResultRes<List<QueryShareStoreListRes>> queryShareStoreList(QueryShareStoreListReq param);

    /**
     * 返回注册店铺信息:前端需求
     */
    ResultRes<HashMap> getStoreInfo();

    /**
     * 商品分类权限与平台服务费比例设置列表
     * @param storeUuid
     * @return
     */
    ResultRes<List<StoreServiceRatesRes>> storeServiceChargeList(String storeUuid);

    /**
     * 商品分类权限与平台服务费比例设置添加
     * @param
     * @return
     */
    void insertStoreServiceCharge(List<StoreServiceRatesReq> storeServiceRatesReq);

    /**
     * 商品分类权限与平台服务费比例设置修改
     * @param
     * @return
     */
    void updateStoreServiceCharge(List<StoreServiceRatesRes> storeServiceRatesReq);

    /**
     * 根据用户ID获取店铺主账号
     * @param storeUuid
     * @return
     */
    StoreUserRes getStoreUset(String storeUuid);

    /**
     * 修改店铺评分---内部调用
     * @param uuid
     * @param score
     * @return
     */
    ResultRes<String> updateScore( String uuid,  BigDecimal score);

    /**
     * 查询店铺关联品牌
     * @param uuid
     * @return
     */
    ResultRes<List<String>> queryStoreBrand(@PathVariable("uuid") String uuid);
}
