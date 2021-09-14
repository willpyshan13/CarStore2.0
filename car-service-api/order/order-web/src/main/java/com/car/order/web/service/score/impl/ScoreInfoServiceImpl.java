package com.car.order.web.service.score.impl;

import com.alibaba.fastjson.JSON;
import com.car.account.client.feign.StoreFegin;
import com.car.account.client.feign.TechnicianFegin;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.EvaluateStsEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.request.order.score.AddScoreInfoReq;
import com.car.order.client.request.order.score.QueryScoreListReq;
import com.car.order.client.response.order.score.QueryScoreInfoListRes;
import com.car.order.client.response.order.score.QueryScoreInfoRes;
import com.car.order.web.mapper.consult.ConsultMapper;
import com.car.order.web.mapper.consult.ConsultOrderMapper;
import com.car.order.web.mapper.course.CourseMapper;
import com.car.order.web.mapper.course.CourseOrderMapper;
import com.car.order.web.mapper.dtc.DtcOrderDetailMapper;
import com.car.order.web.mapper.dtc.DtcOrderMapper;
import com.car.order.web.mapper.goods.OrderGoodsMapper;
import com.car.order.web.mapper.instance.OrderCaseMapper;
import com.car.order.web.mapper.order.OrderInfoMapper;
import com.car.order.web.mapper.scene.SceneOrderMapper;
import com.car.order.web.mapper.scene.SceneOrderServicesMapper;
import com.car.order.web.mapper.score.ScoreConfigMapper;
import com.car.order.web.mapper.score.ScoreInfoMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianOrderMapper;
import com.car.order.web.mapper.technician.TechnicianCaseMapper;
import com.car.order.web.model.consult.ConsultOrder;
import com.car.order.web.model.goods.OrderGoods;
import com.car.order.web.model.order.OrderInfo;
import com.car.order.web.model.score.ScoreConfig;
import com.car.order.web.model.score.ScoreInfo;
import com.car.order.web.service.score.ScoreInfoService;
import com.car.utility.client.feign.PayFeign;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/1/30
 */
@Slf4j
@Service
public class ScoreInfoServiceImpl implements ScoreInfoService {

    @Autowired
    private ScoreInfoMapper scoreInfoMapper;
    @Autowired
    private ScoreConfigMapper scoreConfigMapper;
    @Autowired
    private TechnicianFegin technicianFegin;
    @Autowired
    private StoreFegin storeFegin;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 新增评分信息
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> addScore(AddScoreInfoReq req) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderUuid(req.getOrderUuid());
        orderInfo.setSts(StsEnum.ACTIVE.getValue());
        OrderInfo orderInfoSelect = orderInfoMapper.selectOne(orderInfo);
        if(orderInfoSelect == null){
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        if(!orderInfoSelect.getPaySts().equals(OrderStsEnum.COMPLETED.getValue())||orderInfoSelect.getEvaluateSts().equals(EvaluateStsEnum.COMMENTED.getValue())){
            throw new BusinessException("99999","该订单不能评价!");
        }
        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setUuid(UuidUtils.getUuid());
        scoreInfo.setOrderUuid(req.getOrderUuid());
        scoreInfo.setOrderType(req.getOrderType());
        scoreInfo.setScoreType(1);
        scoreInfo.setScoreStar(req.getScoreStar());
        scoreInfo.setUserUuid(TokenHelper.getUserUuid());
        scoreInfo.setUserType(TokenHelper.getUserType());
        scoreInfo.setSts(StsEnum.ACTIVE.getValue());
        scoreInfo.setCreatedBy(TokenHelper.getUserName());
        scoreInfo.setCreatedTime(new Date());
        scoreInfo.setRelationType(req.getRelationType());
        scoreInfo.setRelationUuid(req.getRelationUuid());
        int addScoreInfoNum = scoreInfoMapper.insert(scoreInfo);
        if (addScoreInfoNum <= 0 ) {
            log.error("新增评分失败，新增参数为：{}", JSON.toJSONString(scoreInfo));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }

        updateScore(req);
        orderInfo.setEvaluateSts(EvaluateStsEnum.COMMENTED.getValue());
        orderInfo.setUuid(orderInfoSelect.getUuid());
        orderInfo.setLastUpdatedTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        return ResultRes.success(scoreInfo.getUuid());
    }


    /**
     * 修改评分
     * @param req
     */
    private void updateScore(AddScoreInfoReq req) {
        List<BigDecimal> bigDecimalList = scoreInfoMapper.queryScoreByUserId(req.getRelationUuid(),req.getRelationType());
        //倒数第二次评价
        BigDecimal var1 =new BigDecimal(5);
        //后一次评价
        BigDecimal var2 =new BigDecimal(5);
        if(bigDecimalList != null && bigDecimalList.size() == 1 ){
            var2 = bigDecimalList.get(0);
        }else if(bigDecimalList != null && bigDecimalList.size() == 2 ){
            var1 = bigDecimalList.get(1);
            var2 = bigDecimalList.get(0);
        }
        ScoreConfig scoreConfig = new ScoreConfig();
        scoreConfig.setVar1(var1);
        scoreConfig.setVar2(var2);
        scoreConfig = scoreConfigMapper.selectOne(scoreConfig);
        if (UserTypeEnum.technician.getType().equals(req.getRelationType())) {
            technicianFegin.updateScore(req.getRelationUuid(),scoreConfig.getScore());
        }else if(UserTypeEnum.store.getType().equals(req.getRelationType())){
            storeFegin.updateScore(req.getRelationUuid(),scoreConfig.getScore());
        }
    }


    /**
     * 查询评分信息列表
     * @param req
     * @return
     */
    @Override
    public PageRes<List<QueryScoreInfoListRes>> queryScoreList(QueryScoreListReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        //用户uuid
        String userUuid = TokenHelper.getUserUuid();
        List<QueryScoreInfoListRes> resList = scoreInfoMapper.queryScoreList(req, userUuid);
        PageInfo<QueryScoreInfoListRes> pageInfo = new PageInfo<>(resList);
        return PageRes.success(resList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 查询评分信息详情
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<QueryScoreInfoRes> queryScoreInfo(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        //查询评分详情
        ScoreInfo scoreInfo = scoreInfoMapper.selectByPrimaryKey(uuid);
        QueryScoreInfoRes res = new QueryScoreInfoRes();
        if (null != scoreInfo) {
            BeanUtils.copyProperties(scoreInfo, res);
        }
        return ResultRes.success(res);
    }

    /**
     * 删除评分信息
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<String> deleteScore(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setUuid(uuid);
        scoreInfo.setSts(StsEnum.INVALID.getValue());
        scoreInfo.setLastUpdatedBy(TokenHelper.getUserName());
        scoreInfo.setLastUpdatedTime(new Date());
        int deleteNum = scoreInfoMapper.updateByPrimaryKeySelective(scoreInfo);
        if (deleteNum <= 0) {
            log.error("删除评分信息失败，请求参数为：{}", JSON.toJSONString(scoreInfo));
            throw new BusinessException(ResEnum.DELETE_DB_ERROR);
        }
        return ResultRes.success(uuid);
    }

    /**
     * 查询商品/店铺/服务评分
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<String> queryGoodsScore(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new BusinessException(ResEnum.DELETE_DB_ERROR);
        }
        String goodsScore = scoreInfoMapper.queryGoodsScore(uuid);
        return ResultRes.success(goodsScore);
    }
}
