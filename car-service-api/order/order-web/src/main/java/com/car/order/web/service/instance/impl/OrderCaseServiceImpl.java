package com.car.order.web.service.instance.impl;

import com.car.account.client.feign.StoreFegin;
import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.feign.TechnicianFegin;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.account.client.response.store.StoreUserRes;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.common.enums.UserTypeEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.TokenHelper;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.PayMethodEnum;
import com.car.order.client.request.order.instance.QueryOrderCaseListReq;
import com.car.order.client.response.order.CarOwnerInfoRes;
import com.car.order.client.response.order.TechnicianInfoRes;
import com.car.order.client.response.order.driving.AfterSaleInfoRes;
import com.car.order.client.response.order.instance.OrderCaseDetailRes;
import com.car.order.client.response.order.instance.OrderCaseInfoListRes;
import com.car.order.client.response.order.instance.OrderDetailRes;
import com.car.order.client.response.technician.TechnicianCaseRes;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.dto.OrderCaseDto;
import com.car.order.web.mapper.instance.OrderCaseMapper;
import com.car.order.web.model.instance.OrderCase;
import com.car.order.web.model.order.OrderInfo;
import com.car.order.web.service.instance.OrderCaseService;
import com.car.order.web.service.order.OrderInfoService;
import com.car.order.web.service.technician.TechnicianCaseService;
import com.car.system.client.feign.SystemFeign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/31
 */
@Slf4j
@Service
public class OrderCaseServiceImpl implements OrderCaseService {

    @Autowired
    OrderCaseMapper orderCaseMapper;

    @Autowired
    private StoreFegin storeFegin;

    @Autowired
    StoreUserFeign storeUserFeign;

    @Autowired
    private TechnicianFegin technicianFegin;

    @Autowired
    private TechnicianCaseService technicianCaseService;

    @Autowired
    private SystemFeign systemFeign;

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 查询案例订单列表
     * @param param
     * @return
     */
    @Override
    public PageRes<List<OrderCaseInfoListRes>> queryOrderCaseList(QueryOrderCaseListReq param) {
        log.debug("查询案例订单列表");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
      /*  if(param.getType()==2){
            param.setCarOwnerUuid(TokenHelper.getUserUuid());
            param.setOrderSts(1);
        }else if(param.getType()==1){
            param.setTechnicianUuid(TokenHelper.getUserUuid());
            param.setOrderSts(1);
        }*/
        List<OrderCaseInfoListRes> orderCaseInfoList = orderCaseMapper.queryOrderCaseInfoList(param);
        orderCaseInfoList.stream().forEach(e->{
            TechnicianCaseRes data = technicianCaseService.queryTechnicianCaseDetail(e.getCaseUuid()).getData();
            if(UserTypeEnum.technician.getType()==(data.getTechnicianType().intValue())){
                TechnicianRes data1 = technicianFegin.queryTechnicianDetail(data.getTechnicianUuid()).getData();
                e.setCybAuth(data1.getCybAuth().byteValue());
                e.setWorkingYear(data1.getWorkingYear());
                e.setAddressProvince(data1.getAddressProvince());
                e.setTechnicianType(data.getTechnicianType());
            }else if(UserTypeEnum.store.getType()==(data.getTechnicianType().intValue())){
                StoreUserRes data2 = storeUserFeign.queryStoreUserInfo(data.getTechnicianUuid()).getData();
                StoreDetailRes data1 = storeFegin.queryStoreDetail(data2.getStoreUuid()).getData();
                if(data1!=null){
                    e.setStoreType(data1.getStoreType());
                    e.setAddressProvince(data1.getCompanyAddressProvince());
                    e.setTechnicianType(data.getTechnicianType());
                }
            }
            Long casePurchaseNumber = orderCaseMapper.getCasePurchaseNumber(e.getCaseUuid());
            e.setPurchaseNumber(casePurchaseNumber);
        });
        PageInfo<OrderCaseInfoListRes> pageInfo = new PageInfo<>(orderCaseInfoList);
        return PageRes.success(orderCaseInfoList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 查询案例订单详情
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<OrderCaseDetailRes> queryOrderCaseDetail(String uuid) {
        log.debug("查询案例订单详情 uuid {}",uuid);
        OrderCaseDetailRes orderCaseInfoRes = null;
        OrderCaseDto orderCaseDto = orderCaseMapper.queryOrderCaseDetail(uuid);
        if (!StringUtils.isEmpty(orderCaseDto)){
            orderCaseInfoRes = new OrderCaseDetailRes();
            orderCaseInfoRes.setUuid(orderCaseDto.getUuid());
            orderCaseInfoRes.setEvaluateSts(orderCaseDto.getEvaluateSts());
            orderCaseInfoRes.setTechnicianScore(orderCaseDto.getTechnicianScore());
            orderCaseInfoRes.setCaseInfoListRes(orderCaseDto.getCaseInfoListRes());
            orderCaseInfoRes.setCreatedTime(orderCaseDto.getCreatedTime());
            OrderDetailRes orderInfoRes = new OrderDetailRes();
            BeanUtils.copyProperties(orderCaseDto,orderInfoRes);
            orderCaseInfoRes.setOrderDetailRes(orderInfoRes);
            TechnicianInfoRes technicianInfoRes = new TechnicianInfoRes();
            BeanUtils.copyProperties(orderCaseDto,technicianInfoRes);
            /*if(orderCaseDto.getCarOwnerType()==3){
                StoreDetailRes data = storeFegin.queryStoreDetail(orderCaseDto.getTechnicianUuid()).getData();
            }else if(orderCaseDto.getCarOwnerType()==2){
                TechnicianRes data = technicianFegin.queryTechnicianDetail(technicianInfoRes.getTechnicianUuid()).getData();
                if(data!=null && data.getCybAuth()!=null){
                    technicianInfoRes.setCybAuth(data.getCybAuth().toString());
                }
            }*/
            orderCaseInfoRes.setTechnicianInfoRes(technicianInfoRes);
            CarOwnerInfoRes carOwnerInfoRes = new CarOwnerInfoRes();
            BeanUtils.copyProperties(orderCaseDto,carOwnerInfoRes);
            orderCaseInfoRes.setCarOwnerInfoRes(carOwnerInfoRes);

            if (!StringUtils.isEmpty(orderCaseDto.getRefundSts())) {
                AfterSaleInfoRes afterSaleInfoRes = new AfterSaleInfoRes();
                BeanUtils.copyProperties(orderCaseDto,afterSaleInfoRes);
                orderCaseInfoRes.setAfterSaleInfoRes(afterSaleInfoRes);
            }
        }
        OrderInfo data = orderInfoService.getByOrderUuid(uuid).getData();
        orderCaseInfoRes.setEvaluateSts(data.getEvaluateSts());
        return ResultRes.success(orderCaseInfoRes);
    }

    /**
     * 案例订单信息导出
     * @param exportReq
     * @param response
     */
    @Override
    public void exportOrderDrivingList(QueryOrderCaseListReq exportReq, HttpServletResponse response) {
        log.debug("案例订单信息导出");
        try {
            List<OrderCaseInfoListRes> orderCaseInfoList = orderCaseMapper.queryOrderCaseInfoList(exportReq);
            //读取模板文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(Constants.ORDER_INSTANCE_INFO_EXPORT_TEMPLATE);
            //设置空行默认属性
            List<OrderCaseInfoListRes> excelList = ExcelUtils.setFieldValue(orderCaseInfoList);
            Workbook wb = new XSSFWorkbook(resourceAsStream);
            Sheet sheet = wb.getSheetAt(0);
            //从第三行开始写入
            int firstRowIndex = sheet.getFirstRowNum()+2;
            for (int rowIndex = firstRowIndex; rowIndex < excelList.size()+2; rowIndex++) {
                //行样式
                Row rowStyle = (rowIndex % 2) == 0?sheet.getRow(2): sheet.getRow(3);
                //单列样式
                CellStyle cellStyle = ExcelUtils.getExcelFormat(rowStyle.getCell(1));
                CellStyle cellStyle1 = ExcelUtils.getExcelFormat(rowStyle.getCell(0));
                Row row = sheet.getRow(rowIndex);
                if(row == null){
                    row = sheet.createRow(rowIndex);
                }
                row.setHeight(rowStyle.getHeight());
                OrderCaseInfoListRes exportDto = excelList.get(rowIndex - 2);
                ExcelUtils.setCell(row,cellStyle1,0,rowIndex-1);
                ExcelUtils.setCell(row,cellStyle,1,exportDto.getOrderNum());
                ExcelUtils.setCell(row,cellStyle,2, exportDto.getCaseName());
                ExcelUtils.setCell(row,cellStyle,3,exportDto.getCaseNum());

                ExcelUtils.setCell(row,cellStyle,4,exportDto.getCreatedTime());
                ExcelUtils.setCell(row,cellStyle,5,exportDto.getTechnicianName());
                ExcelUtils.setCell(row,cellStyle,6, exportDto.getTechnicianMobile());
                ExcelUtils.setCell(row,cellStyle,7, "¥ "+exportDto.getOrderAmount());
                ExcelUtils.setCell(row,cellStyle,8, exportDto.getCarOwnerName());
                ExcelUtils.setCell(row,cellStyle,9, exportDto.getCarOwnerMobile());
                ExcelUtils.setCell(row,cellStyle,10, PayMethodEnum.enumOfDesc(exportDto.getPayType()));
                ExcelUtils.setCell(row,cellStyle,11, OrderStsEnum.enumOfDesc(exportDto.getOrderSts()));
            }
            ExcelUtils.responseWrite(wb,response, Constants.ORDER_INSTANCE_INFO_EXPORT_TEMPLATE);
        } catch (Exception ex){
            log.error("案例订单信息导出异常，异常原因：{}", ExceptionUtils.stackTraceToString(ex));
        }
    }

    @Override
    public ResultRes<List<OrderCaseDetailRes>> getMyorderCase() {
        OrderCase orderCase=new OrderCase();
        if(TokenHelper.getUserType().equals(UserTypeEnum.store.getType())){
            StoreDetailRes data = storeFegin.queryStoreDetailByUser().getData();
            orderCase.setTechnicianUuid(data.getUuid());
        }else{
            orderCase.setTechnicianUuid(TokenHelper.getUserUuid());
        }
        List<OrderCaseDetailRes> myorderCase = orderCaseMapper.getMyorderCase(orderCase);
        myorderCase.stream().forEach(e->{
            Long casePurchaseNumber = orderCaseMapper.getCasePurchaseNumber(e.getCaseUuid());
            e.setPurchaseNumber(casePurchaseNumber);
        });
        return ResultRes.success(myorderCase);

    }
}

