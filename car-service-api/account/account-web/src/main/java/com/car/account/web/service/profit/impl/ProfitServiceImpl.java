package com.car.account.web.service.profit.impl;

import com.car.account.client.request.profit.AddProfitReq;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.client.response.account.AccountRes;
import com.car.account.client.response.account.QueryQuizCaseCarCountRes;
import com.car.account.client.response.profit.AccountAmtRes;
import com.car.account.client.response.profit.sub.ProfitStreamClassify;
import com.car.account.client.response.store.StoreUserRes;
import com.car.account.web.dto.profit.ClassifyProfitDto;
import com.car.account.web.mapper.profit.ProfitStreamMapper;
import com.car.account.web.mapper.store.StoreAccountMapper;
import com.car.account.web.mapper.technician.TechnicianAccountMapper;
import com.car.account.web.mapper.vehicle.VehicleMapper;
import com.car.account.web.model.profit.ProfitStream;
import com.car.account.web.model.store.StoreAccount;
import com.car.account.web.model.technician.TechnicianAccount;
import com.car.account.web.service.profit.ProfitService;
import com.car.account.web.service.store.StoreService;
import com.car.account.web.service.user.UserService;
import com.car.common.enums.*;
import com.car.common.exception.BusinessException;
import com.car.common.res.ResultRes;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.feign.*;
import com.car.order.client.request.order.consult.QueryOrderConsultFrontListReq;
import com.car.order.client.request.order.goods.QueryOrderGoodsFrontListReq;
import com.car.order.client.request.order.instance.QueryOrderCaseFrontListReq;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;
import com.car.order.client.request.technicianappointment.QueryShareTechnicianOrderReq;
import com.car.order.client.response.order.goods.OrderGoodsGroupRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhangyp
 * @date 2021/1/27 22:25
 */
@Slf4j
@Service
public class ProfitServiceImpl implements ProfitService {

    @Resource
    private StoreAccountMapper storeAccountMapper;
    @Resource
    private TechnicianAccountMapper technicianAccountMapper;
    @Resource
    private ProfitStreamMapper profitStreamMapper;
    @Autowired
    private OrderGoodsFeign orderGoodsFeign;

    @Autowired
    private OrderFrontFeign orderFrontFeign;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    OrderCaseFrontFeign orderCaseFrontFeign;

    @Autowired
    OrderConsultFrontFeign orderConsultFrontFeign;

    @Autowired
    OrderGoodsFrontFeign orderGoodsFrontFeign;

    @Autowired
    ShareTechnicianFrontFeign shareTechnicianFrontFeign;
    @Autowired
    StoreService storeService;
    @Autowired
    UserService userService;
    @Autowired
    DtcOrderFeign dtcOrderFeign;

    @Override
    public ResultRes<AccountRes> queryAccount() {

        String userUuid = TokenHelper.getUserUuid();
        Integer userType = TokenHelper.getUserType();
        AccountRes accountRes = initAccount(userUuid, userType);
        accountRes.setUserInfoRes(userService.queryUserInfo());
        return ResultRes.success(accountRes);
    }

    @Override
    public ResultRes<AccountAmtRes> queryProfitClassify() {

        String userUuid = TokenHelper.getUserUuid();
        Integer userType = TokenHelper.getUserType();
        AccountRes accountRes = initAccount(userUuid, userType);
        AccountAmtRes rst = new AccountAmtRes();
        if(null != accountRes){
            BeanUtils.copyProperties(accountRes,rst);
        }

        //??????????????????
        List<ClassifyProfitDto> inList = profitStreamMapper.staticsClassifyProfitAmt(userUuid, userType, StreamTypeEnum.IN.getType());
        //??????????????????
        List<ClassifyProfitDto> otList = profitStreamMapper.staticsClassifyProfitAmt(userUuid, userType, StreamTypeEnum.OUT.getType());

        List<ProfitStreamClassify> psList = new ArrayList<>();
        for(ClassifyEnum f : ClassifyEnum.values()){

            Integer type = f.getType();

            ProfitStreamClassify c = new ProfitStreamClassify();
            c.setClassify(type);
            c.setAmt(BigDecimal.ZERO);
            c.setWithdrawAmt(BigDecimal.ZERO);

            if(!CollectionUtils.isEmpty(inList)){
                inList.stream().forEach(s ->{

                    if(f.getType().equals(s.getClassify())){
                        c.setAmt(s.getTotalAmt());
                    }
                });
            }
            if(!CollectionUtils.isEmpty(otList)){
                otList.stream().forEach(s ->{

                    if(f.getType().equals(s.getClassify())){
                        c.setWithdrawAmt(s.getTotalAmt());
                    }
                });
            }
            psList.add(c);
        }
        rst.setProfitStreamClassifies(psList);
        return ResultRes.success(rst);
    }

    /**
     * ??????????????????????????????????????????
     */
    @Override
    public ResultRes<QueryQuizCaseCarCountRes> queryQuizCaseCarCount() {
        //??????uuid
        String userUuid = TokenHelper.getUserUuid();
        QueryQuizCaseCarCountRes queryQuizCaseCarCountRes = new QueryQuizCaseCarCountRes();
        //????????????????????????
        ResultRes<Integer> quizResultRes = orderFrontFeign.queryQuizCount(userUuid);
        if (quizResultRes.isSuccess()) {
            queryQuizCaseCarCountRes.setQuizCount(quizResultRes.getData());
        } else {
            queryQuizCaseCarCountRes.setQuizCount(0);
        }
        //????????????????????????
        ResultRes<Integer> caseResultRes = orderFrontFeign.queryCaseCount(userUuid);
        if (caseResultRes.isSuccess()) {
            queryQuizCaseCarCountRes.setCaseCount(caseResultRes.getData());
        } else {
            queryQuizCaseCarCountRes.setCaseCount(0);
        }
        //????????????????????????
        Integer vehicleCount = vehicleMapper.queryVehicleCount(userUuid);
        queryQuizCaseCarCountRes.setCarCount(vehicleCount);
        return ResultRes.success(queryQuizCaseCarCountRes);
    }

    @Override
    public ResultRes<String> addProfit(AddProfitReq addProfitReq) {
        ProfitStream profitStream = new ProfitStream();
        BeanUtils.copyProperties(addProfitReq,profitStream);
        profitStream.setUuid(UuidUtils.getUuid());
        profitStream.setCreatedTime(new Date());
        profitStream.setSts(StsEnum.ACTIVE.getValue());
        profitStream.setCashSts(0);
        profitStreamMapper.insert(profitStream);
        return ResultRes.success(profitStream.getUuid());
    }

    @Override
    public ResultRes<Map> statisticsIncomeAmount(SceneOrderStatisticsReq sceneOrderStatisticsReq) {
        Map outMap = new HashMap();
        String userUuid = TokenHelper.getUserUuid();
        Integer userType = TokenHelper.getUserType();

        List<Map> list = new ArrayList<>();
        BigDecimal a = BigDecimal.ZERO;
        Map map = new HashMap();
        if(UserTypeEnum.technician.getType().equals(userType)) {
            a = profitStreamMapper.statisticsAmount(StreamTypeEnum.IN.getType(), "10", userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
            map.put("name", "????????????");
            map.put("data", a);
            log.info("userType ---- 1");
        }else if(UserTypeEnum.store.getType().equals(userType)) {
            a = profitStreamMapper.statisticsAmount(StreamTypeEnum.IN.getType(),"5",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
            map.put("name","????????????");
            map.put("data",a);
            log.info("userType ---- 2");
        }
        list.add(map);
        BigDecimal b  =profitStreamMapper.statisticsAmount(StreamTypeEnum.IN.getType(),"3",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        Map map1 = new HashMap();
        map1.put("name","????????????");
        map1.put("data",b);
        BigDecimal c  = profitStreamMapper.statisticsAmount(StreamTypeEnum.IN.getType(),"2",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        Map map2 = new HashMap();
        map2.put("name","????????????");
        map2.put("data",c);
        BigDecimal d = profitStreamMapper.statisticsAmount(StreamTypeEnum.IN.getType(),"9,11",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        Map map3 = new HashMap();
        map3.put("name","????????????");
        map3.put("data",d);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        outMap.put("list",list);
        outMap.put("count",a.add(b).add(c).add(d) );
        return ResultRes.success(outMap);
    }

    @Override
    public ResultRes<Map> statisticsPayAmount(SceneOrderStatisticsReq sceneOrderStatisticsReq) {
        Map outMap = new HashMap();
        String userUuid = TokenHelper.getUserUuid();
        List<Map> list = new ArrayList<>();
        BigDecimal q = profitStreamMapper.statisticsAmount(StreamTypeEnum.OUT.getType(),"1,4",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        BigDecimal cases = profitStreamMapper.statisticsAmount(StreamTypeEnum.OUT.getType(),"3",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        BigDecimal dtc = profitStreamMapper.statisticsAmount(StreamTypeEnum.OUT.getType(),"7",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        BigDecimal scene = profitStreamMapper.statisticsAmount(StreamTypeEnum.OUT.getType(),"9,11",userUuid,sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
        Map map = new HashMap();
        map.put("name","??????");
        map.put("data",q);
        Map map1 = new HashMap();
        map1.put("name","????????????");
        map1.put("data",cases);
        Map map2 = new HashMap();
        map2.put("name","DTC??????");
        map2.put("data",dtc);
        Map map3 = new HashMap();
        map3.put("name","????????????");
        map3.put("data",scene);
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        outMap.put("list",list);
        outMap.put("count",q.add(cases).add(dtc).add(scene) );
        return ResultRes.success(outMap);
    }

    @Override
    public ResultRes<List<Map>> statisticsAmountByTypeList (SceneOrderStatisticsReq  sceneOrderStatisticsReq){
        String userUuid = TokenHelper.getUserUuid();
        List<Map> maps = profitStreamMapper.statisticsAmountByTypeList(sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth(),userUuid);
        return ResultRes.success(maps);
    }

    @Override
    public ResultRes<BigDecimal> statisticsAmount (ProfitStreamReq profitStreamReq){
        BigDecimal totalAmount =  profitStreamMapper.statisticsAmount(profitStreamReq.getStreamType(),profitStreamReq.getClassify(),profitStreamReq.getUserUuid(),null,null);
        return ResultRes.success(totalAmount);
    }


    /**
     * ???????????????????????????
     * @param userUuid
     * @param userType
     * @return
     */
    private AccountRes initAccount(String userUuid,Integer userType){
        /**
         * ???????????? :   ?????????????????????????????????
         * ??????????????? : ?????????????????????
         * ??????????????? : ???????????? - ???????????? - ???????????????
         */
        AccountRes rst = new AccountRes();
        if (UserTypeEnum.technician.getType().equals(userType)){

            log.info("??????????????????????????????");
            TechnicianAccount t = new TechnicianAccount();
            t.setSts(StsEnum.ACTIVE.getValue());
            t.setTechnicianUuid(userUuid);
            TechnicianAccount account = technicianAccountMapper.selectOne(t);
//            if(null == account){
//
//                log.error("??????????????????????????????");
//                throw new BusinessException(ResEnum.TECHNICIAN_NO_ACCOUNT_AMT);
//            }
            if (!StringUtils.isEmpty(account)) {
                //????????????
                BigDecimal accountAmount = initVal(account.getAccountAmount());
                //???????????????
                BigDecimal waitAmount = initVal(account.getWaitAmount());
                //???????????????
                BigDecimal withdrawAmount = initVal(account.getWithdrawAmount());
                //????????????
                BigDecimal frozenAmt = initVal(account.getFrozenAmt());
                rst.setAccountAmt(accountAmount);
                rst.setWaitAmount(waitAmount);
                rst.setWithdrawAmount(withdrawAmount);
                rst.setFrozenAmt(frozenAmt);
                rst.setCardNumbers(account.getCardNumbers());
                rst.setAccountName(account.getAccountName());
                rst.setSubBranchName(account.getSubBranchName());
                rst.setDepositBank(account.getDepositBank());
            }

        }else if(UserTypeEnum.store.getType().equals(userType)){


            log.info("??????????????????????????????");
            ResultRes<StoreUserRes> resResultRes =  storeService.queryStoreUserInfo(userUuid);

            StoreAccount sa = new StoreAccount();
            sa.setSts(StsEnum.ACTIVE.getValue());
            sa.setStoreUuid(resResultRes.getData().getStoreUuid());
            StoreAccount storeAccount = storeAccountMapper.selectOne(sa);
            if (!StringUtils.isEmpty(storeAccount)) {
                //????????????
                BigDecimal accountAmount = initVal(storeAccount.getAccountAmount());
                //???????????????
                BigDecimal waitAmount = initVal(storeAccount.getWaitAmount());
                //???????????????
                BigDecimal withdrawAmount = initVal(storeAccount.getWithdrawAmount());
                //????????????
                BigDecimal frozenAmt = initVal(storeAccount.getFrozenAmt());
                rst.setAccountAmt(accountAmount);
                rst.setWaitAmount(waitAmount);
                rst.setWithdrawAmount(withdrawAmount);
                rst.setFrozenAmt(frozenAmt);
                rst.setCardNumbers(storeAccount.getCardNumbers());
                rst.setAccountName(storeAccount.getAccountName());
                rst.setSubBranchName(storeAccount.getSubBranchName());
                rst.setDepositBank(storeAccount.getDepositBank());
            }

        }else if(UserTypeEnum.vehicle.getType().equals(userType)){

            log.error("?????????????????????????????????????????????");
            throw new BusinessException(ResEnum.VEHICLE_NOT_ACCOUNT_AMT);
        }else {

            log.error("??????????????????>>>userType:{}",userType);
            throw new BusinessException(ResEnum.VEHICLE_NOT_ACCOUNT_AMT);
        }

        int orderNum = queryOrderNum(userUuid, userType);
        rst.setOrderNum(orderNum);
        rst.setScore(4.1);
        return rst;
    }


    private BigDecimal initVal(BigDecimal s){

        return (null == s) ? BigDecimal.ZERO : s;
    }
    /**
     * ?????????????????????
     * @param userUuid
     * @param userTypeEnum
     * @return
     */
    private BigDecimal queryIngAmt(String userUuid,UserTypeEnum userTypeEnum){
        //TODO ?????????
        return BigDecimal.ZERO;
    }

    /**
     * ?????????????????? TODO ???????????????
     * @param userUuid
     * @param userTypeEnum
     * @return
     */
    private int queryOrderNum(String userUuid, Integer userTypeEnum) {
        Integer goodNum = 0;
        Integer shar = 0;
        Integer dtc = 0;
        if (userTypeEnum.equals(UserTypeEnum.store.getType())) {
            OrderGoodsGroupRes res = orderGoodsFeign.queryGoodsGroupCountUserApi(userUuid).getData();
            goodNum =  res.getAllTotal().intValue();
        }
        if (userTypeEnum.equals(UserTypeEnum.technician.getType())) {
            shar = shareTechnicianFrontFeign.statisticsShareOrderApi(userUuid).getData();
        }

        ResultRes<HashMap> resultRes = dtcOrderFeign.getNumberByUuid(userUuid);
        if(resultRes.isSuccess()){
            HashMap hashMap = resultRes.getData();
            Integer d = Integer.parseInt(hashMap.get("kecha").toString());
            Integer t =  Integer.parseInt(hashMap.get("guoqi").toString());
            dtc = d+t;
        }
        Integer  caseNum= orderCaseFrontFeign.orderCaseNum(userUuid).getData();
        caseNum = caseNum==null?0:caseNum;
        Integer consult = orderConsultFrontFeign.orderConsultNum(userUuid).getData();
        consult =consult==null?0:consult;
        Integer count = shar+caseNum+consult+goodNum+dtc;



        return count;
    }
}
