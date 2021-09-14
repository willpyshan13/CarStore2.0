package com.car.account.client.feign;

import com.car.account.client.request.technician.UpdateTechnicianAccountReq;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyp
 * @date 2021/1/21 21:39
 */
@FeignClient(value = "account")
public interface TechnicianFegin {

    /**
     * 技师详情
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "/technician/queryTechnicianDetail/{uuid}")
    ResultRes<TechnicianRes> queryTechnicianDetail(@PathVariable("uuid") String uuid);

    /**
     * 修改技师账户信息
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/technicianAccount/updateTechnicianAccount")
    ResultRes<String> updateTechnicianAccount(@RequestBody @Valid UpdateTechnicianAccountReq req);

    /**
     * 修改技师问答数量
     *
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/technician/updateQaCount/{uuid}", method = RequestMethod.PUT)
    public ResultRes<String> updateQaCount(@PathVariable("uuid") String uuid);

    /**
     * 修改技师评分
     *
     * @param uuid
     * @param score
     * @return
     */
    @PostMapping(value = "/technician/updateScore")
    ResultRes<String> updateScore(@RequestParam("uuid") String uuid, @RequestParam("score") BigDecimal score);

    /**
     * 查询技师品牌
     * @param uuid
     * @return
     */
    @GetMapping(value = "/technician/queryTechnicianBrand/{uuid}")
    public ResultRes<List<String>> queryTechnicianBrand(@PathVariable("uuid") String uuid);
}
