package com.car.account.web.mapper.curing;

import com.car.account.client.request.curing.QueryCuringListReq;
import com.car.account.client.response.curing.QueryCuringRes;
import com.car.account.web.model.curing.Curing;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Repository
public interface CuringMapper extends Mapper<Curing> {

	/**
	 * 查询养护管理（爱车讲堂）列表信息
	 * @param req
	 * @param checkSts
	 * @return
	 */
	List<QueryCuringRes> queryCuringList(QueryCuringListReq req);

}
