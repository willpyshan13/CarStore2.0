package com.car.order.web.controller.share;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.scene.AddSceneOrderServiceReq;
import com.car.order.client.request.scene.SceneOrderConfirmReq;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;
import com.car.order.client.request.technicianappointment.*;
import com.car.order.client.response.technicianappointment.ShareTechnicianOrderInfoRes;
import com.car.order.client.response.technicianappointment.ShareTechnicianOrderRes;
import com.car.order.web.service.technicianappointment.ShareTechnicianOrderService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 技师预约订单管理
 *
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.web.controller
 * @NAME: TechnicianAppointmentOrderController
 * @DATE: 2021/3/4 20:59
 */
@Slf4j
@Api(value = "ShareTechnicianOrderController", tags = "技师预约订单管理---前端调用")
@RequestMapping(value = "/shareTechnicianOrder", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class ShareTechnicianOrderController {


    @Autowired
    ShareTechnicianOrderService shareTechnicianOrderService;


    @PostMapping(value = "/saveShareTechnicianOrder")
    @ApiOperation(value = "新增预约技师订单", nickname = "saveShareTechnicianOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.ADD, operDesc = "新增技师预约订单")
    public ResultRes<String> saveShareTechnicianOrder(@RequestBody @Valid ShareTechnicianOrderReq req) {
        return shareTechnicianOrderService.saveTechnicianAppointment(req);
    }




    @PutMapping(value = "/receiveShareTechnicianOrder/{uuid}")
    @ApiOperation(value = "技师同意接单", nickname = "receiveShareTechnicianOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "技师接单")
    public ResultRes receiveShareTechnicianOrder(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.receiveShareTechnicianOrder(uuid);
    }

    @GetMapping(value = "/refuseOrder/{uuid}")
    @ApiOperation(value = "技师拒绝订单", nickname = "cancelOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "拒绝订单")
    public ResultRes refuseOrder(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.refuseOrder(uuid);
    }

    @GetMapping(value = "/cancelOrder/{uuid}")
    @ApiOperation(value = "客户取消订单", nickname = "cancelOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "取消订单")
    public ResultRes cancelOrder(@PathVariable(name = "uuid") String uuid,
                                 @ApiParam(value = "取消类型1 直接取消，2 接单后取消 3 提交服务方案后取消", required = true)@RequestParam("type") Integer type){
        return shareTechnicianOrderService.cancelOrder(uuid, type);
    }


    @PostMapping(value = "/shareOrderDescribe")
    @ApiOperation(value = "共享技师--技师提交说明", nickname = "shareOrderDescribe")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.ADD, operDesc = "共享技师订单详情")
    public ResultRes<String> shareOrderDescribe(@RequestBody ShareTechnicianDetailReq shareTechnicianDetailReq) {
        return shareTechnicianOrderService.shareOrderDescribe( shareTechnicianDetailReq);
    }


    @PostMapping(value = "/shareOrderConfirm")
    @ApiOperation(value = "共享技师-客户确定", nickname = "shareOrderConfirm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "共享技师管理", operType = OperEnum.UPDATE, operDesc = "客户确定")
    public ResultRes<String> shareOrderConfirm(@RequestBody @Valid ShareOrderConfirmReq req){
        return shareTechnicianOrderService.shareOrderConfirm(req);
    }

    @PostMapping(value = "/sceneSubmitPlan")
    @ApiOperation(value = "共享技师--提交维修方案", nickname = "sceneSubmitPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "共享技师管理", operType = OperEnum.UPDATE, operDesc = "提交维修方案")
    public ResultRes<String> sceneSubmitPlan(@RequestBody @Valid AddShareOrderServiceReq req){
        //log.info("sceneSubmitPlan---------"+ JSON.toJSONString(req));
        return shareTechnicianOrderService.sceneSubmitPlan(req);
    }

    @PutMapping(value = "/updateShareTechnicianOrder/{uuid}")
    @ApiOperation(value = "完成预约订单状态", nickname = "updateShareTechnicianOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "完成预约订单状态")
    public ResultRes updateShareTechnicianOrder(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.updateShareTechnicianOrder(uuid);
    }

    @PutMapping(value = "/applicationRefundShareTechnicianOrder/{uuid}")
    @ApiOperation(value = "申请退款预约订单", nickname = "applicationRefundShareTechnicianOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "申请退款预约订单")
    public ResultRes applicationRefundShareTechnicianOrder(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.applicationRefundShareTechnicianOrder(uuid);
    }
    @PostMapping(value = "/queryShareTechnicianOrderList")
    @ApiOperation(value = "查询预约订单列表", nickname = "queryShareTechnicianOrderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.SELECT, operDesc = "查询预约订单列表")
    public PageRes<List<ShareTechnicianOrderRes>> queryShareTechnicianOrderList(@RequestBody @Validated QueryShareTechnicianOrderReq req) {
        return shareTechnicianOrderService.queryShareTechnicianOrderList(req);
    }

    @GetMapping(value = "/queryShareTechnicianOrder/{uuid}")
    @ApiOperation(value = "查询预约订单详情", nickname = "queryShareTechnicianOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.SELECT, operDesc = "查询预约订单详情")
    public ResultRes<ShareTechnicianOrderInfoRes> queryShareTechnicianOrder(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.queryShareTechnicianOrder(uuid);
    }



    @PostMapping(value = "/shareTechnicianOrderStatistics")
    @ApiOperation(value = "预约技师订单统计", nickname = "shareTechnicianOrderStatistics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @SysOperLog(operModul = "技师预约订单管理", operType = OperEnum.UPDATE, operDesc = "预约技师订单统计")
    public ResultRes shareTechnicianOrderStatistics(@RequestBody SceneOrderStatisticsReq sceneOrderStatisticsReq) {
        return shareTechnicianOrderService.shareTechnicianOrderStatistics(sceneOrderStatisticsReq);
    }

    @PostMapping(value = "/statisticsShareOrderApi/{uuid}")
    public ResultRes statisticsShareOrderApi(@PathVariable(name = "uuid") String uuid) {
        return shareTechnicianOrderService.statisticsShareOrderApi(uuid);
    }

}
