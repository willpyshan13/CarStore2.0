package com.car.account.web.service.withdrawal.impl;

import com.car.account.client.enums.withdrawal.WithdrawalTypeEnum;
import com.car.account.client.enums.withdrawal.WithdrawalUserRoleEnum;
import com.car.account.client.request.withdrawal.AddWithdrawalReq;
import com.car.account.client.request.withdrawal.QueryWithdrawalListReq;
import com.car.account.client.request.withdrawal.UpdateWithdrawalReq;
import com.car.account.client.request.withdrawal.WithdrawalDetailReq;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.account.client.response.withdrawal.QueryWithdrawalListRes;
import com.car.account.client.response.withdrawal.WithdrawalDetailRes;
import com.car.account.client.response.withdrawal.WithdrawalRes;
import com.car.account.web.common.constants.WithdrawalConstants;
import com.car.account.web.mapper.store.StoreAccountMapper;
import com.car.account.web.mapper.store.StoreMapper;
import com.car.account.web.mapper.technician.TechnicianAccountMapper;
import com.car.account.web.mapper.technician.TechnicianMapper;
import com.car.account.web.mapper.withdrawal.WithdrawalDetailMapper;
import com.car.account.web.mapper.withdrawal.WithdrawalMapper;
import com.car.account.web.model.store.Store;
import com.car.account.web.model.store.StoreAccount;
import com.car.account.web.model.technician.TechnicianAccount;
import com.car.account.web.model.withdrawal.Withdrawal;
import com.car.account.web.model.withdrawal.WithdrawalDetail;
import com.car.account.web.service.dict.SysDictService;
import com.car.account.web.service.store.StoreService;
import com.car.account.web.service.withdrawal.WithdrawalService;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/26
 */
@Slf4j
@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    WithdrawalMapper withdrawalMapper;
    @Autowired
    WithdrawalDetailMapper withdrawalDetailMapper;

    @Autowired
    StoreAccountMapper storeAccountMapper;
    @Autowired
    StoreMapper storeMapper;

    @Autowired
    TechnicianMapper technicianMapper;
    @Autowired
    TechnicianAccountMapper technicianAccountMapper;
    @Autowired
    StoreService storeService;
    @Autowired
    SysDictService sysDictService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> addWithdrawal(AddWithdrawalReq addWithdrawalReq) {
        if(addWithdrawalReq.getWithdrawalAmount().compareTo(BigDecimal.ZERO)==0){
            return ResultRes.error("提现金额不能为0");
        }

        String userUuid = TokenHelper.getUserUuid();
        Integer userType = TokenHelper.getUserType();
        String  mobile = TokenHelper.getLoginToken().getUserMobile();
        Withdrawal withdrawal = new Withdrawal();
        BeanUtils.copyProperties(addWithdrawalReq,withdrawal);
        withdrawal.setUuid(UuidUtils.getUuid());
        withdrawal.setCreatedBy(TokenHelper.getUserName());
        withdrawal.setCreatedTime(new Date());
        withdrawal.setMobile(mobile);
        withdrawal.setUserName(TokenHelper.getLoginToken().getUserName());
        withdrawal.setUserUuid(userUuid);
        withdrawal.setUserRole(userType);
        withdrawal.setCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
        withdrawal.setSts(0);

        if (UserTypeEnum.technician.getType().equals(userType)){
            TechnicianAccount technicianAccount = new TechnicianAccount();
            technicianAccount.setTechnicianUuid(userUuid);
            TechnicianAccount account = technicianAccountMapper.selectOne(technicianAccount);
            if(addWithdrawalReq.getWithdrawalAmount().compareTo(account.getWaitAmount())>0){
                return ResultRes.error("提现金额不能为大于可提现金额！");
            }
            technicianAccount.setUuid(account.getUuid());
            technicianAccount.setCardNumbers(addWithdrawalReq.getCardNumbers());
            technicianAccount.setSubBranchName(addWithdrawalReq.getSubBranchName());
            technicianAccount.setWaitAmount(account.getWaitAmount().subtract(addWithdrawalReq.getWithdrawalAmount()));
            technicianAccount.setAccountAmount(account.getAccountAmount().subtract(addWithdrawalReq.getWithdrawalAmount()));
            technicianAccount.setFrozenAmt(account.getFrozenAmt().add(addWithdrawalReq.getWithdrawalAmount()));
            technicianAccountMapper.updateByPrimaryKeySelective(technicianAccount);
            withdrawal.setBalanceAmount(technicianAccount.getAccountAmount());

        }else  if(UserTypeEnum.store.getType().equals(userType)){
            Store store =  storeService.getStore();
            withdrawal.setStoreTypeName(sysDictService.querySysDict(store.getStoreType()).getLableDesc());
            withdrawal.setStoreName(store.getStoreName());
            StoreAccount storeAccount = new StoreAccount();
            storeAccount.setStoreUuid(store.getUuid());
            StoreAccount account = storeAccountMapper.selectOne(storeAccount);
            storeAccount.setUuid(account.getUuid());

            storeAccount.setCardNumbers(addWithdrawalReq.getCardNumbers());
            storeAccount.setSubBranchName(addWithdrawalReq.getSubBranchName());
            if(addWithdrawalReq.getWithdrawalAmount().compareTo(account.getWaitAmount())>0){
                return ResultRes.error("提现金额不能为大于可提现金额！");
            }
            storeAccount.setWaitAmount(account.getWaitAmount().subtract(addWithdrawalReq.getWithdrawalAmount()));
            storeAccount.setAccountAmount(account.getAccountAmount().subtract(addWithdrawalReq.getWithdrawalAmount()));
            storeAccount.setFrozenAmt(account.getFrozenAmt().add(addWithdrawalReq.getWithdrawalAmount()));
            storeAccountMapper.updateByPrimaryKeySelective(storeAccount);
            withdrawal.setBalanceAmount(storeAccount.getAccountAmount());
        }
        withdrawalMapper.insert(withdrawal);
        return ResultRes.success();
    }

    /**
     * 删除提现记录
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<String> deleteWithdrawal(String uuid) {
        log.debug("删除提现记录");
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setUuid(uuid);
        withdrawal = withdrawalMapper.selectByPrimaryKey(withdrawal);
        if (withdrawal == null || StsEnum.INVALID.getValue().equals(withdrawal.getSts())) {
            log.error("删除 未匹配到对应数据 uuid {}",uuid);
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }

        withdrawal.setSts(StsEnum.INVALID.getValue());
        withdrawalMapper.updateByPrimaryKeySelective(withdrawal);
        withdrawalDetailMapper.deleteWithdrawalDetail(uuid);
        return ResultRes.success(uuid);
    }

    /**
     * 提现审核
     * @param updateWithdrawalReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes<String> checkWithdrawal(UpdateWithdrawalReq updateWithdrawalReq) {
        log.debug("提现审核");
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setUuid(updateWithdrawalReq.getUuid());
        withdrawal = withdrawalMapper.selectByPrimaryKey(withdrawal);
        if (withdrawal == null || StsEnum.INVALID.getValue().equals(withdrawal.getSts())) {
            log.error("修改 未匹配到对应数据 uuid {}",updateWithdrawalReq.getUuid());
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }

        BeanUtils.copyProperties(updateWithdrawalReq,withdrawal);

        if (UserTypeEnum.technician.getType().equals(withdrawal.getUserRole())) {
            TechnicianAccount technicianAccount = new TechnicianAccount();
            technicianAccount.setTechnicianUuid(withdrawal.getUserUuid());
            TechnicianAccount account = technicianAccountMapper.selectOne(technicianAccount);
            technicianAccount.setUuid(account.getUuid());
            technicianAccount.setFrozenAmt(account.getFrozenAmt().subtract(withdrawal.getWithdrawalAmount()));

            //审核驳回金额退回
            if(CheckStatusEnum.CHECK_REJECTED.getValue().equals(updateWithdrawalReq.getCheckSts())){
                technicianAccount.setWaitAmount(account.getWaitAmount().add(withdrawal.getWithdrawalAmount()));
                technicianAccount.setAccountAmount(account.getAccountAmount().add(withdrawal.getWithdrawalAmount()));
            }else {
                technicianAccount.setWithdrawAmount(account.getWithdrawAmount().add(withdrawal.getWithdrawalAmount()));
            }
            technicianAccountMapper.updateByPrimaryKeySelective(technicianAccount);
        } else if (UserTypeEnum.store.getType().equals(withdrawal.getUserRole())) {
            StoreAccount storeAccount = new StoreAccount();
            storeAccount.setStoreUuid(withdrawal.getUserUuid());
            StoreAccount account = storeAccountMapper.selectOne(storeAccount);
            storeAccount.setUuid(account.getUuid());
            storeAccount.setFrozenAmt(account.getFrozenAmt().subtract(withdrawal.getWithdrawalAmount()));
            //审核驳回金额退回
            if(CheckStatusEnum.CHECK_REJECTED.getValue().equals(updateWithdrawalReq.getCheckSts())){
                storeAccount.setWaitAmount(account.getWaitAmount().add(withdrawal.getWithdrawalAmount()));
                storeAccount.setAccountAmount(account.getAccountAmount().add(withdrawal.getWithdrawalAmount()));
            }else {
                storeAccount.setWithdrawAmount(account.getWithdrawAmount().add(withdrawal.getWithdrawalAmount()));
            }
            storeAccountMapper.updateByPrimaryKeySelective(storeAccount);
        }

        withdrawalMapper.updateByPrimaryKeySelective(withdrawal);

        return ResultRes.success(updateWithdrawalReq.getUuid());
    }

    /**
     * 查询提现列表
     * @param param
     * @return
     */
    @Override
    public PageRes<List<QueryWithdrawalListRes>> queryWithdrawalList(QueryWithdrawalListReq param) {
        log.debug("查询提现列表");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<QueryWithdrawalListRes> withdrawalList = withdrawalMapper.queryWithdrawalList(param);
        PageInfo<QueryWithdrawalListRes> pageInfo = new PageInfo<>(withdrawalList);

        return PageRes.success(withdrawalList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
    }

    /**
     * 查询提现详情
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<WithdrawalRes> queryWithdrawalDetailByUuid(String uuid) {
        log.debug("查询  验证提现记录是否存在 userUuid {}",uuid);
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setUuid(uuid);
        Withdrawal withdrawal = withdrawalMapper.selectByPrimaryKey(queryWithdrawal);
        if (withdrawal == null || StsEnum.INVALID.getValue().equals(withdrawal.getSts())) {
            log.error("查询  提现记录未匹配到对应数据：userUuid{}",uuid);
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        WithdrawalRes withdrawalRes = withdrawalMapper.queryWithdrawalDetailByUuid(uuid);


        return ResultRes.success(withdrawalRes);
    }

    /**
     * 提现信息导出
     * @param exportReq
     * @param response
     */
    @Override
    public void exportWithdrawalList(QueryWithdrawalListReq exportReq, HttpServletResponse response) {
        log.debug("提现信息导出");
        try {
            List<QueryWithdrawalListRes> withdrawalList = withdrawalMapper.queryWithdrawalList(exportReq);
            //读取模板文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(WithdrawalConstants.WITHDRAWAL_EXPORT_TEMPLATE);
            //设置空行默认属性
            List<QueryWithdrawalListRes> excelList = ExcelUtils.setFieldValue(withdrawalList);
            Workbook wb = new XSSFWorkbook(resourceAsStream);
            Sheet sheet = wb.getSheetAt(0);
            //从第三行开始写入
            int firstRowIndex = sheet.getFirstRowNum()+2;
            QueryWithdrawalListRes exportDto;
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
                exportDto = excelList.get(rowIndex - 2);
                ExcelUtils.setCell(row,cellStyle1,0,rowIndex-1);
                ExcelUtils.setCell(row,cellStyle,1,exportDto.getUserName());
                ExcelUtils.setCell(row,cellStyle,2,exportDto.getMobile());
                if (!StringUtils.isEmpty(exportDto.getCreatedTime())) {
                    ExcelUtils.setCell(row,cellStyle,3, DateUtil.dateToStr(exportDto.getCreatedTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
                }

                ExcelUtils.setCell(row,cellStyle,4,exportDto.getWithdrawalAmount().toString());
                ExcelUtils.setCell(row,cellStyle,5,WithdrawalUserRoleEnum.enumOfDesc(exportDto.getUserRole()));
                if (!StringUtils.isEmpty(exportDto.getLastUpdatedTime())) {
                    ExcelUtils.setCell(row,cellStyle,6,DateUtil.dateToStr(exportDto.getLastUpdatedTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
                }
                ExcelUtils.setCell(row,cellStyle,7, CheckStatusEnum.enumOfDesc(exportDto.getCheckSts()));
            }
            ExcelUtils.responseWrite(wb,response, WithdrawalConstants.WITHDRAWAL_EXPORT_TEMPLATE);
        } catch (Exception ex){
            log.error("提现信息导出异常，异常原因：{}", ExceptionUtils.stackTraceToString(ex));
        }
    }




}
