package com.car.order.web.service.course.impl;

import com.alibaba.fastjson.JSON;
import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.response.store.StoreUserRes;
import com.car.common.enums.*;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.*;
import com.car.common.utils.token.LoginToken;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.course.CourseTypeEnum;
import com.car.order.client.enums.course.PurchaseTypeEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.PayMethodEnum;
import com.car.order.client.request.course.AddCourseOrderReq;
import com.car.order.client.request.course.QueryCourseOrderListReq;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.response.course.QueryCourseInfoRes;
import com.car.order.client.response.course.QueryCourseOrderInfoRes;
import com.car.order.client.response.course.QueryCourseOrderListRes;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.mapper.course.CourseMapper;
import com.car.order.web.mapper.course.CourseOrderDetailMapper;
import com.car.order.web.mapper.course.CourseOrderMapper;
import com.car.order.web.model.course.CourseOrder;
import com.car.order.web.model.course.CourseOrderDetail;
import com.car.order.web.service.course.CourseOrderService;
import com.car.order.web.service.order.OrderInfoService;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author: ??cjw
 * Date: ??2021/2/21
 */
@Slf4j
@Service
public class CourseOrderServiceImpl implements CourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StoreUserFeign storeUserFeign;

    @Autowired
    private CourseOrderDetailMapper courseOrderDetailMapper;

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * ??????id????????????????????????
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<QueryCourseOrderInfoRes> getById(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            log.error("????????????????????????uuid????????????");
            throw new BusinessException(ResEnum.LACK_PARAMETER);
        }
        QueryCourseOrderInfoRes res = courseOrderMapper.getById(uuid);
        return ResultRes.success(res);
    }

    /**
     * ????????????????????????
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> add(AddCourseOrderReq req) {
        //????????????????????????
        String userName = TokenHelper.getUserName();
        //???????????????uuid
        String userUuid = TokenHelper.getUserUuid();
        //????????????????????????
        QueryCourseInfoRes queryCourseInfoRes = courseMapper.getById(req.getCourseUuid());
        if (null == queryCourseInfoRes) {
            log.error("???????????????????????????????????????????????????uuid??????{}", req.getCourseUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        //???????????????????????????
        int orderCount = courseOrderMapper.queryIsPurchase(queryCourseInfoRes.getUuid(), userUuid);
        if (orderCount > 0) {
            //?????????????????????
            log.error("??????????????????");
            throw new BusinessException(ResEnum.COURSE_ORDER_PURCHASED);
        }
        //????????????????????????
        String courseOrderUuid = insertOrderCourse(userName, req, queryCourseInfoRes);
        //??????????????????????????????
        insertCourseOrderContent(courseOrderUuid, userName, queryCourseInfoRes);
        return ResultRes.success(courseOrderUuid);
    }

    /**
     * ????????????
     * @param req
     * @return
     */
    @Override
    public PageRes<List<QueryCourseOrderListRes>> list(QueryCourseOrderListReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        //????????????
        Integer userType = TokenHelper.getUserType();
        //??????uuid
        String userUuid = null;
        if (null != userType) {
            //?????????????????????????????????
            userUuid = queryStoreUserInfo(userType, TokenHelper.getUserUuid());
        }
        List<QueryCourseOrderListRes> resList = courseOrderMapper.list(req, userUuid);
        PageInfo<QueryCourseOrderListRes> pageInfo = new PageInfo<>(resList);
        return PageRes.success(resList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * ??????????????????
     * @param exportReq
     * @param response
     */
    @Override
    public void exportCourseOrderList(QueryCourseOrderListReq exportReq, HttpServletResponse response) {
        try{
            List<QueryCourseOrderListRes> resList = courseOrderMapper.list(exportReq, null);
            //??????????????????
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(Constants.ORDER_COURSE_INFO_EXPORT_TEMPLATE);
            //????????????????????????
            List<QueryCourseOrderListRes> excelList = ExcelUtils.setFieldValue(resList);
            Workbook wb = new XSSFWorkbook(resourceAsStream);
            Sheet sheet = wb.getSheetAt(0);
            //????????????????????????
            int firstRowIndex = sheet.getFirstRowNum()+2;
            for (int rowIndex = firstRowIndex; rowIndex < excelList.size()+2; rowIndex++) {
                //?????????
                Row rowStyle = (rowIndex % 2) == 0?sheet.getRow(2): sheet.getRow(3);
                //????????????
                CellStyle cellStyle = ExcelUtils.getExcelFormat(rowStyle.getCell(1));
                CellStyle cellStyle1 = ExcelUtils.getExcelFormat(rowStyle.getCell(0));
                Row row = sheet.getRow(rowIndex);
                if(row == null){
                    row = sheet.createRow(rowIndex);
                }
                row.setHeight(rowStyle.getHeight());
                QueryCourseOrderListRes exportDto = excelList.get(rowIndex - 2);
                ExcelUtils.setCell(row,cellStyle1,0,rowIndex-1);
                ExcelUtils.setCell(row,cellStyle,1,exportDto.getBuyerName());
                ExcelUtils.setCell(row,cellStyle,2,exportDto.getBuyerMobile());
                ExcelUtils.setCell(row,cellStyle,3,exportDto.getCourseTitle());
                ExcelUtils.setCell(row,cellStyle,4,DateUtil.dateToStr(exportDto.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                ExcelUtils.setCell(row,cellStyle,5,exportDto.getCourseLecturer());
                BigDecimal orderAmount = (null != exportDto.getOrderAmount()) ? exportDto.getOrderAmount() : BigDecimal.ZERO;
                ExcelUtils.setCell(row,cellStyle,6,"?? " + orderAmount);
                ExcelUtils.setCell(row,cellStyle,7, CourseTypeEnum.enumOfDesc(exportDto.getCourseType()));
                ExcelUtils.setCell(row,cellStyle,8, exportDto.getPayType() == null ? "" : PayMethodEnum.enumOfDesc(exportDto.getPayType()));
            }
            ExcelUtils.responseWrite(wb,response, Constants.ORDER_COURSE_INFO_EXPORT_TEMPLATE);
        }catch (Exception ex){
            log.error("????????????????????????????????????????????????{}", ExceptionUtils.stackTraceToString(ex));
        }
    }

    /**
     * ????????????????????????
     * @param userName
     * @param req
     * @return
     */
    private String insertOrderCourse (String userName, AddCourseOrderReq req, QueryCourseInfoRes queryCourseInfoRes) {
        //??????uuid
        String userUuid = TokenHelper.getUserUuid();
        //????????????
        Integer userType = TokenHelper.getUserType();
        //???????????????
        String mobile = null;
        LoginToken loginToken = TokenHelper.getLoginToken();
        if (null != loginToken) {
            mobile = loginToken.getUserMobile();
        }
        //?????????????????????????????????
        userUuid = queryStoreUserInfo(userType, userUuid);
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setUuid(UuidUtils.getUuid());
        courseOrder.setCourseUuid(req.getCourseUuid());
        courseOrder.setCourseType(queryCourseInfoRes.getCourseType());
        courseOrder.setCourseLecturer(queryCourseInfoRes.getCourseLecturer());
        courseOrder.setCourseTitle(queryCourseInfoRes.getCourseTitle());
        courseOrder.setCourseCover(queryCourseInfoRes.getCourseCover());
        courseOrder.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.KC));
        courseOrder.setOrderAmount(queryCourseInfoRes.getCourseAmount());
        courseOrder.setOrderSts(OrderStsEnum.UNPAID.getValue());
        courseOrder.setBuyerUuid(userUuid);
        courseOrder.setBuyerName(userName);
        courseOrder.setBuyerMobile(mobile);
        courseOrder.setSts(StsEnum.ACTIVE.getValue());
        courseOrder.setCreatedBy(userName);
        courseOrder.setCreatedTime(new Date());
        int insertNum = courseOrderMapper.insert(courseOrder);
        if (insertNum <= 0 ) {
            log.error("???????????????????????????????????????????????????{}", JSON.toJSONString(courseOrder));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }
        //??????order_info??????
        AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
        addOrderInfoReq.setOrderType(OrderTypeEnum.COURSE.getValue());
        addOrderInfoReq.setOrderUuid(courseOrder.getUuid());
        orderInfoService.addOrder(addOrderInfoReq);
        return courseOrder.getUuid();
    }

    /**
     * ??????????????????????????????
     * @param courseOrderUuid
     * @param userName
     * @param queryCourseInfoRes
     */
    private void insertCourseOrderContent (String courseOrderUuid, String userName, QueryCourseInfoRes queryCourseInfoRes) {
        CourseOrderDetail courseOrderDetail = new CourseOrderDetail();
        courseOrderDetail.setUuid(UuidUtils.getUuid());
        courseOrderDetail.setOrderUuid(courseOrderUuid);
        courseOrderDetail.setCourseIntro(queryCourseInfoRes.getCourseIntro());
        courseOrderDetail.setCourseContent(queryCourseInfoRes.getCourseContent());
        courseOrderDetail.setCourseTime(queryCourseInfoRes.getCourseTime());
        courseOrderDetail.setCourseUrl(queryCourseInfoRes.getCourseUrl());
        courseOrderDetail.setSts(StsEnum.ACTIVE.getValue());
        courseOrderDetail.setCreatedBy(userName);
        courseOrderDetail.setCreatedTime(new Date());
        int insertNum = courseOrderDetailMapper.insert(courseOrderDetail);
        if (insertNum <= 0 ) {
            log.error("?????????????????????????????????????????????????????????{}", JSON.toJSONString(courseOrderDetail));
            throw new BusinessException(ResEnum.INSERT_DB_ERROR);
        }
    }


    /**
     * ??????????????????????????????????????????????????????????????????
     * @param userType
     * @param userUuid
     * @return
     */
    private String queryStoreUserInfo (Integer userType, String userUuid) {
        if (UserTypeEnum.store.getType().equals(userType)) {
            //???????????????????????????????????????uuid
            ResultRes<StoreUserRes> resResultRes = storeUserFeign.queryStoreUserInfo(userUuid);
            if (!resResultRes.isSuccess()) {
                log.error("??????????????????????????????????????????????????????uuid??????{}", userUuid);
                throw new BusinessException(ResEnum.NON_EXISTENT);
            } else {
                userUuid = resResultRes.getData().getStoreUuid();
            }
        }
        return userUuid;
    }
}
