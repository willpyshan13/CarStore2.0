package com.car.order.web.service.technicianappointment.impl;

import com.alibaba.fastjson.JSON;
import com.car.account.client.enums.comm.TerminalEnum;
import com.car.account.client.feign.MessageFeign;
import com.car.account.client.feign.ProfitStreamFeign;
import com.car.account.client.feign.TechnicianFegin;
import com.car.account.client.feign.VehicleFegin;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.client.request.profit.SceneOrderProfitReq;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.account.client.response.vehicle.config.ConfigRes;
import com.car.account.client.response.vehicle.vehicleUser.VehicleUserRes;
import com.car.common.enums.*;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.*;
import com.car.common.utils.token.LoginToken;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.order.DoorOrServiceEnum;
import com.car.order.client.enums.order.ImageTypeEnum;
import com.car.order.client.enums.scene.SceneOrderStsEnum;
import com.car.order.client.enums.sharetechnicianorder.ShareTechnicianOrderEnum;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.request.order.order.ConfirmOrderReq;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;
import com.car.order.client.request.technicianappointment.*;
import com.car.order.client.response.scene.SceneOrderServiceRes;
import com.car.order.client.response.scene.SceneOrderTechnicianRes;
import com.car.order.client.response.scene.StatisticsSceneOrderRes;
import com.car.order.client.response.share.StatisticsShoreOrderRes;
import com.car.order.client.response.technicianappointment.*;
import com.car.order.web.controller.share.ShareTechnicianOrderController;
import com.car.order.web.mapper.order.OrderInfoMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianDetailMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianImagesMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianOrderMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianServiceMapper;
import com.car.order.web.model.order.OrderInfo;
import com.car.order.web.model.scene.SceneOrder;
import com.car.order.web.model.scene.SceneOrderDtcImages;
import com.car.order.web.model.scene.SceneOrderServices;
import com.car.order.web.model.scene.SceneOrderTechnician;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianDetail;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianImages;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianOrder;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianService;
import com.car.order.web.service.order.OrderAccountService;
import com.car.order.web.service.order.OrderInfoService;
import com.car.order.web.service.technicianappointment.ShareTechnicianOrderService;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.dict.DictionaryRes;
import com.car.utility.client.enums.OrderStatusEnum;
import com.car.utility.client.enums.PaymentTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author niushuaixiang
 * @PACKAGE_NAME: com.car.order.web.service.technicianappointment.impl
 * @NAME: TechnicianAppointmentOrderServiceImpl
 * @DATE: 2021/8/11 21:02
 */
@Slf4j
@Service
public class ShareTechnicianOrderServiceImpl implements ShareTechnicianOrderService {

    @Autowired
    ShareTechnicianOrderMapper shareTechnicianOrderMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    SystemFeign systemFeign;

    @Autowired
    TechnicianFegin technicianFegin;

    @Autowired
    VehicleFegin vehicleFegin;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    ProfitStreamFeign profitStreamFeign;
    @Autowired
    ShareTechnicianDetailMapper shareTechnicianDetailMapper;
    @Autowired
    ShareTechnicianImagesMapper shareTechnicianImagesMapper;
    @Autowired
    MessageFeign messageFeign;
    @Autowired
    OrderAccountService orderAccountService;
    @Autowired
    ShareTechnicianServiceMapper shareTechnicianServiceMapper;
    /**
     * 默认专家基础上门费
     */
    private static final String expert_share_technician_money = "6201";

    /**
     * 默认普通技师基础上门费
     */
    private static final String default_share_technician_money = "6202";

    /**
     * 预约技师平台服务费
     */
    private static final String default_share_technician_service_money = "6203";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultRes<String> saveTechnicianAppointment(ShareTechnicianOrderReq req) {
        ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
        BeanUtils.copyProperties(req, shareTechnicianOrder);
        // 发起人员账号为当前下单车主
        shareTechnicianOrder.setOwnerUuid(TokenHelper.getUserUuid());
        shareTechnicianOrder.setUuid(UuidUtils.getUuid());

        shareTechnicianOrder.setCreatedBy(TokenHelper.getUserName());
        shareTechnicianOrder.setCreatedTime(new Date());
        shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
        shareTechnicianOrder.setLastUpdatedTime(new Date());
        shareTechnicianOrder.setSts(StsEnum.ACTIVE.getValue());
        shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.HAVE_PAID.getValue());

        insertShareOrderDtcImg(req.getFaultImage(),TokenHelper.getUserName(),shareTechnicianOrder.getUuid(),ImageTypeEnum.FAULT_DESC.getValue());


        ResultRes<TechnicianRes> resResultRes = technicianFegin.queryTechnicianDetail( req.getTechnicianUuid());
        if(!resResultRes.isSuccess()){
            return ResultRes.error(ResEnum.TECHNICIAN_NOT_EXIST);
        }
        //查询字典预约费用
        ResultRes<DictionaryRes> reservationMoneyRes = null;
        if(resResultRes.getData().getCybAuth().equals(1)) {
            reservationMoneyRes = systemFeign.queryByUuid(StringUtils.isEmpty(req.getBasicDoorAmountUuid()) ? expert_share_technician_money : req.getBasicDoorAmountUuid());
        }else {
            reservationMoneyRes = systemFeign.queryByUuid(StringUtils.isEmpty(req.getBasicDoorAmountUuid()) ? default_share_technician_money : req.getBasicDoorAmountUuid());
        }
        if (!reservationMoneyRes.isSuccess()){
            return ResultRes.error(ResEnum.TECHNICIAN_NO_CONSUL_ORDER);
        }
        DictionaryRes reservationMoney = reservationMoneyRes.getData();
        BigDecimal reservationMoneyNum = new BigDecimal(reservationMoney.getLableValue());
        //预约技师费用金额
        shareTechnicianOrder.setBasicDoorAmount(reservationMoneyNum);
        //查询字典预约平台服务费用
        ResultRes<DictionaryRes> platformMoneyRes = systemFeign.queryByUuid(req.getBasicDoorAmountUuid());
        if (!platformMoneyRes.isSuccess()){
            return ResultRes.error(ResEnum.TECHNICIAN_NO_CONSUL_ORDER);
        }
        DictionaryRes platformMoney = platformMoneyRes.getData();
        BigDecimal platformMoneyNum = new BigDecimal(platformMoney.getLableValue()).multiply(reservationMoneyNum).setScale(2,BigDecimal.ROUND_UP);
        //平台服务费用金额
        shareTechnicianOrder.setPlatformMoney(platformMoneyNum);
        //计算订单总金额
        shareTechnicianOrder.setTotalAmount(reservationMoneyNum);
        // 订单号生成规则需要确认
        shareTechnicianOrder.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.GX));
        shareTechnicianOrder.setCouponAmount(BigDecimal.ZERO);
        int cnt = shareTechnicianOrderMapper.insert(shareTechnicianOrder);
        if (cnt > 0) {
            //记录生成成功，生成订单主表数据
            return ResultRes.success(shareTechnicianOrder.getUuid());
        } else {
            return ResultRes.error(ResEnum.INSERT_ORDER_ERROR);
        }
    }

    @Override
    public PageRes<List<ShareTechnicianOrderRes>> queryShareTechnicianOrderList(QueryShareTechnicianOrderReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<ShareTechnicianOrder> list = null;
        List<Integer> orderStatus = new ArrayList<>();
        //待付款(1)待服务(2)已完成(3)退款(4,5)

        if (StringUtils.isEmpty(TokenHelper.getUserType())){
            list = shareTechnicianOrderMapper.queryShareTechnicianOrderList(null, null, null);
            PageInfo<ShareTechnicianOrder> pageInfo = new PageInfo(list);
            List<ShareTechnicianOrderRes> resList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(list)) {
                list.stream().forEach(data -> {
                    ShareTechnicianOrderRes res = new ShareTechnicianOrderRes();
                    BeanUtils.copyProperties(data, res);
                    res.setOrderStatusName(ShareTechnicianOrderEnum.enumOfDesc(data.getOrderStatus()));
                    resList.add(res);
                });
            }
            return PageRes.success(resList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
        }
        if (req.getStatus()!=null && req.getStatus().equals(1)) {
            orderStatus.add(ShareTechnicianOrderEnum.UNPAID.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.WAIT_DOOR.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.WAIT_PAYMENT.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.IN_SERVICE.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.WAIT_COMPLETED.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.A_REFUND_OF.getValue());
        } else if (req.getStatus()!=null && req.getStatus().equals(2)) {
            orderStatus.add(ShareTechnicianOrderEnum.COMPLETED.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.CANCELED.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.REFUSE.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.REFUND_FAILURE.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.REFUND_SUCCESS.getValue());
        }
        if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType().intValue())) {
            //车主端查询
            if (req.getStatus().equals(1)) {
                orderStatus.add(ShareTechnicianOrderEnum.HAVE_PAID.getValue());
            }
        } else if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType().intValue())) {
            //技师端查询
            if(req.getType().equals(1)){
                orderStatus.clear();
                orderStatus.add(ShareTechnicianOrderEnum.HAVE_PAID.getValue());
            }
        }

        list = shareTechnicianOrderMapper.queryShareTechnicianOrderList(orderStatus, TokenHelper.getUserUuid(), TokenHelper.getUserType());

        PageInfo<ShareTechnicianOrder> pageInfo = new PageInfo(list);
        List<ShareTechnicianOrderRes> resList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(data -> {
                ShareTechnicianOrderRes res = new ShareTechnicianOrderRes();
                BeanUtils.copyProperties(data, res);
                res.setOrderStatusName(ShareTechnicianOrderEnum.enumOfDesc(data.getOrderStatus()));
                resList.add(res);
            });
        }
        return PageRes.success(resList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    @Override
    public ResultRes updateShareTechnicianOrder(String uuid) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())) {
            // 登录端为车主
            if (ShareTechnicianOrderEnum.WAIT_COMPLETED.getValue().equals(shareTechnicianOrder.getOrderStatus())) {
                //订单状态为待服务
                ConfirmOrderReq req = new ConfirmOrderReq();
                req.setOrderUuid(uuid);
                req.setOrderType(OrderTypeEnum.SHARED_TECHNICIAN.getValue());
                orderInfoService.confirmOrder(req);

                return ResultRes.success();
            } else {
                return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
            }
        } else {
            return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
        }
    }

    @Override
    public ResultRes applicationRefundShareTechnicianOrder(String uuid) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())) {
            // 登录端为车主
            //订单状态为 待接单，待服务，车主可发起退款
            if (ShareTechnicianOrderEnum.HAVE_PAID.getValue().equals(shareTechnicianOrder.getOrderStatus()) || ShareTechnicianOrderEnum.WAIT_DOOR.getValue().equals(shareTechnicianOrder.getOrderStatus())) {
                shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.A_REFUND_OF.getValue());
                shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
                shareTechnicianOrder.setLastUpdatedTime(new Date());
                shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
                // todo 申请退款后续业务暂停，等待退款功能接口完成
                return ResultRes.success();
            } else {
                return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
            }
        } else {
            return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
        }
    }

    @Override
    public ResultRes<String> shareOrderDescribe(ShareTechnicianDetailReq req) {
        //获取当前登录用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //获取当前登录用户名称
        String userName = TokenHelper.getUserName();
        //获取当前登录用户手机号
        String phone = null;
        LoginToken loginToken = TokenHelper.getLoginToken();
        if (null != loginToken) {
            phone = loginToken.getUserMobile();
        }

        ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
        shareTechnicianOrder.setUuid(req.getSceneOrderUuid());
        ShareTechnicianOrder shareTechnicianOrders = shareTechnicianOrderMapper.selectOne(shareTechnicianOrder);
        if (null == shareTechnicianOrders) {

            log.error("未查询到相关信息，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        ShareTechnicianDetail shareTechnicianDetail = new ShareTechnicianDetail();

        if (req.getType().equals(DoorOrServiceEnum.SHOW_UP.getValue())){
            shareTechnicianDetail.setUuid(UuidUtils.getUuid());
            shareTechnicianDetail.setDesc(req.getDescribe());
            shareTechnicianDetail.setOrderUuid(req.getSceneOrderUuid());
            shareTechnicianDetail.setTechnicianMobile(phone);
            shareTechnicianDetail.setTechnicianUuid(userUuid);
            shareTechnicianDetail.setTechnicianName(userName);
            shareTechnicianDetail.setSts(StsEnum.ACTIVE.getValue());
            shareTechnicianDetail.setCreatedTime(new Date());
            shareTechnicianDetail.setCreatedBy(userName);
            shareTechnicianDetailMapper.insert(shareTechnicianDetail);
            shareTechnicianOrder.setShareTechnicianDetailUuid(shareTechnicianDetail.getUuid());
            shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
            insertShareOrderDtcImg(req.getImageList(), userName, shareTechnicianDetail.getUuid(), ImageTypeEnum.SHOW_UP.getValue());
            try {

            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }else if(req.getType().equals(DoorOrServiceEnum.SERVICE_END.getValue()))
        {
            shareTechnicianDetail.setUuid(shareTechnicianOrders.getShareTechnicianDetailUuid());
            shareTechnicianDetail.setRepairSummary(req.getRepairSummary());
            shareTechnicianDetail.setFaultSolve(0);
            shareTechnicianDetail.setLastUpdatedTime(new Date());
            shareTechnicianDetail.setLastUpdatedBy(userName);
            shareTechnicianDetailMapper.updateByPrimaryKeySelective(shareTechnicianDetail);
            shareTechnicianOrder.setOrderStatus(SceneOrderStsEnum.WAIT_COMPLETED.getValue());
            shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
            insertShareOrderDtcImg(req.getImageList(), userName, shareTechnicianDetail.getUuid(),ImageTypeEnum.SERVICE_END.getValue());

            try {


            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }
        shareTechnicianOrder.setLastUpdatedBy(userName);
        shareTechnicianOrder.setLastUpdatedTime(new Date());
        shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
        return ResultRes.success();
    }

    @Override
    public ResultRes<String> shareOrderConfirm(ShareOrderConfirmReq req) {
        ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
        shareTechnicianOrder.setUuid(req.getShareOrderUuid());
        ShareTechnicianOrder shareOrderSelect = shareTechnicianOrderMapper.selectOne(shareTechnicianOrder);
        if (null == shareOrderSelect) {
            log.error("未查询到相关信息，订单uuid为：{}", req.getShareOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if (req.getType().equals(DoorOrServiceEnum.SHOW_UP.getValue())){
            if(StringUtils.isEmpty(shareOrderSelect.getShareTechnicianDetailUuid())){
                log.error("技师未上门：{}", req.getShareOrderUuid());
                throw new BusinessException("9999","技师未上门!");
            }
            shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            shareTechnicianOrder.setGrabUpdateTime(new Date());
            shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());

            shareTechnicianOrder.setLastUpdatedTime(new Date());
            orderAccountService.sceneOrderBranchAccount(shareTechnicianOrder.getUuid(),OrderTypeEnum.SHARED_TECHNICIAN.getValue());
            shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
            try {



            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }else if(req.getType().equals(DoorOrServiceEnum.SERVICE_END.getValue()))
        {
            if(!shareOrderSelect.getOrderStatus().equals(ShareTechnicianOrderEnum.WAIT_COMPLETED.getValue())){
                log.error("技师没有提交完成：{}", req.getShareOrderUuid());
                throw new BusinessException("9999","技师没有提交完成!");
            }
            shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.COMPLETED.getValue());
            shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
            shareTechnicianOrder.setLastUpdatedTime(new Date());
            orderAccountService.sceneOrderBranchAccount(shareOrderSelect.getUuid(),OrderTypeEnum.SCENE_SERVICE.getValue());
            shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
            try {


            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }

        return ResultRes.success(req.getShareOrderUuid());
    }

    @Override
    public ResultRes<String> sceneSubmitPlan(AddShareOrderServiceReq addShareOrderServiceReq) {
        ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
        shareTechnicianOrder.setUuid(addShareOrderServiceReq.getShareOrderUuid());
        ShareTechnicianOrder shareOrderSelect = shareTechnicianOrderMapper.selectOne(shareTechnicianOrder);
        if (null == shareOrderSelect) {
            log.error("未查询到相关信息，订单uuid为：{}", addShareOrderServiceReq.getShareOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if ( !shareOrderSelect.getOrderStatus().equals(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue())) {
            log.error("订单状态异常，订单uuid为：{}", addShareOrderServiceReq.getShareOrderUuid());
            throw new BusinessException("9999","订单状态异常!");
        }
        //创建服务订单
        ShareTechnicianService shareTechnicianService = new ShareTechnicianService();
        BeanUtils.copyProperties(addShareOrderServiceReq, shareTechnicianService);
        String technicianServiceMoney = queryDictValue( default_share_technician_service_money);

        BigDecimal totalAmount = addShareOrderServiceReq.getOtherAmount().add(addShareOrderServiceReq.getBasicInspectAmount()).add(addShareOrderServiceReq.getRepairAmount());
        BigDecimal platformMoneyNum = totalAmount.multiply(new BigDecimal(technicianServiceMoney)).setScale(2,BigDecimal.ROUND_UP);

        shareTechnicianService.setOrderServiceAmount(platformMoneyNum);
        shareTechnicianService.setOrderSts(OrderStsEnum.UNPAID.getValue());
        shareTechnicianService.setUuid(UuidUtils.getUuid());
        shareTechnicianService.setCouponAmount(BigDecimal.ONE);
        shareTechnicianService.setCreatedBy(TokenHelper.getUserName());
        shareTechnicianService.setCreatedTime(new Date());
        shareTechnicianService.setSts(0);

        shareTechnicianService.setTotalAmount(totalAmount.subtract(shareTechnicianService.getCouponAmount()));
        shareTechnicianServiceMapper.insert(shareTechnicianService);

        insertShareOrderDtcImg(addShareOrderServiceReq.getImageList(), TokenHelper.getUserName(), shareTechnicianOrder.getUuid(),ImageTypeEnum.SERVICE.getValue());
        //修改主订单状态
        shareTechnicianOrder.setTotalAmount(shareTechnicianOrder.getTotalAmount().add(shareTechnicianService.getTotalAmount()));
        shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.WAIT_PAYMENT.getValue());
        shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
        shareTechnicianOrder.setLastUpdatedTime(new Date());
        shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);


        //创建支付订单
        AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
        addOrderInfoReq.setUuid(UuidUtils.getUuid());
        addOrderInfoReq.setOrderType(OrderTypeEnum.SHARED_SERVICE.getValue());
        addOrderInfoReq.setOrderUuid(shareTechnicianOrder.getUuid());
        orderInfoService.addOrder(addOrderInfoReq);
        try {


        } catch (Exception e) {
            e.printStackTrace();
            log.info("推送消息失败!!");
        }
        return ResultRes.success(shareTechnicianService.getUuid());
    }

    /**
     * 根据字典uuid，查询字典名称
     * @return
     */
    private String queryDictValue (String dictUuid) {
        String dictName = null;
        if (StringUtils.isEmpty(dictUuid)) {
            return dictName;
        }
        //根据字典uuid查询字典信息
        ResultRes<DictionaryRes> resResultRes = systemFeign.queryByUuid(dictUuid);
        if (resResultRes.isSuccess()) {
            if (null != resResultRes.getData()) {
                dictName = resResultRes.getData().getLableValue();
            }
        }
        return dictName;
    }

    /**
     * 图片新增
     * @param dtcImageList
     */

    public void insertShareOrderDtcImg (List<String> dtcImageList, String userName, String relationUuid,Integer type) {
        for (String dtcImgUrl : dtcImageList) {
            ShareTechnicianImages shareTechnicianImages = new ShareTechnicianImages();
            shareTechnicianImages.setUuid(UuidUtils.getUuid());
            shareTechnicianImages.setRelationUuid(relationUuid);
            shareTechnicianImages.setImageUrl(dtcImgUrl);
            shareTechnicianImages.setType(type);
            shareTechnicianImages.setSts(StsEnum.ACTIVE.getValue());
            shareTechnicianImages.setCreatedBy(userName);
            shareTechnicianImages.setCreatedTime(new Date());
            int insertNum = shareTechnicianImagesMapper.insert(shareTechnicianImages);
            if (insertNum <= 0) {
                log.info("新增现场订单dtc图片失败，请求参数为：{}", JSON.toJSONString(shareTechnicianImages));
                throw new BusinessException(ResEnum.INSERT_DB_ERROR);
            }
        }
    }
    @Override
    public ResultRes<StatisticsShoreOrderRes> shareTechnicianOrderStatistics( SceneOrderStatisticsReq sceneOrderStatisticsReq) {
        String userUuId = TokenHelper.getUserUuid();
        StatisticsShoreOrderRes statisticsShoreOrderRes = new StatisticsShoreOrderRes();
        statisticsShoreOrderRes.setNumList( shareTechnicianOrderMapper.shareOrderStatisticsNum(sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth(),userUuId));
        statisticsShoreOrderRes.setTotal(shareTechnicianOrderMapper.statisticsShareOrder(userUuId))
        ;
        SceneOrderProfitReq sceneOrderProfitReq = new SceneOrderProfitReq();
        sceneOrderProfitReq.setMonth(sceneOrderStatisticsReq.getMonth());
        sceneOrderProfitReq.setYear(sceneOrderStatisticsReq.getYear());

        statisticsShoreOrderRes.setAmountList(profitStreamFeign.statisticsAmountByType(sceneOrderProfitReq).getData());

        ProfitStreamReq profitStreamReq = new ProfitStreamReq();
        profitStreamReq.setClassify("10");
        profitStreamReq.setUserUuid(userUuId);
        profitStreamReq.setStreamType(StreamTypeEnum.IN.getType());

        statisticsShoreOrderRes.setTotalAmount(profitStreamFeign.statisticsAmount(profitStreamReq).getData());
        return ResultRes.success(statisticsShoreOrderRes);
    }


    @Override
    public ResultRes<ShareTechnicianOrderInfoRes> queryShareTechnicianOrder(String uuid) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        //获取当前登录人uuid
        String userUuid = TokenHelper.getUserUuid();
        //查询技师详情
        ResultRes<TechnicianRes> resResultRes = technicianFegin.queryTechnicianDetail(shareTechnicianOrder.getTechnicianUuid());
        if (!resResultRes.isSuccess()){
            throw new BusinessException(resResultRes.getCode(),resResultRes.getMsg());
        }
        TechnicianRes technicianRes = resResultRes.getData();

        ResultRes<DictionaryRes> dictionaryResResultRes = systemFeign.queryByUuid(technicianRes.getTechnologyType());
        if (!dictionaryResResultRes.isSuccess()){
            throw new BusinessException(dictionaryResResultRes.getCode(),dictionaryResResultRes.getMsg());
        }

        ResultRes<VehicleUserRes> vehicleUserResResultRes = vehicleFegin.queryDetail(shareTechnicianOrder.getOwnerUuid());
        if (!vehicleUserResResultRes.isSuccess()){
            throw new BusinessException(vehicleUserResResultRes.getCode(),vehicleUserResResultRes.getMsg());
        }

        ShareTechnicianOrderInfoRes res = new ShareTechnicianOrderInfoRes();
        BeanUtils.copyProperties(shareTechnicianOrder, res);
        res.setName(technicianRes.getUserName());
        res.setPhotoImgUrl(technicianRes.getPhotoImgUrl());
        res.setTechnologyType(technicianRes.getTechnologyType());
        res.setWorkingYear(technicianRes.getWorkingYear());
        res.setShareNum(technicianRes.getShareNum());
        //设置技师联系方式
        res.setTechnicianPhone(technicianRes.getMobile());
        //设置车主联系方式
        res.setCarOwnerPhone( vehicleUserResResultRes.getData().getMobile());

        res.setTechnologyTypeName(dictionaryResResultRes.getData().getLableDesc());

        // 关联查询车型，品牌等信息
        ResultRes<ConfigRes> brandRes = vehicleFegin.queryConfig(shareTechnicianOrder.getBrandUuid());
        if (!brandRes.isSuccess()){
            throw new BusinessException(brandRes.getCode(),brandRes.getMsg());
        }
        res.setBrandName(brandRes.getData().getConfigName());
        ResultRes<ConfigRes> modelRes = vehicleFegin.queryConfig(shareTechnicianOrder.getModelUuid());
        if (!modelRes.isSuccess()){
            throw new BusinessException(modelRes.getCode(),modelRes.getMsg());
        }
        res.setModelName(modelRes.getData().getConfigName());

        //转换技师维修品牌信息
        List<TechnicianBrandRes> technicianBrandResList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(technicianRes.getBrandList())) {
            technicianRes.getBrandList().forEach(data -> {
                TechnicianBrandRes technicianBrandRes = new TechnicianBrandRes();
                BeanUtils.copyProperties(data, technicianBrandRes);
                technicianBrandResList.add(technicianBrandRes);
            });
        }
        res.setBrandList(technicianBrandResList);
        //查询现场下单订单dtc图片
        List<String> faultImageList = shareTechnicianImagesMapper.queryList(shareTechnicianOrder.getUuid(),ImageTypeEnum.FAULT_DESC.getValue());
        res.setFaultImageList(faultImageList);
        boolean flag = false;
        if (userUuid.equals(shareTechnicianOrder.getOwnerUuid())) {
            flag = true;
        }
        res.setIsOneself(flag);
        //查询共享技师上门或完成对象
        if(!StringUtils.isEmpty(shareTechnicianOrder.getShareTechnicianDetailUuid())) {
            ShareTechnicianDetail shareTechnicianDetail = shareTechnicianDetailMapper.selectByPrimaryKey(shareTechnicianOrder.getShareTechnicianDetailUuid());
            ShareTechnicianDetailRes shareTechnicianDetailRes = new ShareTechnicianDetailRes();
            BeanUtils.copyProperties(shareTechnicianDetail, shareTechnicianDetailRes);
            res.setShareTechnicianDetailRes(shareTechnicianDetailRes);
            List<String> lists =shareTechnicianImagesMapper.queryList(shareTechnicianDetail.getUuid(),ImageTypeEnum.SHOW_UP.getValue());
            if(lists!=null&&lists.size()>0) {
                res.getShareTechnicianDetailRes().setDoorImageList(lists);
            }
            List<String> list = shareTechnicianImagesMapper.queryList(shareTechnicianDetail.getUuid(),ImageTypeEnum.SERVICE_END.getValue());
            if(list!=null&&list.size()>0) {
                res.getShareTechnicianDetailRes().setEndImageList(list);
            }
        }
        //查询共享技师服务对象
        ShareTechnicianService shareTechnicianService = new ShareTechnicianService();
        shareTechnicianService.setOrderNum(shareTechnicianOrder.getOrderNum());
        shareTechnicianService = shareTechnicianServiceMapper.selectOne(shareTechnicianService);
        if(shareTechnicianService!=null) {
            ShareTechnicianServiceRes shareTechnicianServiceRes = new ShareTechnicianServiceRes();
            BeanUtils.copyProperties(shareTechnicianService, shareTechnicianServiceRes);
            List<String> list = shareTechnicianImagesMapper.queryList(shareTechnicianService.getUuid(),ImageTypeEnum.SERVICE.getValue());
            shareTechnicianServiceRes.setImageList(list);
            res.setShareTechnicianServiceRes(shareTechnicianServiceRes);
        }

        return ResultRes.success(res);
    }

    @Override
    public ResultRes receiveShareTechnicianOrder(String uuid) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())) {
            // 登录端为技师

            if (ShareTechnicianOrderEnum.HAVE_PAID.getValue().equals(shareTechnicianOrder.getOrderStatus())) {
                shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.UNPAID.getValue());
                shareTechnicianOrder.setOrderTakingDate(new Date());
                shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
                shareTechnicianOrder.setLastUpdatedTime(new Date());
                shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
                AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
                addOrderInfoReq.setOrderType(OrderTypeEnum.SHARED_TECHNICIAN.getValue());
                addOrderInfoReq.setOrderUuid(shareTechnicianOrder.getUuid());
                orderInfoService.addOrder(addOrderInfoReq);
                return ResultRes.success();
            } else {
                return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
            }
        } else {
            return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
        }
    }

    @Override
    public ResultRes refuseOrder(String uuid) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())) {
            // 登录端为技师
            if (ShareTechnicianOrderEnum.HAVE_PAID.getValue().equals(shareTechnicianOrder.getOrderStatus())) {
                shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.REFUSE.getValue());
                shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
                shareTechnicianOrder.setLastUpdatedTime(new Date());
                shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
                return ResultRes.success(uuid);
            } else {
                return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
            }
        } else {
            return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
        }
    }

    @Override
    public ResultRes cancelOrder(String uuid,Integer type) {
        ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper.selectByPrimaryKey(uuid);
        if (StringUtils.isEmpty(shareTechnicianOrder)) {
            return ResultRes.error(ResEnum.NOT_ORDER_ERROR);
        }
        if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())) {
            // 登录端为技师
            if (ShareTechnicianOrderEnum.HAVE_PAID.getValue().equals(shareTechnicianOrder.getOrderStatus())||ShareTechnicianOrderEnum.UNPAID.getValue().equals(shareTechnicianOrder.getOrderStatus())||ShareTechnicianOrderEnum.WAIT_PAYMENT.getValue().equals(shareTechnicianOrder.getOrderStatus())) {
                if(type.equals(1)) {
                    shareTechnicianOrder.setOrderStatus(SceneOrderStsEnum.CANCELED.getValue());
                }else  if(type.equals(2)) {
                    shareTechnicianOrder.setOrderStatus(SceneOrderStsEnum.HAVE_PAID_CANCELED.getValue());
                }else if(type.equals(3)) {
                    shareTechnicianOrder.setOrderStatus(SceneOrderStsEnum.SERVICE_CANCELED.getValue());
                }
                shareTechnicianOrder.setLastUpdatedBy(TokenHelper.getUserName());
                shareTechnicianOrder.setLastUpdatedTime(new Date());
                shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
                return ResultRes.success(uuid);
            } else {
                return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
            }
        } else {
            return ResultRes.error(ResEnum.LOGIN_TERMINAL_UPDATE_ERROR);
        }
    }

    @Override
    public ResultRes statisticsShareOrderApi(String uuid) {
        Integer count = shareTechnicianOrderMapper.statisticsShareOrder(uuid);
        return ResultRes.success(count);
    }
}
