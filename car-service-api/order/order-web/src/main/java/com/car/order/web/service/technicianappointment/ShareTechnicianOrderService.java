package com.car.order.web.service.technicianappointment;

import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;
import com.car.order.client.request.technicianappointment.*;
import com.car.order.client.response.technicianappointment.ShareTechnicianOrderInfoRes;
import com.car.order.client.response.technicianappointment.ShareTechnicianOrderRes;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.web.service
 * @NAME: TechnicianAppointmentOrderService
 * @DATE: 2021/3/4 21:01
 */
public interface ShareTechnicianOrderService {

    /**
     * 新增预约技师订单信息
     * @param req
     * @return
     */
    ResultRes<String> saveTechnicianAppointment(ShareTechnicianOrderReq req);

    /**
     * 查询预约技师订单列表
     * @return
     */
    PageRes<List<ShareTechnicianOrderRes>> queryShareTechnicianOrderList(QueryShareTechnicianOrderReq req);


    /**
     * 完成预约技师订单信息
     * @param uuid
     * @return
     */
    ResultRes updateShareTechnicianOrder(String uuid);

    /**
     * 申请退款预约技师订单
     * @param uuid
     * @return
     */
    ResultRes applicationRefundShareTechnicianOrder(String uuid);


    /**
     * 提交说明
     * @param req
     * @return
     */
    ResultRes<String> shareOrderDescribe(ShareTechnicianDetailReq req);

    /**
     * 提交说明
     * @param req
     * @return
     */
    ResultRes<String> shareOrderConfirm(ShareOrderConfirmReq req);

    /**
     * 提交服务方案订单
     * @param addShareOrderServiceReq
     * @return
     */
    ResultRes<String> sceneSubmitPlan (AddShareOrderServiceReq addShareOrderServiceReq);

    /**
     * 预约技师订单统计
     * @param sceneOrderStatisticsReq
     * @return
     */
    ResultRes shareTechnicianOrderStatistics( SceneOrderStatisticsReq sceneOrderStatisticsReq);

    /**
     * 查询订单详情
     * @param uuid
     * @return
     */
    ResultRes<ShareTechnicianOrderInfoRes> queryShareTechnicianOrder(String uuid);

    /**
     * 技师同意接单
     * @param uuid
     * @return
     */
    ResultRes receiveShareTechnicianOrder(String uuid);

    /**
     * 技师拒绝接单
     * @param uuid
     * @return
     */
    ResultRes refuseOrder(String uuid);
    /**
     * 客户取消订单
     * @param uuid
     * @return
     */
    ResultRes cancelOrder(String uuid,Integer type);

    /**
     * 技师统计订单数量
     * @param uuid
     * @return
     */
    ResultRes statisticsShareOrderApi(String uuid);

}
