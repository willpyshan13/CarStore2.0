package com.car.order.web.service.sence.impl;

import com.alibaba.fastjson.JSON;
import com.car.account.client.feign.*;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.client.request.profit.SceneOrderProfitReq;
import com.car.account.client.response.store.StoreBrandRes;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.account.client.response.store.StoreUserRes;
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
import com.car.order.client.enums.scene.SceneOrderStsEnum;
import com.car.order.client.enums.order.DoorOrServiceEnum;
import com.car.order.client.enums.order.ImageTypeEnum;
import com.car.order.client.enums.scene.GrabOrdersStsEnum;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.request.order.order.ConfirmOrderReq;
import com.car.order.client.request.scene.*;
import com.car.order.client.response.scene.*;
import com.car.order.web.dto.LaAndLoDto;
import com.car.order.web.dto.OrderSceneDto;
import com.car.order.web.dto.scene.SceneOrderDto;
import com.car.order.web.mapper.scene.SceneOrderDtcImagesMapper;
import com.car.order.web.mapper.scene.SceneOrderMapper;
import com.car.order.web.mapper.scene.SceneOrderServicesMapper;
import com.car.order.web.mapper.scene.SceneOrderTechnicianMapper;
import com.car.order.web.model.scene.SceneOrder;
import com.car.order.web.model.scene.SceneOrderDtcImages;
import com.car.order.web.model.scene.SceneOrderServices;
import com.car.order.web.model.scene.SceneOrderTechnician;
import com.car.order.web.service.order.OrderAccountService;
import com.car.order.web.service.order.OrderInfoService;
import com.car.order.web.service.sence.SceneOrderService;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.dict.DictionaryRes;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author cjw
 */
@Slf4j
@Service
public class SceneOrderServiceImpl implements SceneOrderService {

    @Autowired
    private SceneOrderMapper sceneOrderMapper;

    @Autowired
    private SystemFeign systemFeign;

    @Autowired
    private VehicleFegin vehicleFegin;

    @Autowired
    private SceneOrderDtcImagesMapper sceneOrderDtcImagesMapper;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private TechnicianFegin technicianFegin;

    @Autowired
    private StoreUserFeign storeUserFeign;

    @Autowired
    private StoreFegin storeFegin;
    @Autowired
    private ProfitStreamFeign profitStreamFeign;
    @Autowired
    private SceneOrderTechnicianMapper sceneOrderTechnicianMapper;

    @Autowired
    private SceneOrderServicesMapper sceneOrderServicesMapper;
    @Autowired
    OrderAccountService orderAccountService;
    @Autowired
    MessageFeign messageFeign;

    /**
     *新增现场订单
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> addSceneOrder(AddSceneOrderReq req) {
        if (null == req) {
            log.error("新增现场订单请求参数为空：{}", JSON.toJSONString(req));
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        //查询当前登录用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //查询当前登录用户名称
        String userName = TokenHelper.getUserName();
        //查询当前登录用户类型
        Integer userType = TokenHelper.getUserType();
        //获取当前登录用户手机号
        String mobile = null;
        LoginToken loginToken = TokenHelper.getLoginToken();
        if (null != loginToken) {
            mobile = loginToken.getUserMobile();
        }
        if (UserTypeEnum.store.getType().equals(userType)) {
            ResultRes<StoreDetailRes> resResultRes = storeFegin.queryStoreDetailByUser();
            userName = resResultRes.getData().getStoreName();
            userUuid =getStoremMsterUser(userUuid);
        }
        //新增现场订单
        SceneOrder sceneOrder = insertSceneOrder(req, userName, mobile, userUuid, userType);

        //新增dtc图片
        if (!CollectionUtils.isEmpty(req.getDtcImageList())) {
            insertSceneOrderDtcImg(req.getDtcImageList(), userName, sceneOrder.getUuid(), ImageTypeEnum.DTC.getValue());
        }
        //新增故障描述图片
        if (!CollectionUtils.isEmpty(req.getDtcImageList())) {
            insertSceneOrderDtcImg(req.getFaultDescImageList(), userName, sceneOrder.getUuid(), ImageTypeEnum.FAULT_DESC.getValue());
        }
        try {
            Map<String, String> param = new HashMap<>();
            param.put("site", req.getDetailedAddr());
            param.put("pingpai", sceneOrder.getBrandName());
            param.put("chexing", sceneOrder.getCarModelName());
            messageFeign.sendMsg("3001", param, userUuid, userType,sceneOrder.getUuid());
        } catch (Exception e) {
            log.info("推送消息失败!!");
        }

        return ResultRes.success(sceneOrder.getUuid());
    }


    /**
     *现场订单-描述
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> sceneOrderDescribe(SceneOrderDescribeReq req) {
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

        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(req.getSceneOrderUuid());
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(sceneOrder);
        if (null == sceneOrderSelect) {

            log.error("未查询到相关信息，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        SceneOrderTechnician sceneOrderTechnician = new SceneOrderTechnician();

        if (req.getType().equals(DoorOrServiceEnum.SHOW_UP.getValue())){

            if (StringUtil.isNotBlank(sceneOrderSelect.getSceneOrderTechnicianUuid())) {
                log.error("重复提交，订单uuid为：{}", req.getSceneOrderUuid());
                throw new BusinessException(ResEnum.OPERATION_FREQUENTLY_ERROR);
            }
            sceneOrderTechnician.setUuid(UuidUtils.getUuid());
            sceneOrderTechnician.setDescribes(req.getDescribe());
            sceneOrderTechnician.setOrderUuid(req.getSceneOrderUuid());
            sceneOrderTechnician.setTechnicianMobile(phone);
            sceneOrderTechnician.setTechnicianUuid(userUuid);
            sceneOrderTechnician.setTechnicianName(userName);
            sceneOrderTechnician.setSts(StsEnum.ACTIVE.getValue());
            sceneOrderTechnician.setCreatedTime(new Date());
            sceneOrderTechnician.setCreatedBy(userName);
            sceneOrderTechnicianMapper.insert(sceneOrderTechnician);
            sceneOrder.setSceneOrderTechnicianUuid(sceneOrderTechnician.getUuid());
            sceneOrderMapper.updateByPrimaryKeySelective(sceneOrderSelect);
            insertSceneOrderDtcImg(req.getImageList(), userName, sceneOrderTechnician.getUuid(),ImageTypeEnum.SHOW_UP.getValue());
            try {
                Map<String, String> param = new HashMap<>();
                param.put("site", sceneOrderSelect.getDetailedAddr());
                param.put("pingpai", sceneOrderSelect.getBrandName());
                param.put("chexing", sceneOrderSelect.getCarModelName());
                messageFeign.sendMsg("3004", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrder.getUuid());

                Map<String, String> param1 = new HashMap<>();
                sendSceneMsg(sceneOrderSelect, param1);
                messageFeign.sendMsg("3010", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());
            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }else if(req.getType().equals(DoorOrServiceEnum.SERVICE_END.getValue()))
        {
            sceneOrderTechnician.setUuid(sceneOrderSelect.getSceneOrderTechnicianUuid());
            sceneOrderTechnician.setRepairSummary(req.getRepairSummary());
            sceneOrderTechnician.setFaultSolve(0);
            sceneOrderTechnician.setLastUpdatedTime(new Date());
            sceneOrderTechnician.setLastUpdatedBy(userName);
            sceneOrderTechnicianMapper.updateByPrimaryKeySelective(sceneOrderTechnician);
            sceneOrder.setOrderSts(SceneOrderStsEnum.WAIT_COMPLETED.getValue());
            sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
            insertSceneOrderDtcImg(req.getImageList(), userName, sceneOrderTechnician.getUuid(),ImageTypeEnum.SERVICE_END.getValue());

            try {
                Map<String, String> param = new HashMap<>();
                param.put("site", sceneOrderSelect.getDetailedAddr());
                param.put("pingpai", sceneOrderSelect.getBrandName());
                param.put("chexing", sceneOrderSelect.getCarModelName());
                messageFeign.sendMsg("3006", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrderSelect.getUuid());
                Map<String, String> param1 = new HashMap<>();
                sendSceneMsg(sceneOrderSelect, param1);
                messageFeign.sendMsg("3014", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());
            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }
        sceneOrder.setLastUpdatedBy(userName);
        sceneOrder.setLastUpdatedTime(new Date());
        sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
        return  ResultRes.success(sceneOrder.getUuid());
    }

    /**
     * 现场订单--客户确认
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> sceneOrderConfirm(SceneOrderConfirmReq req){
        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(req.getSceneOrderUuid());
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(sceneOrder);
        if (null == sceneOrderSelect) {
            log.error("未查询到相关信息，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if (req.getType().equals(DoorOrServiceEnum.SHOW_UP.getValue())){
            if(StringUtils.isEmpty(sceneOrderSelect.getSceneOrderTechnicianUuid())){
                log.error("技师未上门：{}", req.getSceneOrderUuid());
                throw new BusinessException("9999","技师未上门!");
            }
            sceneOrder.setOrderSts(SceneOrderStsEnum.SUBMIT_PLAN.getValue());
            sceneOrder.setLastUpdatedBy(TokenHelper.getUserName());
            sceneOrder.setLastUpdatedTime(new Date());
            sceneOrder.setGrabUpdateTime(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            orderAccountService.sceneOrderBranchAccount(sceneOrderSelect.getUuid(),OrderTypeEnum.SCENE.getValue());
            sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
            try {

                Map<String, String> param1 = new HashMap<>();
                sendSceneMsg(sceneOrderSelect, param1);
                messageFeign.sendMsg("3011", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());

            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }else if(req.getType().equals(DoorOrServiceEnum.SERVICE_END.getValue()))
        {
            if(!sceneOrderSelect.getOrderSts().equals(SceneOrderStsEnum.WAIT_COMPLETED.getValue())){
                log.error("技师没有提交完成：{}", req.getSceneOrderUuid());
                throw new BusinessException("9999","技师没有提交完成!");
            }
            sceneOrder.setOrderSts(SceneOrderStsEnum.COMPLETED.getValue());
            sceneOrder.setLastUpdatedBy(TokenHelper.getUserName());
            sceneOrder.setLastUpdatedTime(new Date());
            orderAccountService.sceneOrderBranchAccount(sceneOrderSelect.getUuid(),OrderTypeEnum.SCENE_SERVICE.getValue());
            sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
            try {
                Map<String, String> param = new HashMap<>();
                param.put("site", sceneOrderSelect.getDetailedAddr());
                param.put("pingpai", sceneOrderSelect.getBrandName());
                param.put("chexing", sceneOrderSelect.getCarModelName());
                messageFeign.sendMsg("3007", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrder.getUuid());


                Map<String, String> param1 = new HashMap<>();
                sendSceneMsg(sceneOrderSelect, param1);
                messageFeign.sendMsg("3015", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());


            } catch (Exception e) {
                log.info("推送消息失败!!");
            }
        }

        return ResultRes.success(req.getSceneOrderUuid());
    }




    /**
     * 提交方案
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> sceneSubmitPlan(AddSceneOrderServiceReq req){

        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(req.getSceneOrderUuid());
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(sceneOrder);
        if (null == sceneOrderSelect) {
            log.error("未查询到相关信息，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if ( !sceneOrderSelect.getOrderSts().equals(SceneOrderStsEnum.SUBMIT_PLAN.getValue())) {
            log.error("订单状态异常，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException("9999","订单状态异常!");
        }



        SceneOrderServices sceneOrderServices = new SceneOrderServices();
        BeanUtils.copyProperties(req, sceneOrderServices);
        //基本检查费用
        BigDecimal basicInspectAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getBasicInspectAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getBasicInspectAmountUuid()));
        //相关线路检查费用
        BigDecimal lineInspectAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getLineInspectAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getLineInspectAmountUuid()));
        //诊断仪使用费
        BigDecimal diagnosisInstrumentAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getDiagnosisInstrumentAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getDiagnosisInstrumentAmountUuid()));
        //相关线路检查费用
        BigDecimal carSheetMetalAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getCarSheetMetalAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getCarSheetMetalAmountUuid()));
        //车辆油漆修复费用
        BigDecimal carPaintRepairAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getCarPaintRepairAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getCarPaintRepairAmountUuid()));
        //其他费用费
        BigDecimal otherAmount = new BigDecimal(StringUtils.isEmpty(queryDictName(req.getOtherAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictName(req.getOtherAmountUuid()));
        //平台订单服务费
        BigDecimal orderServiceAmount = new BigDecimal(StringUtils.isEmpty(queryDictValue(req.getOrderServiceAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictValue(req.getOrderServiceAmountUuid()));
        orderServiceAmount = orderServiceAmount.divide(new BigDecimal(100));
        sceneOrderServices.setOrderNum(sceneOrderSelect.getOrderNum());
        sceneOrderServices.setFaultDesc(req.getFaultDesc());
        sceneOrderServices.setSolution(req.getSolution());
        sceneOrderServices.setBasicInspectAmount(basicInspectAmount);
      /*  sceneOrderServices.setLineInspectAmount(lineInspectAmount);
        sceneOrderServices.setDiagnosisInstrumentAmount(diagnosisInstrumentAmount);
        sceneOrderServices.setCarSheetMetalAmount(carSheetMetalAmount);
        sceneOrderServices.setCarPaintRepairAmount(carPaintRepairAmount);*/
        sceneOrderServices.setOtherAmount(otherAmount);
        sceneOrderServices.setOrderSts(OrderStsEnum.UNPAID.getValue());
        sceneOrderServices.setUuid(UuidUtils.getUuid());
        sceneOrderServices.setCreatedBy(TokenHelper.getUserName());
        sceneOrderServices.setCreatedTime(new Date());
        sceneOrderServices.setSts(0);
        BigDecimal totalAmount = basicInspectAmount.add(lineInspectAmount).add(diagnosisInstrumentAmount).add(carSheetMetalAmount).add(carPaintRepairAmount).add(otherAmount);
        orderServiceAmount =orderServiceAmount.multiply(totalAmount).setScale(2,BigDecimal.ROUND_UP);
        if(orderServiceAmount.compareTo(BigDecimal.ZERO)<0){
            orderServiceAmount=BigDecimal.ZERO;
        }
        sceneOrderServices.setTotalAmount(totalAmount);
        sceneOrderServices.setOrderServiceAmount(orderServiceAmount);
        int insertNum = sceneOrderServicesMapper.insert(sceneOrderServices);
        if (insertNum <= 0) {
            log.error("新增现场订单失败，新增参数为：{}", JSON.toJSONString(req));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }

        sceneOrder.setTotalAmount(sceneOrderSelect.getTotalAmount().add(totalAmount));
        sceneOrder.setOrderSts(SceneOrderStsEnum.WAIT_PAYMENT.getValue());
        sceneOrder.setLastUpdatedBy(TokenHelper.getUserName());
        sceneOrder.setLastUpdatedTime(new Date());
        sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
        AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
        addOrderInfoReq.setUuid(UuidUtils.getUuid());
        addOrderInfoReq.setOrderType(OrderTypeEnum.SCENE_SERVICE.getValue());
        addOrderInfoReq.setOrderUuid(sceneOrderServices.getUuid());
        orderInfoService.addOrder(addOrderInfoReq);
        try {
            Map<String, String> param = new HashMap<>();
            param.put("site", sceneOrderSelect.getDetailedAddr());
            param.put("pingpai", sceneOrderSelect.getBrandName());
            param.put("chexing", sceneOrderSelect.getCarModelName());
            messageFeign.sendMsg("3005", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrder.getUuid());


            Map<String, String> param1 = new HashMap<>();
            sendSceneMsg(sceneOrderSelect, param1);
            messageFeign.sendMsg("3012", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());


        } catch (Exception e) {
            e.printStackTrace();
            log.info("推送消息失败!!");
        }
        return  ResultRes.success(sceneOrderServices.getUuid());
    }


    /**
     * 提交方案
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> sceneSubmitPlanTwo(AddSceneOrderServiceTwoReq req){

        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(req.getSceneOrderUuid());
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(sceneOrder);
        if (null == sceneOrderSelect) {
            log.error("未查询到相关信息，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if ( !sceneOrderSelect.getOrderSts().equals(SceneOrderStsEnum.SUBMIT_PLAN.getValue())) {
            log.error("订单状态异常，订单uuid为：{}", req.getSceneOrderUuid());
            throw new BusinessException("9999","订单状态异常!");
        }

        SceneOrderServices sceneOrderServices = new SceneOrderServices();
        BeanUtils.copyProperties(req, sceneOrderServices);
        //平台订单服务费
        BigDecimal orderServiceAmount = new BigDecimal(StringUtils.isEmpty(queryDictValue(req.getOrderServiceAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictValue(req.getOrderServiceAmountUuid()));
        orderServiceAmount = orderServiceAmount.divide(new BigDecimal(100));
        sceneOrderServices.setOrderNum(sceneOrderSelect.getOrderNum());
        sceneOrderServices.setFaultDesc(req.getFaultDesc());
        sceneOrderServices.setSolution(req.getSolution());
        sceneOrderServices.setOrderSts(OrderStsEnum.UNPAID.getValue());
        sceneOrderServices.setUuid(UuidUtils.getUuid());
        sceneOrderServices.setCreatedBy(TokenHelper.getUserName());
        sceneOrderServices.setCreatedTime(new Date());
        sceneOrderServices.setSts(0);
        BigDecimal totalAmount = req.getBasicInspectAmount().add(req.getOtherAmount()).add(req.getRepairAmount());
        orderServiceAmount =orderServiceAmount.multiply(totalAmount).setScale(2,BigDecimal.ROUND_UP);
        if(orderServiceAmount.compareTo(BigDecimal.ZERO)<0){
            orderServiceAmount=BigDecimal.ZERO;
        }
        sceneOrderServices.setTotalAmount(totalAmount);
        sceneOrderServices.setOrderServiceAmount(orderServiceAmount);
        int insertNum = sceneOrderServicesMapper.insert(sceneOrderServices);

        if (insertNum <= 0) {
            log.error("新增现场订单失败，新增参数为：{}", JSON.toJSONString(req));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }
        //添加服务图片视频
        insertSceneOrderDtcImg(req.getImageList(), TokenHelper.getUserName(), sceneOrderServices.getUuid(),ImageTypeEnum.SERVICE.getValue());
        //修改主订单状态
        sceneOrder.setTotalAmount(sceneOrderSelect.getTotalAmount().add(totalAmount));
        sceneOrder.setOrderSts(SceneOrderStsEnum.WAIT_PAYMENT.getValue());
        sceneOrder.setLastUpdatedBy(TokenHelper.getUserName());
        sceneOrder.setLastUpdatedTime(new Date());
        sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
        //创建支付订单
        AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
        addOrderInfoReq.setUuid(UuidUtils.getUuid());
        addOrderInfoReq.setOrderType(OrderTypeEnum.SCENE_SERVICE.getValue());
        addOrderInfoReq.setOrderUuid(sceneOrderServices.getUuid());
        orderInfoService.addOrder(addOrderInfoReq);
        try {
            Map<String, String> param = new HashMap<>();
            param.put("site", sceneOrderSelect.getDetailedAddr());
            param.put("pingpai", sceneOrderSelect.getBrandName());
            param.put("chexing", sceneOrderSelect.getCarModelName());
            messageFeign.sendMsg("3005", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrder.getUuid());


            Map<String, String> param1 = new HashMap<>();
            sendSceneMsg(sceneOrderSelect, param1);
            messageFeign.sendMsg("3012", param1, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());


        } catch (Exception e) {
            e.printStackTrace();
            log.info("推送消息失败!!");
        }
        return  ResultRes.success(sceneOrderServices.getUuid());
    }

    /**
     * 查询现场订单列表
     * @param req
     * @return
     */
    @Override
    public PageRes<List<QuerySceneOrderListRes>> querySceneOrderList(QuerySceneOrderListReq req) {


        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        //获取当前登录用户信息
        String userUuid = TokenHelper.getUserUuid();
        //获取当前登录用户类型判断啊是否是店铺，
        Integer userType = TokenHelper.getUserType();
        if(UserTypeEnum.store.getType().equals(userType)){
            userUuid =getStoremMsterUser(userUuid);
        }
        //查询登录人经纬度
        LaAndLoDto laAndLoDto = queryUserLaAndLo(userUuid);
        List<QuerySceneOrderListRes> querySceneOrderListResList = new ArrayList<>();
        if (GrabOrdersStsEnum.not_grab.getValue().equals(req.getQueryType())) {
            OrderSceneDto orderSceneDto = new OrderSceneDto();
            if(UserTypeEnum.store.getType().equals(userType)){
                StoreDetailRes storeDetailRes = storeFegin.queryStoreDetailByUserUuid(userUuid).getData();
                List<String> list  = storeFegin.queryStoreBrand(storeDetailRes.getUuid()).getData();
                orderSceneDto.setBrands(list);
                orderSceneDto.setAddressCity(storeDetailRes.getCompanyAddressCity());
            }else {
                TechnicianRes  technicianRes= technicianFegin.queryTechnicianDetail(userUuid).getData();
                List<String> list = technicianFegin.queryTechnicianBrand(technicianRes.getUuid()).getData();
                orderSceneDto.setAddressCity(technicianRes.getAddressCity());
                orderSceneDto.setBrands(list);
                orderSceneDto.setTechnologyType(technicianRes.getTechnologyType());
            }
            log.info("-------------------"+orderSceneDto.toString());
            //查询未抢现场订单
            querySceneOrderListResList = sceneOrderMapper.querySceneOrderList(req, null, GrabOrdersStsEnum.not_grab.getValue(), null, laAndLoDto, SceneOrderStsEnum.HAVE_PAID.getValue(),null,orderSceneDto);
            for(Iterator<QuerySceneOrderListRes> it=querySceneOrderListResList.iterator();it.hasNext();){
                QuerySceneOrderListRes  str=it.next();
                if(userUuid.equals(str.getIssuerUuid())) {
                    it.remove();
                }
            }
        } else if (GrabOrdersStsEnum.grab.getValue().equals(req.getQueryType())) {
            //查询用户已抢订单
            querySceneOrderListResList = sceneOrderMapper.querySceneOrderList(req, null, GrabOrdersStsEnum.grab.getValue(), userUuid, laAndLoDto, null,req.getStatus(),null);
        } else if (GrabOrdersStsEnum.RELEASE.getValue().equals(req.getQueryType())) {
            //查询用户发布订单
            querySceneOrderListResList = sceneOrderMapper.querySceneOrderList(req, userUuid, null, null, laAndLoDto, null,req.getStatus(),null);
        } else {
            //查询全部
            querySceneOrderListResList = sceneOrderMapper.querySceneOrderList(req, null, null, null, laAndLoDto, null,null,null);
        }

        PageInfo<QuerySceneOrderListRes> pageInfo = new PageInfo<>(querySceneOrderListResList);
        List<QuerySceneOrderListRes> resList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(querySceneOrderListResList)) {
            for (QuerySceneOrderListRes res : querySceneOrderListResList) {
                Boolean flag = false;
                if (userUuid.equals(res.getIssuerUuid())) {
                    flag = true;
                }
                res.setDistance(res.getDistance() / 1000);
                res.setIsOneself(flag);
                resList.add(res);
            }
        }
        return PageRes.success(resList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 根据当前登陆人Id
     * @return
     */
    private String getStoremMsterUser(String userUuid){
        log.info("当前登陆人uuid"+userUuid);
        ResultRes<StoreUserRes> resResultRes = storeUserFeign.queryStoreUserInfo(userUuid);
        if (resResultRes.isSuccess()){
            StoreUserRes storeUserRes = resResultRes.getData();
            if (!"501".equals(storeUserRes.getPersonType())){

                ResultRes<StoreUserRes> res = storeUserFeign.getStoreUset(storeUserRes.getStoreUuid());
                log.info("当前登陆人主账户uuid"+res.getData().getUuid());
                return  res.getData().getUuid();
            }
        }
        return userUuid;
    }
    /**
     * 查询现场订单列表
     * @param req
     * @return
     */
    @Override
    public PageRes<List<QuerySceneOrderListRes>> querySceneOrderLists(QuerySceneOrderListsReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        //获取当前登录用户信息
         List<QuerySceneOrderListRes>  querySceneOrderListResList = sceneOrderMapper.querySceneOrderLists(req.getOrderSts());
        PageInfo<QuerySceneOrderListRes> pageInfo = new PageInfo<>(querySceneOrderListResList);
        return PageRes.success(querySceneOrderListResList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 查看现场订单详情
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<QuerySceneOrderInfoRes> querySceneOrderInfo(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            log.info("查询现场的订单详情uuid不能为空");
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        //获取当前登录人uuid
        String userUuid = TokenHelper.getUserUuid();
        QuerySceneOrderInfoRes res = new QuerySceneOrderInfoRes();
        SceneOrderDto sceneOrder = sceneOrderMapper.querySceneOrderInfo(uuid);
        Integer userType = TokenHelper.getUserType();
        if(UserTypeEnum.store.getType().equals(userType)){
            userUuid =getStoremMsterUser(userUuid);
        }
        //现场下单dtc图片
        List<String> dtcImageList = new ArrayList<>();
        //现场订单技师相关dtc图片
        List<String> faultImageList = new ArrayList<>();
        Boolean flag = false;
        if (null != sceneOrder) {
            BeanUtils.copyProperties(sceneOrder, res);
            //变速器一级
            if (!StringUtils.isEmpty(sceneOrder.getTransmissionOneLevelUuid())) {
                res.setTransmissionOneLevel(queryDictName(sceneOrder.getTransmissionOneLevelUuid()));
            }
            //变速器二级
            if (!StringUtils.isEmpty(sceneOrder.getTransmissionTwoLevelUuid())) {
                res.setTransmissionTwoLevel(queryDictName(sceneOrder.getTransmissionTwoLevelUuid()));
            }
            //发动机排量
            if (!StringUtils.isEmpty(sceneOrder.getEngineDisplacementUuid())) {
                res.setEngineDisplacement(queryDictName(sceneOrder.getEngineDisplacementUuid()));
            }
            //驱动方式
            if (!StringUtils.isEmpty(sceneOrder.getDrivingModeUuid())) {
                res.setDrivingMode(queryDictName(sceneOrder.getDrivingModeUuid()));
            }
            //增压系统
            if (!StringUtils.isEmpty(sceneOrder.getBoosterSystemUuid())) {
                res.setBoosterSystem(queryDictName(sceneOrder.getBoosterSystemUuid()));
            }
            //维修类型
            if (!StringUtils.isEmpty(sceneOrder.getRepairTypeUuid())) {
                res.setRepairType(queryDictName(sceneOrder.getRepairTypeUuid()));
            }
            //查询现场下单订单dtc图片
            dtcImageList = sceneOrderDtcImagesMapper.queryList(sceneOrder.getUuid(),ImageTypeEnum.DTC.getValue());
            //查询现场下单订单dtc图片
            faultImageList = sceneOrderDtcImagesMapper.queryList(sceneOrder.getUuid(),ImageTypeEnum.FAULT_DESC.getValue());
            if (userUuid.equals(sceneOrder.getIssuerUuid())) {
                flag = true;
            }
            if(!StringUtils.isEmpty(sceneOrder.getSceneOrderTechnicianUuid())) {
                SceneOrderTechnician sceneOrderTechnician = sceneOrderTechnicianMapper.selectByPrimaryKey(sceneOrder.getSceneOrderTechnicianUuid());
                SceneOrderTechnicianRes sceneOrderTechnicianRes = new SceneOrderTechnicianRes();
                BeanUtils.copyProperties(sceneOrderTechnician, sceneOrderTechnicianRes);
                res.setSceneOrderTechnicianRes(sceneOrderTechnicianRes);
                List<String> lists =sceneOrderDtcImagesMapper.queryList(sceneOrderTechnician.getUuid(),ImageTypeEnum.SHOW_UP.getValue());
                if(lists!=null&&lists.size()>0) {
                    res.getSceneOrderTechnicianRes().setDoorImageList(lists);
                }
                List<String> list = sceneOrderDtcImagesMapper.queryList(sceneOrderTechnician.getUuid(),ImageTypeEnum.SERVICE_END.getValue());
                if(list!=null&&list.size()>0) {
                res.getSceneOrderTechnicianRes().setEndImageList(list);
                }
            }
            SceneOrderServices sceneOrderServices = new SceneOrderServices();
            sceneOrderServices.setOrderNum(sceneOrder.getOrderNum());
            sceneOrderServices = sceneOrderServicesMapper.selectOne(sceneOrderServices);
            if(sceneOrderServices!=null) {
                SceneOrderServiceTwoRes sceneOrderServiceRes = new SceneOrderServiceTwoRes();
                List<String> list = sceneOrderDtcImagesMapper.queryList(sceneOrderServices.getUuid(),ImageTypeEnum.SERVICE.getValue());
                BeanUtils.copyProperties(sceneOrderServices, sceneOrderServiceRes);
                sceneOrderServiceRes.setImageList(list);
                res.setSceneOrderServiceRes(sceneOrderServiceRes);
            }
        }
        res.setIsOneself(flag);
        res.setDtcImageList(dtcImageList);
        res.setFaultImageList(faultImageList);
        return ResultRes.success(res);
    }

    /**
     * 现场订单抢单
     * @param sceneOrderUuid
     * @return
     */
    @Override
    public ResultRes<String> grabbingOrders(String sceneOrderUuid) {
        if (StringUtils.isEmpty(sceneOrderUuid)) {
            log.error("现场订单抢单时，订单uuid不能为空");
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        //获取当前登录用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //获取当前登录用户名称
        String userName = TokenHelper.getUserName();
        Integer userType = TokenHelper.getUserType();
        LoginToken loginToken = TokenHelper.getLoginToken();
        //获取当前登录用户手机号
        String phone = null;
        if (null != loginToken) {
            phone = loginToken.getUserMobile();
        }
        if(UserTypeEnum.store.getType().equals(userType)){
            ResultRes<StoreDetailRes> resResultRes =  storeFegin.queryStoreDetailByUser();
            userName = resResultRes.getData().getStoreName();
        }
        //查询现场订单详情
        SceneOrder querySceneOrder = new SceneOrder();
        querySceneOrder.setUuid(sceneOrderUuid);
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(querySceneOrder);
        if (null != sceneOrderSelect) {
            if (userUuid.equals(sceneOrderSelect.getIssuerUuid())) {
                log.error("此订单为本人订单，禁止抢单，订单uuid为：{}", sceneOrderUuid);
                throw new BusinessException(ResEnum.SCENE_ORDER_ONESELF_ERROR);
            }
        } else {
            log.error("未查询到相关信息，订单uuid为：{}", sceneOrderUuid);
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if(sceneOrderSelect.getGrabbingOrdersSts().equals(GrabOrdersStsEnum.grab.getValue())){
            log.error("该订单已经被抢，订单uuid为：{}", sceneOrderUuid);
            throw new BusinessException("9999","该订单已经被抢！");
        }
        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(sceneOrderUuid);
        sceneOrder.setBuyerUuid(userUuid);
        sceneOrder.setBuyerName(userName);
        sceneOrder.setBuyerMobile(phone);
        sceneOrder.setGrabbingOrdersSts(GrabOrdersStsEnum.grab.getValue());
        sceneOrder.setOrderSts(SceneOrderStsEnum.UNPAID.getValue());
        sceneOrder.setLastUpdatedBy(userName);
        sceneOrder.setLastUpdatedTime(new Date());
        sceneOrder.setBuyerUserType(TokenHelper.getUserType());
        sceneOrder.setGrabbingDate(DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS));

        AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
        addOrderInfoReq.setUuid(UuidUtils.getUuid());
        addOrderInfoReq.setOrderType(OrderTypeEnum.SCENE.getValue());
        addOrderInfoReq.setOrderUuid(sceneOrder.getUuid());
        orderInfoService.addOrder(addOrderInfoReq);
        int updateNum = sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
        if (updateNum <= 0 ) {
            log.error("抢单失败，请求参数为：{}", JSON.toJSONString(sceneOrder));
            throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
        }
        try {
            Map<String, String> param = new HashMap<>();
            param.put("site", sceneOrderSelect.getDetailedAddr());
            param.put("pingpai", sceneOrderSelect.getBrandName());
            param.put("chexing", sceneOrderSelect.getCarModelName());
            messageFeign.sendMsg("3002", param, sceneOrderSelect.getIssuerUuid(), sceneOrderSelect.getIssuerType(),sceneOrder.getUuid());

            Map<String, String> param1 = new HashMap<>();
            sendSceneMsg(sceneOrder, param1);
            messageFeign.sendMsg("3008", param1, sceneOrder.getBuyerUuid(), sceneOrder.getBuyerUserType(),sceneOrder.getUuid());

        } catch (Exception e) {
            log.info("推送消息失败!!");
        }
        return ResultRes.success(sceneOrderUuid);
    }

    private void sendSceneMsg(SceneOrder sceneOrderSelect, Map<String, String> param) {
        if (UserTypeEnum.store.getType().equals(sceneOrderSelect.getBuyerUserType())) {
            ResultRes<StoreDetailRes> storeDetailResResultRes = storeFegin.queryStoreDetailByUserUuid(sceneOrderSelect.getBuyerUuid());
            param.put("technicianName", storeDetailResResultRes.getData().getStoreName());
            param.put("type", UserTypeEnum.store.getDesc());
        } else {
            param.put("technicianName", sceneOrderSelect.getBuyerName());
            param.put("type", UserTypeEnum.technician.getDesc());
        }
    }

    /**
     * 新增现场订单
     * @param req
     * @return
     */
    private SceneOrder insertSceneOrder (AddSceneOrderReq req, String userName, String mobile, String userUuid,Integer userType) {
        //查询当前登录人经纬度
        LaAndLoDto laAndLoDto = queryUserLaAndLo(userUuid);
        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setLatitude(laAndLoDto.getLatitude());
        sceneOrder.setLongitude(laAndLoDto.getLongitude());
        BeanUtils.copyProperties(req, sceneOrder);
        sceneOrder.setUuid(UuidUtils.getUuid());
        sceneOrder.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.XC));
        sceneOrder.setBrandName(queryVehicleConfig(req.getBrandUuid()));
        sceneOrder.setCarModelName(queryVehicleConfig(req.getCarModelUuid()));
        //平台服务费

        BigDecimal orderServiceAmount = new BigDecimal(StringUtils.isEmpty(queryDictValue(req.getOrderServiceAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictValue(req.getOrderServiceAmountUuid()));

        //基本检查费用
        BigDecimal amount = new BigDecimal(StringUtils.isEmpty(queryDictValue(req.getBasicDoorAmountUuid())) ? BigDecimal.ZERO.toString() : queryDictValue(req.getBasicDoorAmountUuid()));
        BigDecimal basicDoorAmount = amount;
        orderServiceAmount = orderServiceAmount.divide(new BigDecimal(100)).multiply(amount);
        if (basicDoorAmount.compareTo(BigDecimal.ZERO)>1 && basicDoorAmount.compareTo(orderServiceAmount)>0){
              basicDoorAmount = basicDoorAmount.subtract(orderServiceAmount);
        }
        sceneOrder.setBasicDoorAmount(basicDoorAmount);
        sceneOrder.setBasicDoorAmountUuid(req.getBasicDoorAmountUuid());
        sceneOrder.setOrderServiceAmount(orderServiceAmount);
        sceneOrder.setOrderServiceAmountUuid(req.getOrderServiceAmountUuid());
        sceneOrder.setIssuerType(userType);
        sceneOrder.setIssuerUuid(userUuid);
        sceneOrder.setIssuerName(userName);
        sceneOrder.setIssuerMobile(mobile);
        sceneOrder.setGrabbingOrdersSts(GrabOrdersStsEnum.not_grab.getValue());
        sceneOrder.setSts(StsEnum.ACTIVE.getValue());
        sceneOrder.setOrderSts(SceneOrderStsEnum.HAVE_PAID.getValue());
        sceneOrder.setCreatedBy(userName);
        sceneOrder.setCreatedTime(new Date());
        sceneOrder.setTotalAmount(amount);
        int insertNum = sceneOrderMapper.insert(sceneOrder);
        if (insertNum <= 0) {
            log.error("新增现场订单失败，新增参数为：{}", JSON.toJSONString(sceneOrder));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }
        return sceneOrder;
    }

    /**
     * 查询当前登录人经纬度信息
     * @param userUuid
     * @return
     */
    private LaAndLoDto queryUserLaAndLo (String userUuid) {
        //当前登录用户用户类型
        Integer userType = TokenHelper.getUserType();
        LaAndLoDto laAndLoDto = new LaAndLoDto();
        if (UserTypeEnum.vehicle.getType().equals(userType)) {
            //查询车主信息
            ResultRes<VehicleUserRes> resResultRes = vehicleFegin.queryDetail(userUuid);
            if (resResultRes.isSuccess()) {
                if (null != resResultRes.getData()) {
                    laAndLoDto.setLongitude(resResultRes.getData().getLongitude());
                    laAndLoDto.setLatitude(resResultRes.getData().getLatitude());
                }
            }
        } else if (UserTypeEnum.technician.getType().equals(userType)) {
            //查询技师信息
            ResultRes<TechnicianRes> resResultRes = technicianFegin.queryTechnicianDetail(userUuid);
            if (resResultRes.isSuccess()) {
                if (null != resResultRes.getData()) {
                    if (null != resResultRes.getData().getAddressLatitude()) {
                        laAndLoDto.setLatitude(resResultRes.getData().getAddressLatitude());
                    }
                    if (null != resResultRes.getData().getAddressLongitude()) {
                        laAndLoDto.setLongitude(resResultRes.getData().getAddressLongitude());
                    }

                }
            }
        } else if (UserTypeEnum.store.getType().equals(userType)) {
            //查询店铺联系人信息
            ResultRes<StoreUserRes> resResultRes = storeUserFeign.queryStoreUserInfo(userUuid);
            if (resResultRes.isSuccess()) {
                if (null != resResultRes.getData()) {
                    //查询店铺信息
                    ResultRes<StoreDetailRes> storeDetailResResultRes = storeFegin.queryStoreDetail(resResultRes.getData().getStoreUuid());
                    if (storeDetailResResultRes.isSuccess()) {
                        if (null != storeDetailResResultRes.getData()) {
                            if (null != storeDetailResResultRes.getData().getLongitude()) {
                                laAndLoDto.setLongitude(storeDetailResResultRes.getData().getLongitude());
                            }
                            if (null != storeDetailResResultRes.getData().getLatitude()) {
                                laAndLoDto.setLatitude(storeDetailResResultRes.getData().getLatitude());
                            }

                        }
                    }
                }
            }
        }
        return laAndLoDto;
    }

    /**
     * 根据字典uuid，查询字典名称
     * @return
     */
    private String queryDictName (String dictUuid) {
        String dictName = null;
        if (StringUtils.isEmpty(dictUuid)) {
            return dictName;
        }
        //根据字典uuid查询字典信息
        ResultRes<DictionaryRes> resResultRes = systemFeign.queryByUuid(dictUuid);
        if (resResultRes.isSuccess()) {
            if (null != resResultRes.getData()) {
                dictName = resResultRes.getData().getLableDesc();
            }
        }
        return dictName;
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
     * 根据uuid查询车辆类型/品牌/型号配置表配置名称
     * @param vehicleConfigUuid
     * @return
     */
    private String queryVehicleConfig(String vehicleConfigUuid){
        String vehicleConfigName = null;
        if (StringUtils.isEmpty(vehicleConfigUuid)) {
            return vehicleConfigName;
        }
        ResultRes<ConfigRes> resResultRes = vehicleFegin.queryConfig(vehicleConfigUuid);
        if (resResultRes.isSuccess()) {
            if (null != resResultRes.getData()) {
                vehicleConfigName = resResultRes.getData().getConfigName();
            }
        }
        return vehicleConfigName;
    }

    /**
     * dtc图片新增
     * @param dtcImageList
     */
    @Override
    public void insertSceneOrderDtcImg (List<String> dtcImageList, String userName, String relationUuid,Integer type) {
        for (String dtcImgUrl : dtcImageList) {
            SceneOrderDtcImages sceneOrderDtcImages = new SceneOrderDtcImages();
            sceneOrderDtcImages.setUuid(UuidUtils.getUuid());
            sceneOrderDtcImages.setRelationUuid(relationUuid);
            sceneOrderDtcImages.setDtcImageUrl(dtcImgUrl);
            sceneOrderDtcImages.setType(type);
            sceneOrderDtcImages.setSts(StsEnum.ACTIVE.getValue());
            sceneOrderDtcImages.setCreatedBy(userName);
            sceneOrderDtcImages.setCreatedTime(new Date());
            int insertNum = sceneOrderDtcImagesMapper.insert(sceneOrderDtcImages);
            if (insertNum <= 0) {
                log.info("新增现场订单dtc图片失败，请求参数为：{}", JSON.toJSONString(sceneOrderDtcImages));
                throw new BusinessException(ResEnum.INSERT_DB_ERROR);
            }
        }
    }

    /**
     * 完成现场订单
     * @param sceneOrderUuid
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> completeOrder(String sceneOrderUuid) {
        if (StringUtils.isEmpty(sceneOrderUuid)) {
            log.error("完成现场订单时订单uuid不能为空");
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        ConfirmOrderReq confirmOrderReq = new ConfirmOrderReq();
        confirmOrderReq.setOrderType(OrderTypeEnum.SCENE.getValue());
        confirmOrderReq.setOrderUuid(sceneOrderUuid);
        orderInfoService.confirmOrder(confirmOrderReq);


        return ResultRes.success(sceneOrderUuid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> cancelOrderOrder(String sceneOrderUuid,Integer type){

        if (StringUtils.isEmpty(sceneOrderUuid)) {
            log.error("现场订单取消时，订单uuid不能为空");
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        SceneOrder querySceneOrder = new SceneOrder();
        querySceneOrder.setUuid(sceneOrderUuid);
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(querySceneOrder);
        //获取当前登录用户uuid
        String userUuid = TokenHelper.getUserUuid();
        String userName = TokenHelper.getUserName();
        if (null != sceneOrderSelect) {
            if (!userUuid.equals(sceneOrderSelect.getIssuerUuid())) {
                log.error("该订单不是你的，订单uuid为：{}", sceneOrderUuid);
                throw new BusinessException(ResEnum.SCENE_ORDER_ONESELF_ERROR);
            }
        } else {
            log.error("未查询到相关信息，订单uuid为：{}", sceneOrderUuid);
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if (SceneOrderStsEnum.HAVE_PAID.getValue().equals(sceneOrderSelect.getOrderSts())||SceneOrderStsEnum.UNPAID.getValue().equals(sceneOrderSelect.getOrderSts())||SceneOrderStsEnum.WAIT_PAYMENT.getValue().equals(sceneOrderSelect.getOrderSts())){
            if(type.equals(1)) {
                querySceneOrder.setOrderSts(SceneOrderStsEnum.CANCELED.getValue());
            }else  if(type.equals(2)) {
                querySceneOrder.setOrderSts(SceneOrderStsEnum.HAVE_PAID_CANCELED.getValue());
            }else if(type.equals(3)) {
                querySceneOrder.setOrderSts(SceneOrderStsEnum.SERVICE_CANCELED.getValue());
            }
            querySceneOrder.setLastUpdatedBy(userName);
            querySceneOrder.setLastUpdatedTime(new Date());
            sceneOrderMapper.updateByPrimaryKeySelective(querySceneOrder);
        }else {
            log.error("未查询到相关信息，订单uuid为：{}", sceneOrderUuid);
            throw new BusinessException("9999","订单状态异常,取消失败");
        }
        return ResultRes.success(sceneOrderUuid);
    }

    @Override
    public ResultRes<StatisticsSceneOrderRes> sceneOrderStatistics(Integer year, Integer month) {
        String userUuId = TokenHelper.getUserUuid();
        StatisticsSceneOrderRes statisticsSceneOrderRes = new StatisticsSceneOrderRes();
        statisticsSceneOrderRes.setNumList( sceneOrderServicesMapper.sceneOrderStatisticsNum(year,month,userUuId));
        statisticsSceneOrderRes.setTotal(sceneOrderServicesMapper.statisticsSceneOrder(userUuId));
        SceneOrderProfitReq sceneOrderProfitReq = new SceneOrderProfitReq();
        sceneOrderProfitReq.setMonth(month);
        sceneOrderProfitReq.setYear(year);
        statisticsSceneOrderRes.setAmountList(profitStreamFeign.statisticsAmountByType(sceneOrderProfitReq).getData());
        ProfitStreamReq profitStreamReq = new ProfitStreamReq();
        profitStreamReq.setClassify("9,11");
        profitStreamReq.setUserUuid(userUuId);
        profitStreamReq.setStreamType(StreamTypeEnum.IN.getType());
        statisticsSceneOrderRes.setTotalAmount(profitStreamFeign.statisticsAmount(profitStreamReq).getData());
        return ResultRes.success(statisticsSceneOrderRes);
    }

    /**
     *
     * @param sceneOrderUuid
     * @return
     */
    @Override
    public ResultRes<String> reminderOrders( String sceneOrderUuid){
        SceneOrder sceneOrder = new SceneOrder();
        sceneOrder.setUuid(sceneOrderUuid);
        SceneOrder sceneOrderSelect = sceneOrderMapper.selectOne(sceneOrder);
        try {
            Map<String, String> param = new HashMap<>();
            sendSceneMsg(sceneOrderSelect, param);
            messageFeign.sendMsg("3016", param, sceneOrderSelect.getBuyerUuid(), sceneOrderSelect.getBuyerUserType(),sceneOrder.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("推送消息失败!!");
        }
        return ResultRes.success();
    }
}
