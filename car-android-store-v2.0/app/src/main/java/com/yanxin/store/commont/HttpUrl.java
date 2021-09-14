package com.yanxin.store.commont;

import com.yanxin.store.bean.CaseDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface HttpUrl {
    /**
     * 用户登录
     */
    String USER_LOGIN = "account/login/userLogin";

    /**
     * 获取验证码
     */
    String GET_VERIFICATION_CODE = "account/login/getLoginCode/{accountName}/merchant";

    /**
     * 获取所有开户行
     */
    String GET_DEPOSIT_BANK = "manager/dict/queryListByType/deposit_bank";

    /**
     * 获取人员类型
     */
    String GET_PERSON_TYPE = "manager/dict/queryListByType/person_type";

    /**
     * 上传文件
     */
    String UPLOAD_FILE = "utility/file/uploadFile";

    /**
     * 获取城市信息
     */
    String QUERY_CITY = "manager/area/queryList";

    /**
     * 获取基础上门费
     */
    String QUERY_BASE_FEE = "manager/dict/queryByUuid/{uuid}";

    /**
     * 查询技师案例
     */
    String QUERY_CASE_VEHICLE = "order/case/queryCaseForVehicleList";

    /**
     * 字典表，根据type查询数据，type一般是固定值，全局可用接口
     */
    String QUERY_DICT_LIST_BY_TYPE = "manager/dict/queryListByType/{type}";

    /**
     * 查询父节点下车辆子节点，根节点传-1
     */
    String QUERY_VEHICLE_CONFIG_LIST = "account/vehicleConfig/queryList/{parentUuid}";


    /**
     * 查询父节点下车辆子节点的子车系  和上个接口{QUERY_VEHICLE_CONFIG_LIST}关联
     */
    String QUERY_VEHICLE_LIST_NEXT_CONFIG_LIST = "account/vehicleConfig/queryListNextByParent/{parentUuid}";


    /**
     * 查询DTC故障列表
     */
    String QUERY_DTC_LIST = "order/dtc/list";

    /**
     * 创建DTC订单
     */
    String CREATE_DTC_ORDER = "order/dtcOrder/addOrder";

    /**
     * 补录DTC
     */
    String ADD_DTC_INFO = "order/dtc/add";

    /**
     * 查询我的DTC
     */
    String QUERY_MY_DTC = "order/dtcOrder/myList";

    /**
     * 查询我的DTC次数
     */
    String QUERY_READER_DTC_COUNT = "order/dtcOrder/getNumber";

    /**
     * 注册店铺
     */
    String REGISTER_STORE = "account/store/addStore";

    /**
     * 更新店铺信息
     */
    String UPDATE_STORE = "account/store/updateStore";


    /**
     * 知识问答。广场 (已弃用
     */
    String QUERY_ANSWER_ALL = "order/answer/answerList";

    /**
     * 知识问答。可抢答列表
     */
    String QUERY_RUSH_ANSWER_ALL = "order/answer/preAnswerListTwo";

    /**
     * 查询专家技师列表
     */
    String QUERY_TECHNICIAN_ANSWER_LIST = "account/technician/queryTechnicianAnswerList";

    /**
     * 查询店铺详情
     */
    String GET_STORE_DETAIL = "account/store/queryStoreDetail";

    /**
     * 发布一个问题让大家/专家回答
     */
    String CONSULT_ORDER = "order/orderConsult/consult";

    /**
     * 获取一个订单信息
     */
    String QUERY_ORDER_INFO = "order/orderFront/queryOrderInfo/{uuid}";

    /**
     * 获取订单信息用来提交支付信息
     */
    String PAY_ORDER = "order/pay/payOrder";

    /**
     * 发布案例
     */
    String ADD_CASE = "order/case/addCase";


    /**
     * 查询我购买的案例
     */

    String QUERY_BUY_CASE = "order/orderCaseFront/queryOrderCaseList";


    /**
     * 向我提问  -- 拒绝接单
     */

    String PUT_ASK_ORDER_CANCEL = "order/orderConsult/updateAnswerSts";

    /**
     * 查询我发布的案例
     */
    String QUERY_MINE_CASE = "order/case/queryCaseForTechnicianList";

    /**
     * 注册技师
     */
    String REGISTER_TECHNICIAN = "account/technician/register";


    /**
     * 修改技师
     */
    String UPDATE_TECHNICIAN = "account/technician/updateTechnician";

    /**
     * 查询技师详情
     */
    String QUERY_TECHNICIAN_DETAIL = "account/technician/queryTechnicianDetail";

    /**
     * 抢单接口。
     */
    String QUERY_ORDER_SNAPUP = "order/orderConsult/consultOrderSnapUp/{uuid}";

    /**
     * 回答咨询问答接口
     */
    String REPLY_RUSH = "order/orderConsult/updateConsultAnswer";

    /**
     * 查询现场订单列表
     * 服务页面：现场可下单
     */
    String QUERY_SCENE_ORDER_LIST = "order/sceneOrder/querySceneOrderList";

    /**
     * 新增现场下单
     */
    String ADD_SCENE_ORDER = "order/sceneOrder/addSceneOrder";

    /**
     * 可旁听列表
     * consultType == 0 查询可旁听列表
     * consultType == 1 查询当前用户发起的咨询
     * consultType == 4 查询当前用户旁听的咨询
     */
    String QUERY_CONSULT_LIST = "order/orderConsult/queryConsultList";

    /**
     * 创建商品
     */
    String ADD_MALL_GOODS = "account/goods/addGoods";


    /**
     * 修改商品
     */
    String UPDATE_MALL_GOODS_STATUS = "account/goods/updateGoodsSimplified";


    /**
     * 查询商品详情
     */
    String QUERY_GOODS_DETAIL = "account/goods/queryGoodsDetail/{uuid}";

    /**
     * 修改商品
     */
    String UPDATE_MALL_GOODS = "account/goods/updateGoods";


    /**
     * 知识问答, 我回答的问题
     */
    String QUERY_ORDER_CONSULT_LIST = "order/orderConsult/queryOrderConsultList";


    /**
     * 查询知识问答专区 quizzer 向我提问---1/我提问题的----2/已抢答---3 ,
     */
    String QUERY_ORDER_FRONT_CONSULT_LIST = "order/orderConsultFront/queryOrderConsultList";

    /**
     * 我的页面  我提问的订单列表
     */
    String QUERY_SQ_ORDER_LIST = "order/orderConsultFront/queryOrderConsultListByUuid";

    /**
     * 我的页面内容订单
     */
    String QUERY_MY_QUESTION = "order/orderConsult/getMyQuestion/{questionType}";

    /**
     * 查询案例详情
     */
    String QUERY_CASE_DETAIL = "order/case/queryCaseDetail/{uuid}";

    /**
     * 查询我购买的案例订单详情
     */
    String QUERY_CASE_MY_DETAIL = "order/orderCase/queryOrderCaseDetail/{uuid}";

    /**
     * 查询DTC详情
     */
    String QUERY_DTC_DETAIL = "order/dtc/getById/{uuid}";

    /**
     * 现场下单催单
     */
    String REMINDER_ORDERS = "order/sceneOrder/reminderOrders/{uuid}";

    /**
     * 查询DTC订单详情
     */
    String QUERY_DTC_ORDER_DETAIL = "order/dtcOrder/getById/{uuid}";

    /**
     * 查询抢答详情
     */
    String QUERY_RUSH_ORDER_DETAIL = "order/orderConsult/queryOrderConsultDetail/{uuid}";


    /**
     * 查询抢答订单详情
     */
    String QUERY_ASK_ORDER_DETAIL = "order/orderConsultFront/queryOrderConsultDetail/{uuid}";

    /**
     * 查询抢答详情
     */
    String QUERY_RUSH_DETAIL = "order/orderConsult/queryConsultDetail/{uuid}";


    /**
     * 购买案例
     */
    String BUY_CASE = "order/orderCaseFront/orderCaseTwo/{caseUuid}";

    /**
     * 获取我的商品列表
     */
    String QUERY_GOODS_LIST = "account/goods/queryGoodsList";

    /**
     * 获取我的拼团订单列表
     */
    String QUERY_GROUP_BUY_LIST = "order/orderGroupbuy/queryOrderGroupbuyList";

    /**
     * 获取我的商品订单列表
     */
    String QUERY_GOODS_ORDER_LIST = "order/orderGoods/queryOrderGoodsList";

    /**
     * 获取创建拼团的拼团列表
     */
    String QUERY_GROUP_CREATE_LIST = "account/groupbuy/queryGroupbuyList";

    /**
     * 服务页面,紧急工位列表获取
     */
    String QUERY_ORDER_VEHICLE_STATION_LIST = "order/orderVehicleStatio/queryOrderVehicleStationList";


    /**
     * 查询创建商品时候的一级分类和二级分类, 父节点-1  子节点由父节点的uuid
     */
    String QUERY_GOODS_SUBSTANCE_TYPE = "account/goodsParent/queryListByParent/{parentUuid}";


    /**
     * 查询现场支持下单的详情
     */
    String QUERY_SCENE_ORDER_INFO = "order/sceneOrder/querySceneOrderInfo/{uuid}";

    /**
     * 查看购买我的案例
     */
    String GET_MYORDER_CASE = "order/orderCase/getMyorderCase";


    /**
     * 现场支持抢单接口
     */
    String GRAB_BING_ORDERS = "order/sceneOrder/grabbingOrders/{sceneOrderUuid}";

    /**
     * 现场支持取消订单接口
     */
    String GRAB_CANCEL_ORDERS = "order/sceneOrder/cancelOrder/{sceneOrderUuid}";

    /**
     * 现场订单发起者确认订单  1:抢单用户上门   2:抢单用户完成维修
     */
    String SCENE_ORDER_CONFIRM = "order/sceneOrder/sceneOrderConfirm";

    /**
     * 提交维修方案
     */
    String SCENE_SUBMIT_PLAN = "order/sceneOrder/sceneSubmitPlan";


    /**
     * 提交维修方案  /新版
     */
    String SCENE_SUBMIT_PLAN_TWO = "order/sceneOrder/sceneSubmitPlanTwo";

    /**
     * 抢单用户发起我已到达/完成服务接口
     */
    String SCENE_ORDER_DESCRIBE = "order/sceneOrder/sceneOrderDescribe";

    /**
     * 新增旁听订单
     */
    String ADD_AUDITOR_ORDER = "order/orderConsult/addAuditorOrderTwo";

    /**
     * 旁听订单支付
     */
    String PAY_AUDITOR_ORDER = "order/orderConsult/payAuditorOrder";


    /**
     * 查询购买的DTC(所有)
     */
    String QUERY_MY_BUY_ALL_DTC = "order/dtc/dtcOrderList";


}
