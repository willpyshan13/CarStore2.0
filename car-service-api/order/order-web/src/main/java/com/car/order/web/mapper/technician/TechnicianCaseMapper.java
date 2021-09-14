package com.car.order.web.mapper.technician;

import com.car.common.req.PageReq;
import com.car.order.client.request.technician.CaseForVehicleListRep;
import com.car.order.client.response.technician.CaseForTechnicianItemRes;
import com.car.order.client.response.technician.CaseForVehicleItemRes;
import com.car.order.client.response.technician.TechnicianCaseRes;
import com.car.order.web.model.technician.cases.TechnicianCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TechnicianCaseMapper extends Mapper<TechnicianCase> {

    /**
     * 查询技师案例详情
     * @param uuid
     * @return
     */
    TechnicianCaseRes queryTechnicianCaseDetail(String uuid);

    /**
     * 查询案例列表 技师查询
     * @param pageReq
     * @return
     */
    List<CaseForTechnicianItemRes> queryCaseForTechnicianList(PageReq pageReq);

    /**
     * 查询案例列表 技师查询
     * @param caseForVehicleListRep
     * @return
     */
    List<CaseForVehicleItemRes> queryCaseForVehicleList(CaseForVehicleListRep caseForVehicleListRep);

    /**
     * 查询案例列表 技师查询
     * @param caseForVehicleListRep
     * @return
     */
    List<CaseForVehicleItemRes> queryCaseForVehicleListTwo(CaseForVehicleListRep caseForVehicleListRep);


    /**
     *根据案例uuid获取技师名称工龄等基础信息呵品牌加型号等信息，
     * @param uuid
     * @return
     */
    TechnicianCaseRes queryTechnicianCaseDetails(String uuid);
}