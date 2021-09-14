package com.yanxin.store.mvvm.service

import com.will.habit.http.BaseResponse
import com.yanxin.store.bean.BrandBean
import com.yanxin.store.bean.CityBean
import com.yanxin.store.bean.DepositBankBean
import com.yanxin.store.bean.UploadFileBean
import com.yanxin.store.mvvm.entity.*
import okhttp3.MultipartBody
import retrofit2.http.*
import java.util.ArrayList

/**
 * Desc:服务类
 *
 * Date: 2021-07-25
 * Copyright: Copyright (c) 2018-2020
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
interface MineService {

    @GET("/account/message/messageList")
    suspend fun getMessageList(@Query("type") type:String): BaseResponse<List<RespMessageEntity>>

    @GET("/account/account/account")
    suspend fun getAccountInfo(): BaseResponse<RespAccountInfoEntity>

    @GET("/account/message/updateMessageStatus")
    suspend fun updateMessageStatus(): BaseResponse<String?>

    @GET("/account/message/updateMessageStatusById")
    suspend fun updateMessageStatusById(@Query("id") id:String): BaseResponse<String?>

    @GET("/account/message/deleteMessageById")
    suspend fun deleteMessageStatusById(@Query("id") id:String): BaseResponse<String?>

    @POST("/account/withdrawalparent/withdrawal")
    suspend fun withdrawal(@Body id:ReqWithDrawEntity): BaseResponse<String?>


    @GET("/account/account/classify")
    suspend fun getAccountClassify(): BaseResponse<RespAccountClassifyEntity>

    @POST("/account/user/queryUserPhotoImgUrl")
    suspend fun getAccountHeaderUrl(): BaseResponse<String>

    @POST("/account/banner/queryBannerList")
    suspend fun queryBannerList(@Body access: ReqBannerEntity): BaseResponse<List<RespBannerEntity>>

    @GET("/account/store/queryStoreDetail")
    suspend fun queryStoreDetailByUser(): BaseResponse<RespStoreInfoEntity>

    @PUT("/account/store/updateStore")
    suspend fun updateStore(@Body access: RespStoreInfoEntity): BaseResponse<String>

    @GET("/account/technician/queryTechnicianDetail")
    suspend fun queryTechnicianDetail(): BaseResponse<RespStoreInfoEntity>

    @PUT("/account/technician/updateTechnician")
    suspend fun updateTechnician(@Body access: RespStoreInfoEntity): BaseResponse<String?>

    @POST("/account/login/exitLogin")
    suspend fun getAccountExit(): BaseResponse<String>

    @POST("/order/orderVehicleStatio/queryOrderVehicleStationList")
    suspend fun queryOrderVehicleStationList(@Body data:ReqWorkOrderEntity): BaseResponse<List<RespWorkOrderEntity>>

    @POST("/order/sceneOrder/sceneOrderStatistics")
    suspend fun sceneOrderStatistics(@Body data:ReqOnSiteSupportEntity): BaseResponse<RespOnSiteSupportEntity>

    @POST("/account/profitStream/selectStatisticsIncomeAmount")
    suspend fun statisticsIncomeAmount(@Body data:ReqOnSiteSupportEntity): BaseResponse<RespStatisticsEntity>

    @POST("/account/profitStream/selectStatisticsPayAmount")
    suspend fun statisticsPayAmount(@Body data:ReqOnSiteSupportEntity): BaseResponse<RespStatisticsEntity>

    @POST("/order/orderGoods/queryGoodsGroupAmount")
    suspend fun queryGoodsGroupAmount(@Query("yearMonth") type:String): BaseResponse<RespStoreReportEntity>

    @POST("/order/orderGoods/queryGoodsGroupCount")
    suspend fun queryGoodsGroupCount(@Query("yearMonth") type:String): BaseResponse<RespStoreReportEntity>

    @GET("/manager/dict/queryListByType/person_type")
    suspend fun queryListByTypePersonType(): BaseResponse<List<RespTypeEntity>>

    @GET("/manager/area/queryList")
    suspend fun queryArea(): BaseResponse<List<CityBean.DataBean>>

    @GET("/account/vehicleConfig/queryList/{parentUuid}")
    suspend fun queryBrandList(@Path("parentUuid") parentUuid:String): BaseResponse<ArrayList<BrandBean.DataBean>>

    @GET("/manager/dict/queryListByType/deposit_bank")
    suspend fun queryBankList(): BaseResponse<ArrayList<DepositBankBean.DataBean>>

    @Multipart
    @POST("/utility/file/uploadFile")
    suspend fun uploadFile(@Part part: MultipartBody.Part,@Query("type") type:String): BaseResponse<String>

    @POST("/account/user/updateUserPhotoImg")
    suspend fun updateUserPhotoImg(@Body data:ReqUserHeaderEntity): BaseResponse<String>

}