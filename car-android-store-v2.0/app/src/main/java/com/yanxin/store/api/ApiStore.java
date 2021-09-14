package com.yanxin.store.api;

import com.yanxin.store.bean.AmountBean;
import com.yanxin.store.bean.AskOrderDetailBean;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.BuyCaseBean;
import com.yanxin.store.bean.CaseDetailBean;
import com.yanxin.store.bean.CaseOrderDetailBean;
import com.yanxin.store.bean.CaseVehicleBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.DTCOrderBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.bean.DrivingBean;
import com.yanxin.store.bean.DtcDetailBean;
import com.yanxin.store.bean.DtcOrderDetailBean;
import com.yanxin.store.bean.DtcTypeBean;
import com.yanxin.store.bean.EngineDisplacementBean;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.GoodsSubstanceBean;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.bean.GroupOrderBean;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.bean.MyDtcNumberBean;
import com.yanxin.store.bean.MyOrderBean;
import com.yanxin.store.bean.MyProblemBean;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.OrderInfoBean;
import com.yanxin.store.bean.PayOrderBean;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.bean.RegisterBean;
import com.yanxin.store.bean.ReplyOrderBean;
import com.yanxin.store.bean.RushAnswerBean;
import com.yanxin.store.bean.RushDetailBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.bean.SGrabContentBean;
import com.yanxin.store.bean.SceneInfoBean;
import com.yanxin.store.bean.SiteFeeBean;
import com.yanxin.store.bean.SquareBean;
import com.yanxin.store.bean.StoreDetailBean;
import com.yanxin.store.bean.SuperchargingBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.bean.TechnicianAnswerBean;
import com.yanxin.store.bean.TechnicianDetailBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.TransmissionBean;
import com.yanxin.store.bean.TransmissonChildBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.bean.UserLoginBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.commont.HttpUrl;
import com.yanxin.store.req.AddDTCReq;
import com.yanxin.store.req.AddSceneOrderServiceReq;
import com.yanxin.store.req.AddSiteReq;
import com.yanxin.store.req.AskExpertReq;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.CaseQueryVehicleReq;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.GroupBuyReq;
import com.yanxin.store.req.InDoorSceneOrderReq;
import com.yanxin.store.req.MyDtcReq;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.req.PayAuditReq;
import com.yanxin.store.req.PayOrderReq;
import com.yanxin.store.req.PushCaseReq;
import com.yanxin.store.req.ReplyOrderReq;
import com.yanxin.store.req.ReplyRushReq;
import com.yanxin.store.req.SGrabContentReq;
import com.yanxin.store.req.SceneOrderConfirmReq;
import com.yanxin.store.req.StoreRegisterReq;
import com.yanxin.store.req.TechnicianAnswerReq;
import com.yanxin.store.req.TechnicianRegisterReq;
import com.yanxin.store.req.UserLoginReq;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit 接口提供
 */
public interface ApiStore {

    @POST(HttpUrl.USER_LOGIN)
    Observable<UserLoginBean> userLogin(@Body UserLoginReq uerLoginReq);

    @GET(HttpUrl.GET_VERIFICATION_CODE)
    Observable<DefaultBean> getVerificationCode(@Path("accountName") String accountName);

    @GET(HttpUrl.GET_DEPOSIT_BANK)
    Observable<DepositBankBean> getDepositBank();

    @GET(HttpUrl.GET_PERSON_TYPE)
    Observable<PersonTypeBean> getPersonType();

    @Multipart
    @POST(HttpUrl.UPLOAD_FILE)
    Observable<UploadFileBean> uploadFile(@Part MultipartBody.Part body, @Query("type") String type);

    @GET(HttpUrl.QUERY_CITY)
    Observable<CityBean> queryCity();

    @POST(HttpUrl.QUERY_CASE_VEHICLE)
    Observable<CaseVehicleBean> queryCaseVehicle(@Header("token") String token, @Body CaseQueryVehicleReq caseQueryVehicleReq);

    @GET(HttpUrl.QUERY_VEHICLE_CONFIG_LIST)
    Observable<BrandBean> queryBrand(@Path("parentUuid") String parentUuid);

    @GET(HttpUrl.QUERY_VEHICLE_LIST_NEXT_CONFIG_LIST)
    Observable<BrandBean> queryListNextModel(@Path("parentUuid") String parentUuid);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<DictBean> querySystem(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<EngineDisplacementBean> queryEngineDisplacement(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<DrivingBean> queryDriving(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<TransmissionBean> queryTransmission(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<SuperchargingBean> querySupercharging(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<TechnicianTypeBean> queryTechnicianTypeBean(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<TransmissonChildBean> queryManualTransmission(@Path("type") String type);

    @POST(HttpUrl.QUERY_DTC_LIST)
    Observable<DTCBean> queryDtc(@Header("token") String token, @Body DTCReq dtcReq);

    @POST(HttpUrl.CREATE_DTC_ORDER)
    Observable<DefaultBean> createDtcOrder(@Header("token") String token, @Body CreateDTCReq createDTCReq);

    @POST(HttpUrl.ADD_DTC_INFO)
    Observable<DefaultBean> addDtcInfo(@Header("token") String token, @Body AddDTCReq addDTCReq);

    @POST(HttpUrl.QUERY_MY_DTC)
    Observable<MyDtcBean> queryMyDtc(@Header("token") String token, @Body MyDtcReq myDtcReq);

    @GET(HttpUrl.QUERY_DTC_DETAIL)
    Observable<DtcDetailBean> queryDtcDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.REMINDER_ORDERS)
    Observable<DefaultBean> reminderOrders(@Header("token") String token, @Path("uuid") String uuid);


    @GET(HttpUrl.QUERY_DTC_ORDER_DETAIL)
    Observable<DtcOrderDetailBean> queryDtcOrderDetail(@Header("token") String token, @Path("uuid") String uuid);

    @POST(HttpUrl.QUERY_READER_DTC_COUNT)
    Observable<MyDtcNumberBean> queryDtcCount(@Header("token") String token);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<DtcTypeBean> queryDtcType(@Path("type") String dtc_type);

    @POST(HttpUrl.REGISTER_STORE)
    Observable<RegisterBean> registerStore(@Body StoreRegisterReq storeRegisterReq);

    @PUT(HttpUrl.UPDATE_STORE)
    Observable<RegisterBean> updateStore(@Header("token") String token, @Body StoreRegisterReq storeRegisterReq);

    @POST(HttpUrl.REGISTER_TECHNICIAN)
    Observable<RegisterBean> registerTechnician(@Body TechnicianRegisterReq technicianRegisterReq);

    @PUT(HttpUrl.UPDATE_TECHNICIAN)
    Observable<RegisterBean> updateTechnician(@Header("token") String token,@Body TechnicianRegisterReq technicianRegisterReq);

    @GET(HttpUrl.QUERY_TECHNICIAN_DETAIL)
    Observable<TechnicianDetailBean> queryTechnicianDetail(@Header("token") String token);

    @POST(HttpUrl.QUERY_SCENE_ORDER_LIST)
    Observable<SupportOrderBean> querySceneOrderList(@Header("token") String token, @Body GrabOrderReq sceneOrderReq);

    @POST(HttpUrl.QUERY_ANSWER_ALL)
    Observable<SquareBean> queryAnswerAll(@Header("token") String token, @Body DefaultReq defaultReq);

    @POST(HttpUrl.QUERY_RUSH_ANSWER_ALL)
    Observable<RushAnswerBean> queryRushAnswerAll(@Header("token") String token, @Body DefaultReq defaultReq);

    @POST(HttpUrl.QUERY_TECHNICIAN_ANSWER_LIST)
    Observable<TechnicianAnswerBean> queryTechnicianAnswerList(@Header("token") String token, @Body TechnicianAnswerReq technicianAnswerReq);

    @GET(HttpUrl.GET_STORE_DETAIL)
    Observable<StoreDetailBean> getStoreDetail(@Header("token") String token);

    @POST(HttpUrl.CONSULT_ORDER)
    Observable<DefaultBean> consultOrder(@Header("token") String token, @Body AskExpertReq askExpertReq);

    @GET(HttpUrl.QUERY_ORDER_INFO)
    Observable<OrderInfoBean> queryOrderInfo(@Header("token") String token, @Path("uuid") String uuid);

    @POST(HttpUrl.PAY_ORDER)
    Observable<PayOrderBean> payOrder(@Header("token") String token, @Body PayOrderReq payOrderReq);

    @POST(HttpUrl.ADD_CASE)
    Observable<DefaultBean> addCase(@Header("token") String token, @Body PushCaseReq pushCaseReq);

    @POST(HttpUrl.ADD_SCENE_ORDER)
    Observable<DefaultBean> addSiteOrder(@Header("token") String token, @Body AddSiteReq siteReq);

    @POST(HttpUrl.QUERY_MINE_CASE)
    Observable<MineCaseBean> queryMineCase(@Header("token") String token, @Body DefaultReq defaultReq);

    @POST(HttpUrl.QUERY_BUY_CASE)
    Observable<BuyCaseBean> queryBuyCase(@Header("token") String token, @Body DefaultReq defaultReq);

    @POST(HttpUrl.ADD_MALL_GOODS)
    Observable<DefaultBean> addMallGoods(@Header("token") String token, @Body CreateMallReq createMallReq);

    @PUT(HttpUrl.UPDATE_MALL_GOODS)
    Observable<GoodsDetailBean> updateMallGoods(@Header("token") String token, @Body CreateMallReq createMallReq);

    @GET(HttpUrl.QUERY_GOODS_DETAIL)
    Observable<GoodsDetailBean> queryGoodsDetail(@Header("token") String token, @Path("uuid") String uuid);

    @PUT(HttpUrl.UPDATE_MALL_GOODS_STATUS)
    Observable<GoodsDetailBean> updateMallGoodsStatus(@Header("token") String token, @Body CreateMallReq createMallReq);

    @POST(HttpUrl.QUERY_CONSULT_LIST)
    Observable<AuditBean> queryConsultList(@Header("token") String token, @Body AuditReq auditReq);

    @POST(HttpUrl.QUERY_ORDER_FRONT_CONSULT_LIST)
    Observable<MyRushBean> queryOrderFrontConsultList(@Header("token") String token, @Body MyRushReq myRushReq);

    @POST(HttpUrl.QUERY_SQ_ORDER_LIST)
    Observable<MyRushBean> querySqOrderList(@Header("token") String token, @Body MyRushReq myRushReq);

    @GET(HttpUrl.QUERY_MY_QUESTION)
    Observable<MyProblemBean> queryMyQuestion(@Header("token") String token, @Path("questionType") int questionType);

    @POST(HttpUrl.QUERY_ORDER_CONSULT_LIST)
    Observable<ReplyOrderBean> queryOrderConsultList(@Header("token") String token, @Body ReplyOrderReq replyOrderReq);

    @GET(HttpUrl.QUERY_CASE_DETAIL)
    Observable<CaseDetailBean> queryCaseDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.QUERY_CASE_MY_DETAIL)
    Observable<CaseOrderDetailBean> queryCaseOrderDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.QUERY_RUSH_ORDER_DETAIL)
    Observable<RushOrderDetailBean> queryRushOrderDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.QUERY_ASK_ORDER_DETAIL)
    Observable<AskOrderDetailBean> queryAskOrderDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.QUERY_RUSH_DETAIL)
    Observable<RushDetailBean> queryRushDetail(@Header("token") String token, @Path("uuid") String uuid);

    @GET(HttpUrl.QUERY_ORDER_SNAPUP)
    Observable<DefaultBean> grabRushOrder(@Header("token") String token, @Path("uuid") String uuid);

    @PUT(HttpUrl.REPLY_RUSH)
    Observable<DefaultBean> replyRush(@Header("token") String token, @Body ReplyRushReq replyRushReq);

    @GET(HttpUrl.BUY_CASE)
    Observable<DefaultBean> buyCase(@Header("token") String token, @Path("caseUuid") String caseUuid);

    @GET(HttpUrl.QUERY_BASE_FEE)
    Observable<SiteFeeBean> queryBaseFee(@Path("uuid") String uuid);

    @POST(HttpUrl.QUERY_GOODS_LIST)
    Observable<GoodsBean> queryGoodsList(@Header("token") String token, @Body GoodsReq goodsReq);

    @POST(HttpUrl.QUERY_GROUP_BUY_LIST)
    Observable<GroupOrderBean> queryGroupOrderList(@Header("token") String token, @Body GroupBuyReq groupBuyReq);

    @POST(HttpUrl.QUERY_GROUP_CREATE_LIST)
    Observable<GroupCreateBean> queryAllGroupMall(@Header("token") String token, @Body GroupBuyReq groupBuyReq);

    @POST(HttpUrl.QUERY_GOODS_LIST)
    Observable<GoodsBean> queryGoodsOrderList(@Header("token") String token, @Body GoodsReq goodsReq);

    @POST(HttpUrl.QUERY_GOODS_LIST)
    Observable<GroupCreateBean> queryGroupBuyOrderList(@Header("token") String token, @Body GroupBuyReq groupBuyReq);

    @GET(HttpUrl.QUERY_GOODS_SUBSTANCE_TYPE)
    Observable<GoodsSubstanceBean> queryGoodsSubstanceType(@Header("token") String token, @Path("parentUuid") String parentUuid);

    @GET(HttpUrl.QUERY_SCENE_ORDER_INFO)
    Observable<SceneInfoBean> querySceneOrderInfo(@Header("token") String token, @Path("uuid") String uuid);

    @POST(HttpUrl.GET_MYORDER_CASE)
    Observable<MyOrderBean> getMyorderCase(@Header("token") String token);

    @GET(HttpUrl.GRAB_BING_ORDERS)
    Observable<DefaultBean> grabBingOrders(@Header("token") String token, @Path("sceneOrderUuid") String sceneOrderUuid);

    @GET(HttpUrl.GRAB_CANCEL_ORDERS)
    Observable<DefaultBean> grabCancelOrders(@Header("token") String token, @Path("sceneOrderUuid") String sceneOrderUuid, @Query("type") int type);

    @POST(HttpUrl.SCENE_ORDER_CONFIRM)
    Observable<DefaultBean> sceneOrderConfirm(@Header("token") String token, @Body SceneOrderConfirmReq sceneOrderConfirmReq);

//    @POST(HttpUrl.SCENE_SUBMIT_PLAN)
//    Observable<DefaultBean> sceneSubmitPlan(@Header("token") String token, @Body AddSceneOrderServiceReq addSceneOrderServiceReq);

    @POST(HttpUrl.SCENE_SUBMIT_PLAN_TWO)
    Observable<DefaultBean> sceneSubmitPlan(@Header("token") String token, @Body AddSceneOrderServiceReq addSceneOrderServiceReq);

    @POST(HttpUrl.SCENE_ORDER_DESCRIBE)
    Observable<DefaultBean> sceneOrderDescribe(@Header("token") String token, @Body InDoorSceneOrderReq inDoorSceneOrderReq);

    @POST(HttpUrl.QUERY_ORDER_VEHICLE_STATION_LIST)
    Observable<SGrabContentBean> queryOrderVehicleStationList(@Header("token") String token, @Body SGrabContentReq sGrabContentReq);

    @PUT(HttpUrl.PUT_ASK_ORDER_CANCEL)
    Observable<DefaultBean> putAskOrderCancel(@Header("token") String token, @Query("uuid") String uuid);

    @Streaming
    @GET()
    Observable<ResponseBody> downPdfFile(@Url String url);

    @POST(HttpUrl.ADD_AUDITOR_ORDER)
    Observable<DefaultBean> addAuditorOrder(@Header("token") String token, @Body PayAuditReq payAuditReq);

    @POST(HttpUrl.QUERY_MY_BUY_ALL_DTC)
    Observable<DTCOrderBean> queryBuyDTCList(@Header("token") String token);


//    @PUT(HttpUrl.PAY_AUDITOR_ORDER)
//    Observable<DefaultBean> payAuditorOrder(@Header("token") String token, @Body PayAuditReq payAuditReq);

    /**
     * ==========================================================================================================================
     * ==========================================================================================================================
     */
    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryBasicInspectCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryLineInspectCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> querySheetMetalRepairCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryDiagnosisInstrumentUseCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryPaintRepairCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryOtherCost(@Path("type") String type);

    @GET(HttpUrl.QUERY_DICT_LIST_BY_TYPE)
    Observable<AmountBean> queryOrderServiceCost(@Path("type") String type);

}
