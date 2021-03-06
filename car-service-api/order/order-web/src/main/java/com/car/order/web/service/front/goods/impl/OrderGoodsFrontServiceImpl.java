package com.car.order.web.service.front.goods.impl;

import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.response.store.StoreUserRes;
import com.car.common.enums.ResEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.IdWorker;
import com.car.common.utils.TokenHelper;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.front.GoodsOrderTypeEnum;
import com.car.order.client.enums.front.QueryStateEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.ServiceStsEnum;
import com.car.order.client.enums.score.ScoreTypeEnum;
import com.car.order.client.enums.sharetechnicianorder.ShareTechnicianOrderEnum;
import com.car.order.client.request.order.front.QueryOrderStsNumReq;
import com.car.order.client.request.order.goods.QueryOrderGoodsFrontListReq;
import com.car.order.client.response.order.front.QueryOrderStsNumRes;
import com.car.order.client.response.order.goods.OrderGoodsDetailFrontRes;
import com.car.order.client.response.order.goods.OrderGoodsFrontListRes;
import com.car.order.client.response.order.goods.OrderGoodsFrontRes;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.dto.OrderGoodsDto;
import com.car.order.web.mapper.consult.OrderConsultFrontMapper;
import com.car.order.web.mapper.goods.GoodsDetailMapper;
import com.car.order.web.mapper.goods.OrderGoodsFrontMapper;
import com.car.order.web.mapper.instance.OrderCaseFrontMapper;
import com.car.order.web.mapper.score.ScoreInfoMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianOrderMapper;
import com.car.order.web.model.goods.GoodsDetail;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianOrder;
import com.car.order.web.service.front.goods.OrderGoodsFrontService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@Slf4j
@Service
public class OrderGoodsFrontServiceImpl implements OrderGoodsFrontService {

    @Autowired
    OrderGoodsFrontMapper orderGoodsFrontMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private OrderConsultFrontMapper orderConsultFrontMapper;

    @Autowired
    private OrderCaseFrontMapper orderCaseFrontMapper;

    @Autowired
    private ScoreInfoMapper scoreInfoMapper;

    @Autowired
    StoreUserFeign storeUserFeign;

    @Autowired
    ShareTechnicianOrderMapper shareTechnicianOrderMapper;



    private IdWorker idWorker = new IdWorker(1, 1);
    /**
     * ????????????????????????
     * @param param
     * @return
     */
    @Override
    public PageRes<List<OrderGoodsFrontListRes>> queryOrderGoodsList(QueryOrderGoodsFrontListReq param) {
        log.debug("????????????????????????");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        OrderGoodsDto orderGoodsDto = new OrderGoodsDto();
        if (QueryStateEnum.UNPAID.getValue().equals(param.getState())) {
            //?????????????????????
            orderGoodsDto.setOrderSts(OrderStsEnum.UNPAID.getValue());
        } else if (QueryStateEnum.STAY_SERVICE.getValue().equals(param.getState())) {
            //????????????????????????????????????????????????????????????
            orderGoodsDto.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
            orderGoodsDto.setServiceSts(ServiceStsEnum.NOT_SERVICE.getValue());
        } else if (QueryStateEnum.STAY_EVALUATE.getValue().equals(param.getState())) {
            //?????????????????????
            orderGoodsDto.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
            orderGoodsDto.setServiceSts(ServiceStsEnum.ALREADY_SERVICE.getValue());
        } else if (QueryStateEnum.REFUND.getValue().equals(param.getState())) {
            //??????????????????
            List<Integer> list = new ArrayList<>();
            list.add(OrderStsEnum.A_REFUND_OF.getValue());
            list.add(OrderStsEnum.REFUND_SUCCESS.getValue());
            list.add(OrderStsEnum.REFUND_FAILURE.getValue());
            orderGoodsDto.setOrderStsList(list);
        }
        if(UserTypeEnum.store.getType().equals(TokenHelper.getUserType())){
            ResultRes<StoreUserRes> storeUserRes = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid());
            if(!storeUserRes.isSuccess()){
                throw new BusinessException(ResEnum.STORE_CONTACT_NOT_EXIST);
            }
            StoreUserRes storeUser = storeUserRes.getData();
            orderGoodsDto.setStoreUuid(storeUser.getStoreUuid());
        }else if(UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())){
            orderGoodsDto.setUserUuid(TokenHelper.getUserUuid());
        }
        List<OrderGoodsFrontListRes> orderGoodsList = orderGoodsFrontMapper.queryOrderGoodsList(orderGoodsDto);
        if (!CollectionUtils.isEmpty(orderGoodsList)) {
//            //??????????????????
//            orderGoodsList.forEach(orderGoods -> {
//                Integer orderType = this.goodsOrderType(orderGoods.getAmtExpress(), orderGoods.getAmtService());
//                //????????????????????????
//                orderGoods.setOrderType(orderType);
//            });
        }
        PageInfo<OrderGoodsFrontListRes> pageInfo = new PageInfo<>(orderGoodsList);
        return PageRes.success(orderGoodsList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * ??????????????????????????????
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<OrderGoodsFrontRes> queryOrderGoodsDetail(String uuid) {
        log.debug("??????????????????????????????");
        OrderGoodsFrontRes orderGoodsFrontRes = orderGoodsFrontMapper.queryOrderGoods(uuid);
        if (null != orderGoodsFrontRes) {
            //??????????????????
//            Integer orderType = this.goodsOrderType(orderGoodsFrontRes.getAmtExpress(), orderGoodsFrontRes.getAmtService());
            //??????????????????
            BigDecimal goodsScore = scoreInfoMapper.queryScore(orderGoodsFrontRes.getUuid(), ScoreTypeEnum.GOODS.getValue());
            //??????????????????
            BigDecimal serviceScore = scoreInfoMapper.queryScore(orderGoodsFrontRes.getUuid(), ScoreTypeEnum.SERVICE.getValue());
//            orderGoodsFrontRes.setOrderType(orderType);
            orderGoodsFrontRes.setGoodsScore(goodsScore);
            orderGoodsFrontRes.setServiceScore(serviceScore);
            //????????????????????????
            List<GoodsDetail> goodsDetailList = goodsDetailMapper.queryListByGoodsId(orderGoodsFrontRes.getGoodsUuid());
            if (!CollectionUtils.isEmpty(goodsDetailList)) {
                List<OrderGoodsDetailFrontRes> orderGoodsDetailFrontResList = new ArrayList<>();
                goodsDetailList.forEach(goodsDetail -> {
                    OrderGoodsDetailFrontRes orderGoodsDetailFrontRes = new OrderGoodsDetailFrontRes();
                    orderGoodsDetailFrontRes.setUuid(goodsDetail.getUuid());
                    orderGoodsDetailFrontRes.setGoodsUuid(goodsDetail.getGoodsUuid());
                    orderGoodsDetailFrontRes.setGoodsDetailName(goodsDetail.getName());
                    orderGoodsDetailFrontRes.setGoodsDetailNum(goodsDetail.getNum());
                    orderGoodsDetailFrontRes.setGoodsDetailAmt(goodsDetail.getAmt());
                    orderGoodsDetailFrontRes.setGoodsDetailActAmt(goodsDetail.getActAmt());
                    orderGoodsDetailFrontResList.add(orderGoodsDetailFrontRes);
                });
                orderGoodsFrontRes.setOrderGoodsDetailFrontRes(orderGoodsDetailFrontResList);
            }
        }

        return ResultRes.success(orderGoodsFrontRes);
    }

    /**
     * ???????????????????????????
     * @param req
     * @return
     */
    @Override
    public ResultRes<QueryOrderStsNumRes> queryOrderStsNum(QueryOrderStsNumReq req) {
        //???????????????
        Integer completedNum = 0;
        //???????????????
        Integer unpaidNum = 0;
        //????????????????????????
        Integer paidNotCompleteNum = 0;
        //???????????????
        Integer refunded = 0;
        //??????uuid
        String userUuid = TokenHelper.getUserUuid();
        if (OrderTypeEnum.GOOD.getValue().equals(req.getOrderType())) {
            //??????????????????
            String storeUuid = null;
            if(UserTypeEnum.store.getType().equals(TokenHelper.getUserType())){
                ResultRes<StoreUserRes> storeUserRes = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid());
                if(!storeUserRes.isSuccess()){
                    throw new BusinessException(ResEnum.STORE_CONTACT_NOT_EXIST);
                }
                StoreUserRes storeUser = storeUserRes.getData();
                storeUuid = storeUser.getStoreUuid();
                //?????????????????????????????????????????????????????????ID????????????
                userUuid = null;
            }
            //?????????
            completedNum = orderGoodsFrontMapper.queryOrderStsNum(OrderStsEnum.COMPLETED.getValue(), null, userUuid,storeUuid);
            //?????????
            unpaidNum = orderGoodsFrontMapper.queryOrderStsNum(OrderStsEnum.UNPAID.getValue(), null, userUuid,storeUuid);
            //??????????????????
            paidNotCompleteNum = orderGoodsFrontMapper.queryOrderStsNum(OrderStsEnum.HAVE_PAID.getValue(), ServiceStsEnum.NOT_SERVICE.getValue(), userUuid,storeUuid);
            //?????????
            refunded = orderGoodsFrontMapper.queryOrderStsNum(OrderStsEnum.REFUND_SUCCESS.getValue(), null, userUuid,storeUuid);
        } else if (OrderTypeEnum.CONSULT.getValue().equals(req.getOrderType())) {
            //??????????????????
            String technicianUuid = null;
            if(UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())){
                technicianUuid = TokenHelper.getUserUuid();
                //????????????ID??????????????????????????????????????????ID??????
                userUuid = null;
            }
            //?????????
            completedNum = orderConsultFrontMapper.queryOrderStsNum(OrderStsEnum.HAVE_PAID.getValue(), ServiceStsEnum.ALREADY_SERVICE.getValue(), userUuid,technicianUuid);
            //?????????
            unpaidNum = orderConsultFrontMapper.queryOrderStsNum(OrderStsEnum.UNPAID.getValue(), null, userUuid,technicianUuid);
            //??????????????????
            paidNotCompleteNum = orderConsultFrontMapper.queryOrderStsNum(OrderStsEnum.HAVE_PAID.getValue(), ServiceStsEnum.NOT_SERVICE.getValue(), userUuid,technicianUuid);
            //?????????
            refunded = orderConsultFrontMapper.queryOrderStsNum(OrderStsEnum.REFUND_SUCCESS.getValue(), null, userUuid,technicianUuid);
        } else if (OrderTypeEnum.EXAMPLE.getValue().equals(req.getOrderType())) {
            //??????uuid
            String technicianUuid = null;
            //??????uuid
            String carOwnerUuid = null;
            //?????????????????????????????????????????????????????????
            if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())) {
                //??????
                technicianUuid = userUuid;
            } else {
                //??????
                carOwnerUuid = userUuid;
            }
            //????????????
            //?????????
            completedNum = orderCaseFrontMapper.queryOrderStsNum(OrderStsEnum.HAVE_PAID.getValue(), technicianUuid, carOwnerUuid);
            //?????????
            unpaidNum = orderCaseFrontMapper.queryOrderStsNum(OrderStsEnum.UNPAID.getValue(), technicianUuid, carOwnerUuid);
            //??????????????????(?????????????????????????????????????????????????????????)
            //?????????
            refunded = orderCaseFrontMapper.queryOrderStsNum(OrderStsEnum.REFUND_SUCCESS.getValue(), technicianUuid, carOwnerUuid);
        }else if (OrderTypeEnum.SHARED_TECHNICIAN.getValue().equals(req.getOrderType())) {
            //??????uuid
            String technicianUuid = TokenHelper.getUserUuid();

            //?????????
            List<Integer> orderStatus=new ArrayList<>();
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            completedNum = shareTechnicianOrderMapper.queryOrderStsNum(orderStatus, null,technicianUuid);
            //?????????
            orderStatus.clear();
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            unpaidNum = shareTechnicianOrderMapper.queryOrderStsNum(orderStatus, null,technicianUuid);
            //??????????????????
            orderStatus.clear();
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            paidNotCompleteNum = shareTechnicianOrderMapper.queryOrderStsNum(orderStatus, null,technicianUuid);
            //?????????
            orderStatus.clear();
            orderStatus.add(ShareTechnicianOrderEnum.SUBMIT_PLAN.getValue());
            refunded = shareTechnicianOrderMapper.queryOrderStsNum(orderStatus, null,technicianUuid);
        }
        QueryOrderStsNumRes queryOrderStsNumRes = new QueryOrderStsNumRes();
        queryOrderStsNumRes.setCompletedNum(completedNum);
        queryOrderStsNumRes.setUnpaidNum(unpaidNum);
        queryOrderStsNumRes.setPaidNotCompleteNum(paidNotCompleteNum);
        queryOrderStsNumRes.setRefunded(refunded);
        return ResultRes.success(queryOrderStsNumRes);
    }


    /**
     * ??????????????????????????????
     * @param amtExpress ?????????
     * @param amtService ?????????
     * @return
     */
    private Integer goodsOrderType(BigDecimal amtExpress, BigDecimal amtService) {
        //??????????????? 0 ???????????????????????????????????? 1 ?????????????????????????????? 2 ??????????????????????????????
        Integer orderType;
        BigDecimal bigDecimal = new BigDecimal(Constants.TWO_BIG_DECIMAL);
        if (null != amtExpress && !(bigDecimal.equals(amtExpress))) {
            //???????????????????????????????????????0.00??????????????????????????????????????????
            orderType = GoodsOrderTypeEnum.NOT_SERVICE.getValue();
        } else if (null != amtService && !(bigDecimal.equals(amtService))) {
            //???????????????????????????????????????0.00????????????????????????????????????
            orderType = GoodsOrderTypeEnum.SERVICE.getValue();
        } else {
            //???????????????????????????
            orderType = GoodsOrderTypeEnum.NOT_GOODS.getValue();
        }
        return orderType;
    }
}
