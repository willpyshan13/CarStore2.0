package com.car.order.web.service.front.content.impl;

import com.alibaba.fastjson.JSON;
import com.car.account.client.feign.VehicleFegin;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.car.order.client.enums.consult.AnswerStsEnum;
import com.car.order.client.enums.consult.ConsultImgTypenum;
import com.car.order.client.enums.front.QueryStateEnum;
import com.car.order.client.enums.goods.EvaluateStsEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.ServiceStsEnum;
import com.car.order.client.feign.ScoreFeign;
import com.car.order.client.request.order.consult.QueryOrderConsultFrontListReq;
import com.car.order.client.response.order.consult.CaseDetails;
import com.car.order.client.response.order.consult.ConsultOrderFrontRes;
import com.car.order.client.response.order.consult.ConsultRes;
import com.car.order.client.response.order.consult.OrderConsultFrontListRes;
import com.car.order.web.dto.OrderConsultDto;
import com.car.order.web.mapper.consult.ConsultImagesMapper;
import com.car.order.web.mapper.content.ContentMapper;
import com.car.order.web.mapper.consult.OrderConsultFrontMapper;
import com.car.order.web.service.consult.ConsultService;
import com.car.order.web.service.front.content.ContentFrontService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouz
 * @date 2020/12/28
 */
@Slf4j
@Service
public class ContentFrontServiceImpl implements ContentFrontService {

    @Autowired
    OrderConsultFrontMapper orderConsultFrontMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    ConsultImagesMapper consultImagesMapper;

    @Autowired
    VehicleFegin vehicleFegin;

    @Autowired
    ConsultService consultService;


    /**
     * 查询付费咨询订单列表
     * @param param
     * @return
     */
    @Override
    public PageRes<List<OrderConsultFrontListRes>> queryOrderConsultList(QueryOrderConsultFrontListReq param) {
        log.info("查询付费咨询订单列表"+ JSON.toJSONString(param));
        log.debug("查询付费咨询订单列表");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        OrderConsultDto orderConsultDto = new OrderConsultDto();

        if(param.getQuizzer()!=null && param.getQuizzer()==1){
            orderConsultDto.setTechnicianUuid(TokenHelper.getUserUuid());
            orderConsultDto.setConsultType((byte) 1);
            orderConsultDto.setConsultCheckSts(1);
        }else if(param.getQuizzer()!=null && param.getQuizzer()==2){
            orderConsultDto.setCarOwnerUuid(TokenHelper.getUserUuid());//我提问题的
        }else if(param.getQuizzer()!=null && param.getQuizzer()==3){
            orderConsultDto.setTechnicianUuid(TokenHelper.getUserUuid());
            orderConsultDto.setConsultType((byte) 2);
            //orderConsultDto.setConsultCheckSts(1);
        }

        if (QueryStateEnum.UNPAID.getValue().equals(param.getState())) {
            //查询待付款订单
            orderConsultDto.setOrderSts(OrderStsEnum.UNPAID.getValue());
        } else if (QueryStateEnum.STAY_SERVICE.getValue().equals(param.getState())) {
            //查询待服务订单（已支付订单与未服务订单）
            List<Integer> list = new ArrayList<>();
            list.add(OrderStsEnum.UNPAID.getValue());
            list.add(OrderStsEnum.HAVE_PAID.getValue());
            list.add(OrderStsEnum.A_REFUND_OF.getValue());
            orderConsultDto.setOrderStsList(list);
            //orderConsultDto.setServiceSts(ServiceStsEnum.NOT_SERVICE.getValue());
        } else if (QueryStateEnum.STAY_EVALUATE.getValue().equals(param.getState())) {
            //查询待评价订单
            //orderConsultDto.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
            List<Integer> list = new ArrayList<>();
            list.add(OrderStsEnum.COMPLETED.getValue());
            //list.add(OrderStsEnum.HAVE_PAID.getValue());
            list.add(OrderStsEnum.CANCELED.getValue());
            list.add(OrderStsEnum.REFUND_SUCCESS.getValue());//退款成功加入完成
            orderConsultDto.setOrderStsList(list);
            //orderConsultDto.setEvaluateSts(EvaluateStsEnum.NO_COMMENT.getValue());
            //orderConsultDto.setServiceSts(ServiceStsEnum.ALREADY_SERVICE.getValue());
        } else if (QueryStateEnum.REFUND.getValue().equals(param.getState())) {
            //查询退款订单
            List<Integer> list = new ArrayList<>();
            list.add(OrderStsEnum.A_REFUND_OF.getValue());
            list.add(OrderStsEnum.REFUND_SUCCESS.getValue());
            list.add(OrderStsEnum.REFUND_FAILURE.getValue());
            orderConsultDto.setOrderStsList(list);
        }
        log.info("orderConsultDto传输内容"+orderConsultDto.toString());
        /*orderConsultDto.setCarOwnerUuid(TokenHelper.getUserUuid());*/

        List<OrderConsultFrontListRes> contentList = orderConsultFrontMapper.queryContentList(orderConsultDto);
        if(QueryStateEnum.STAY_EVALUATE.getValue().equals(param.getState())) {
            Iterator<OrderConsultFrontListRes> iterator = contentList.iterator();
            while (iterator.hasNext()) {
                OrderConsultFrontListRes str = iterator.next();
                if (str.getOrderSts() != 4 && str.getServiceSts() == 0) {
                    iterator.remove();
                }
            }
        }
/*        if(QueryStateEnum.STAY_EVALUATE.getValue().equals(param.getState())){
            contentList.stream().forEach(e ->{
                System.out.println(","+e.toString());
                if(e.getOrderSts()!= 4 && e.getServiceSts()==0){
                    System.out.println("-------------------------"+e.getTitle()+"------------------------");
                    contentList.remove(e);
                }
            } );
        }*/
        contentList.stream().forEach(e->{
            ConsultRes data = consultService.queryDetail(e.getConsultUuid()).getData();
            e.setVehicleBrandName(data.getVehicleBrandName());
            e.setVehicleModelName(data.getVehicleModelName());
            List<String> typeImgList = consultImagesMapper.getTypeImgList(e.getConsultUuid(), "0");
            e.setImgUrl(typeImgList);
        });
        PageInfo<OrderConsultFrontListRes> pageInfo = new PageInfo<>(contentList);




        return PageRes.success(contentList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 查询我提问的
     * @param param
     * @return
     */
    @Override
    public PageRes<List<OrderConsultFrontListRes>> queryOrderConsultListByUuid(QueryOrderConsultFrontListReq param) {

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        OrderConsultDto orderConsultDto = new OrderConsultDto();
        //我提问题的
        orderConsultDto.setCarOwnerUuid(TokenHelper.getUserUuid());
        orderConsultDto.setOrderType(1);
         if (param.getAnswerCheckSts().equals(2)) {
            //查询待服务订单（已支付订单与未服务订单）
            List<Integer> list = new ArrayList<>();
            list.add(OrderStsEnum.UNPAID.getValue());
            list.add(OrderStsEnum.HAVE_PAID.getValue());
            list.add(OrderStsEnum.A_REFUND_OF.getValue());
            list.add(OrderStsEnum.HAVE_PAID.getValue());
            orderConsultDto.setOrderStsList(list);
            orderConsultDto.setServiceSts(ServiceStsEnum.NOT_SERVICE.getValue());
        } else if (param.getAnswerCheckSts().equals(3)) {
             //查询待评价订单
             //orderConsultDto.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
             List<Integer> list = new ArrayList<>();
             list.add(OrderStsEnum.COMPLETED.getValue());
             list.add(OrderStsEnum.HAVE_PAID.getValue());
             list.add(OrderStsEnum.REFUND_SUCCESS.getValue());
             list.add(OrderStsEnum.REFUND_FAILURE.getValue());
             orderConsultDto.setOrderStsList(list);
             orderConsultDto.setEvaluateSts(EvaluateStsEnum.NO_COMMENT.getValue());
             orderConsultDto.setServiceSts(ServiceStsEnum.ALREADY_SERVICE.getValue());
             orderConsultDto.setAnswerCheckSts(CheckStatusEnum.APPROVE.getValue());

         }
        log.info("orderConsultDto传输内容"+orderConsultDto.toString());
        /*orderConsultDto.setCarOwnerUuid(TokenHelper.getUserUuid());*/
        List<OrderConsultFrontListRes> contentList = orderConsultFrontMapper.queryContentList(orderConsultDto);
        contentList.stream().forEach(e->{
            List<String> typeImgList = consultImagesMapper.getTypeImgList(e.getConsultUuid(), "0");
            e.setImgUrl(typeImgList);
        });
        PageInfo<OrderConsultFrontListRes> pageInfo = new PageInfo<>(contentList);

        return PageRes.success(contentList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }


    /**
     * 查询付费咨询订单详情
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<ConsultOrderFrontRes> queryOrderConsultDetail(String uuid) {
        log.info("详情"+uuid);
        log.debug("付费咨询订单详情 uuid {}",uuid);
        ConsultOrderFrontRes consultOrderFrontRes = orderConsultFrontMapper.queryContentDetail(uuid);
        if (!StringUtils.isEmpty(consultOrderFrontRes)) {
            //查询咨询图片
            List<String> consultImgUrlList = contentMapper.queryImgUrl(consultOrderFrontRes.getConsultUuid(), ConsultImgTypenum.CONSULT_IMG.getValue());
            //查询回答图片
            List<String> answerUrlList = contentMapper.queryImgUrl(consultOrderFrontRes.getConsultUuid(), ConsultImgTypenum.ANSWER_IMG.getValue());
            consultOrderFrontRes.setConsultImgUrlList(consultImgUrlList);
            consultOrderFrontRes.setAnswerUrlList(answerUrlList);


        }
        return ResultRes.success(consultOrderFrontRes);
    }

    /**
     *
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<Integer>  orderConsultNum(String uuid){
        Integer a = orderConsultFrontMapper.queryOrderStsNum(null,null,uuid,null);
        Integer b = orderConsultFrontMapper.queryOrderStsNum(null,null,null,uuid);
        return ResultRes.success(a+b);
    }
}
