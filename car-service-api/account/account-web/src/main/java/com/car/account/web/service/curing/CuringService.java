package com.car.account.web.service.curing;

import com.car.account.client.request.curing.AddCuringReq;
import com.car.account.client.request.curing.QueryCuringListReq;
import com.car.account.client.response.curing.QueryCuringRes;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/5/30
 */
public interface CuringService {

    /**
     * 新增养护管理（爱车讲堂）信息
     * @param req
     * @return
     */
    ResultRes<String> addCuring(AddCuringReq req);

    /**
     * 查询养护管理（爱车讲堂）信息
     * @param req
     * @return
     */
    PageRes<List<QueryCuringRes>> queryList(QueryCuringListReq req);

    /**
     * 查询养护管理（爱车讲堂）详情
     * @param uuid
     * @return
     */
    ResultRes<QueryCuringRes> queryCuringInfo(String uuid);

    /**
     * 修改养护管理（爱车讲堂）信息
     * @param req
     * @param uuid
     * @return
     */
    ResultRes<String> updateCuringInfo(String uuid, AddCuringReq req);


    /**
     * 删除养护管理（爱车讲堂）信息
     * @param uuid
     * @return
     */
    ResultRes<String> deleteCuringInfo(String uuid);

}
