package com.car.account.web.service.curing.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.car.account.client.enums.goods.SellStatusEnum;
import com.car.account.client.request.curing.AddCuringReq;
import com.car.account.client.request.curing.QueryCuringListReq;
import com.car.account.client.response.curing.QueryCuringRes;
import com.car.account.web.common.utils.UuidUtils;
import com.car.account.web.mapper.curing.CuringMapper;
import com.car.account.web.model.curing.Curing;
import com.car.account.web.service.curing.CuringService;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/5/30
 */
@Slf4j
@Service
public class CuringServiceImpl implements CuringService {

	@Autowired
	private CuringMapper curingMapper;

	/**
	 * 新增养护管理（爱车讲堂）信息
	 * @param req
	 * @return
	 */
	@Override
	public ResultRes<String> addCuring(AddCuringReq req) {
		String userName = TokenHelper.getUserName();
		Curing curing = new Curing();
		BeanUtils.copyProperties(req, curing);
		curing.setUuid(UuidUtils.getUUID());
		curing.setSts(StsEnum.ACTIVE.getValue());
		curing.setCheckSts(SellStatusEnum.REPERTORY.getValue());
		curing.setCreatedTime(new Date());
		curing.setCreatedBy(userName);
		curingMapper.insert(curing);
		return ResultRes.success(curing.getUuid());
	}

	/**
	 * 询养护管理（爱车讲堂）信息
	 * @param req
	 * @return
	 */
	@Override
	public PageRes<List<QueryCuringRes>> queryList(QueryCuringListReq req) {
		// 获取用户类型
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<QueryCuringRes> queryCuringResList = curingMapper.queryCuringList(req);
		PageInfo<QueryCuringRes> pageInfo = new PageInfo<>(queryCuringResList);
		return PageRes.success(queryCuringResList, pageInfo.getPageSize(), (int) pageInfo.getTotal(),
				pageInfo.getPages());
	}

	/**
	 * 查询养护管理（爱车讲堂）详情
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<QueryCuringRes> queryCuringInfo(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("查询养护管理（爱车讲堂）详情uuid不能为空");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		QueryCuringRes res = new QueryCuringRes();
		Curing curingSelect = new Curing();
		curingSelect.setUuid(uuid);
		curingSelect.setSts(StsEnum.ACTIVE.getValue());
		Curing curingRes = curingMapper.selectOne(curingSelect);
		if (null != curingRes) {
			BeanUtils.copyProperties(curingRes, res);
		}
		return ResultRes.success(res);
	}

	/**
	 * 修改养护管理（爱车讲堂）信息
	 * @param req
	 * @return
	 */
	@Override
	public ResultRes<String> updateCuringInfo(String uuid, AddCuringReq req) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("修改养护管理（爱车讲堂）信息uuid不能为空");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		// 获取用户名称
		String userName = TokenHelper.getUserName();
		Curing curing = new Curing();
		BeanUtils.copyProperties(req, curing);
		curing.setUuid(uuid);
		curing.setLastUpdatedBy(userName);
		curing.setLastUpdatedTime(new Date());
		curingMapper.updateByPrimaryKeySelective(curing);
		return ResultRes.success(uuid);
	}

	/**
	 * 删除养护管理（爱车讲堂）信息
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<String> deleteCuringInfo(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("删除养护管理（爱车讲堂）信息uuid不能为空");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		// 获取用户名称
		String userName = TokenHelper.getUserName();
		Curing curingUpdate = new Curing();
		curingUpdate.setUuid(uuid);
		curingUpdate.setSts(StsEnum.INVALID.getValue());
		curingUpdate.setLastUpdatedTime(new Date());
		curingUpdate.setLastUpdatedBy(userName);
		curingMapper.updateByPrimaryKeySelective(curingUpdate);
		return ResultRes.success(uuid);
	}
}
