package com.car.order.web.service.content.impl;

import java.util.*;

import com.car.account.client.feign.UserFeign;
import com.car.common.enums.UserTypeEnum;
import com.car.order.client.feign.OrderCaseFrontFeign;
import com.car.order.client.response.order.consult.ConsultOrderDetailRes;
import com.car.order.client.response.technician.TechnicianCaseRes;
import com.car.order.web.mapper.consult.ConsultMapper;
import com.car.order.web.model.consult.Consult;
import com.car.order.web.model.consult.ConsultOrder;
import com.car.order.web.model.instance.OrderCase;
import com.codingapi.txlcn.common.util.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.car.account.client.feign.MessageFeign;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.request.content.AddContentReq;
import com.car.order.client.request.content.CheckContentReq;
import com.car.order.client.request.content.QueryContentListReq;
import com.car.order.client.response.content.ContentDetailRes;
import com.car.order.client.response.content.QueryContentListRes;
import com.car.order.client.response.order.consult.CaseDetails;
import com.car.order.web.mapper.consult.ConsultOrderMapper;
import com.car.order.web.mapper.content.ContentMapper;
import com.car.order.web.mapper.content.ContentResourcesMapper;
import com.car.order.web.model.content.Content;
import com.car.order.web.model.content.ContentResources;
import com.car.order.web.service.consult.OrderConsultService;
import com.car.order.web.service.content.ContentService;
import com.car.order.web.service.technician.TechnicianCaseService;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouz
 * @date 2020/12/28
 */
@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	ContentMapper contentMapper;
	@Autowired
	ContentResourcesMapper contentResourcesMapper;

	@Autowired
	ConsultOrderMapper consultOrderMapper;
	@Autowired
	OrderConsultService orderConsultService;
	@Autowired
	private TechnicianCaseService technicianCaseService;
	@Autowired
	private MessageFeign messageFeign;
	@Autowired
	private OrderCaseFrontFeign orderCaseFrontFeign;
	@Autowired
	private ConsultMapper consultMapper;
	@Autowired
	UserFeign userFeign;

	/**
	 * ????????????
	 * @param addContentReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addContent(AddContentReq addContentReq) {
		log.debug("????????????");
		Content content = new Content();
		content.setUuid(UuidUtils.getUuid());
		content.setSts(StsEnum.ACTIVE.getValue());
		content.setCreatedBy(TokenHelper.getUserName());
		content.setCreatedTime(new Date());
		content.setCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
		BeanUtils.copyProperties(addContentReq, content);

		List<String> resourcesList = addContentReq.getResourcesList();
		if (!CollectionUtils.isEmpty(resourcesList)) {
			List<ContentResources> contentResourcesList = new ArrayList<>();
			ContentResources contentResources;
			for (String resources : resourcesList) {
				contentResources = new ContentResources();
				contentResources.setUuid(UuidUtils.getUuid());
				contentResources.setSts(StsEnum.ACTIVE.getValue());
				contentResources.setCreatedBy(TokenHelper.getUserName());
				contentResources.setCreatedTime(new Date());
				contentResources.setContentUuid(content.getUuid());
				contentResources.setResourcesUrl(resources);
				contentResourcesList.add(contentResources);
			}

			log.debug("??????????????????");
			contentResourcesMapper.batchInsertContentResources(contentResourcesList);
		}
		contentMapper.insert(content);
		return ResultRes.success(content.getUuid());
	}

	/**
	 * ????????????
	 * @param uuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> deleteContent(String uuid) {
		log.debug("????????????");
		Content content = new Content();
		content.setUuid(uuid);
		content = contentMapper.selectByPrimaryKey(content);
		if (StringUtils.isEmpty(content) || StsEnum.INVALID.getValue().equals(content.getSts())) {
			log.error("???????????? ???????????????????????? uuid {}", uuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		content.setSts(StsEnum.INVALID.getValue());
		contentMapper.updateByPrimaryKey(content);

		// ????????????
		contentResourcesMapper.deleteResourcesByContentUuid(uuid);

		return ResultRes.success(uuid);
	}

	/**
	 * ????????????
	 * @param checkContentReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> checkContent(CheckContentReq checkContentReq) {
		log.debug("????????????");
		Content content = new Content();
		content.setUuid(checkContentReq.getUuid());
		content = contentMapper.selectByPrimaryKey(content);

		if (StringUtils.isEmpty(content) || StsEnum.INVALID.getValue().equals(content.getSts())) {
			log.error("???????????? ???????????????????????? uuid {}", checkContentReq.getUuid());
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}

		String checkDesc = CheckStatusEnum.enumOfDesc(checkContentReq.getCheckSts());
		if (StringUtils.isEmpty(checkDesc)) {
			log.error("???????????? ??????????????????????????? checkSt:{}", checkContentReq.getCheckSts());
			throw new BusinessException(ResEnum.INVALID_CHECK_STS);
		}

		if (!checkContentReq.getCheckSts().equals(content.getCheckSts())) {
			content.setCheckSts(checkContentReq.getCheckSts());
			contentMapper.updateByPrimaryKeySelective(content);
			if (OrderTypeEnum.ORDER_REVIEW.getValue().equals(content.getOrderType())) {
				// ????????????

			} else if (OrderTypeEnum.CONSULT.getValue().equals(content.getOrderType())
					|| OrderTypeEnum.ANSWER.getValue().equals(content.getOrderType())) {
				//????????????
				ConsultOrder conord=new ConsultOrder();
				conord.setUuid(content.getOrderUuid());
				ConsultOrder consultOrder1 = consultOrderMapper.selectByPrimaryKey(conord);
				Consult con = new Consult();
				con.setUuid(consultOrder1.getConsultUuid());
				Consult cton = consultMapper.selectByPrimaryKey(con);

				// ?????? ??? ????????????
				log.debug("???????????? ?????????????????? orderUuid:{}", content.getOrderUuid());
				orderConsultService.updateConsultCheckSts(content.getOrderUuid(), content.getOrderType(),
						checkContentReq.getCheckSts());

				//????????????--------?????????????????????
				if(OrderTypeEnum.CONSULT.getValue().equals(content.getOrderType()) && checkContentReq.getCheckSts()==1 ){
					System.out.println(content.getUserUuid());
					Integer userType = userFeign.getUserType(cton.getCarOwnerUuid()).getData();
					log.info(cton.getCarOwnerUuid()+"------------------------"+userType);
					messageFeign.sendMsg("2016", new HashMap(), cton.getCarOwnerUuid(), userType,content.getOrderUuid());
					//??????????????????????????????????????????????????????????????????????????????????????????????????????
					ConsultOrder consultOrder = consultOrderMapper.selectByPrimaryKey(content.getOrderUuid());
					Consult consult = consultMapper.selectByPrimaryKey(consultOrder.getConsultUuid());
					if(consult.getConsultType()==1){
						//?????????????????????
						Map param = Maps.newHashMap("userName", consult.getCarOwnerName());
						messageFeign.sendMsg("2001", param, consult.getTechnicianUuid(), UserTypeEnum.technician.getType(),content.getOrderUuid());
					}
				}else if(OrderTypeEnum.CONSULT.getValue().equals(content.getOrderType()) && checkContentReq.getCheckSts()==2 ){
					//????????????--------??????????????????????????????
					Integer userType = userFeign.getUserType(cton.getCarOwnerUuid()).getData();
					messageFeign.sendMsg("2015", new HashMap(), cton.getCarOwnerUuid(), userType,content.getOrderUuid());
				}

				//???????????????
				if(OrderTypeEnum.ANSWER.getValue().equals(content.getOrderType()) && checkContentReq.getCheckSts()==1){
					//?????????content?????????ID,??????consult_order?????????consult_uuid,?????????????????????
					ConsultOrder consultOrder = consultOrderMapper.selectByPrimaryKey(content.getOrderUuid());
					Consult consult = consultMapper.selectByPrimaryKey(consultOrder.getConsultUuid());
					Map param = Maps.newHashMap("userName", consult.getCarOwnerName());
					messageFeign.sendMsg("2002", param, consult.getTechnicianUuid(), consult.getTechnicianType(),content.getOrderUuid());
					Map map = Maps.newHashMap("technicianName", consult.getTechnicianName());
					messageFeign.sendMsg("2018", map, consult.getCarOwnerUuid(), consult.getCarOwnerType(),content.getOrderUuid());
				}else if(OrderTypeEnum.ANSWER.getValue().equals(content.getOrderType()) && checkContentReq.getCheckSts()==2 && cton.getConsultType()==1){
					//???????????????
					ConsultOrder consultOrder = consultOrderMapper.selectByPrimaryKey(content.getOrderUuid());
					Consult consult = consultMapper.selectByPrimaryKey(consultOrder.getConsultUuid());
					Map param = Maps.newHashMap("userName", consult.getCarOwnerName());
					messageFeign.sendMsg("2003", param, consult.getTechnicianUuid(), consult.getTechnicianType(),content.getOrderUuid());
					Map map =new HashMap();
					map.put("technicianName", consult.getTechnicianName());
					map.put("amount",consultOrder.getOrderAmount());
					messageFeign.sendMsg("2020", map, consult.getCarOwnerUuid(), consult.getCarOwnerType(),content.getOrderUuid());
				}


			} else if (OrderTypeEnum.EXAMPLE.getValue().equals(content.getOrderType())) {
				// ??????
				technicianCaseService.updateTechnicianCaseCheckSts(content.getOrderUuid(),checkContentReq.getCheckSts(), TokenHelper.getUserName());

				//????????????-----------??????????????????
				if (checkContentReq.getCheckSts() == 1) {
					TechnicianCaseRes data = technicianCaseService.queryTechnicianCaseDetail(content.getOrderUuid()).getData();

					Map param = Maps.newHashMap("title", data.getTitle());
					messageFeign.sendMsg("2102", param, data.getTechnicianUuid(), data.getTechnicianType().intValue(),content.getOrderUuid());

				} else if (checkContentReq.getCheckSts() == 2) {
					TechnicianCaseRes data = technicianCaseService.queryTechnicianCaseDetail(content.getOrderUuid()).getData();

					Map param = Maps.newHashMap("title", data.getTitle());
					messageFeign.sendMsg("2103", param, data.getTechnicianUuid(), data.getTechnicianType().intValue(),content.getOrderUuid());
				}

			}
		}
		content.setRemarks(checkContentReq.getRejectDetail());
		contentMapper.updateByPrimaryKeySelective(content);
		return ResultRes.success(checkContentReq.getUuid());
	}

	/**
	 * ??????????????????
	 * @param param
	 * @return
	 */
	@Override
	public PageRes<List<QueryContentListRes>> queryContentList(QueryContentListReq param) {
		log.debug("??????????????????");
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<QueryContentListRes> contentList = contentMapper.queryContentList(param);
		PageInfo<QueryContentListRes> pageInfo = new PageInfo<>(contentList);

		return PageRes.success(contentList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<ContentDetailRes> queryContentDetail(String uuid) {
		log.debug("?????????????????? uuid {}", uuid);
		ContentDetailRes contentDetailRes = contentMapper.queryContentDetail(uuid);
		return ResultRes.success(contentDetailRes);
	}

	@Override
	public ResultRes<CaseDetails> getCaseDetails(String uuid) {
		// ???????????????????????????
		// ??????????????????
		CaseDetails caseDetails = contentMapper.getCaseDetails(uuid);
		return ResultRes.success(caseDetails);
	}

	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<List<CaseDetails>> getMyCaseList(String uuid, Integer type) {
		if (type == 1) {
			List<CaseDetails> myCaseList = contentMapper.getMyCaseList(uuid);
			myCaseList.stream().forEach(e -> {

				// ??????????????????
				// e.setPurchaseNumber();
			});
			return ResultRes.success(myCaseList);
		}
		if (type == 2) {
			List<CaseDetails> myCaseList = contentMapper.getPurchaseCase(uuid);
			myCaseList.stream().forEach(e -> {

				// ??????????????????
				// e.setPurchaseNumber();
			});
			return ResultRes.success(myCaseList);
		}
		return ResultRes.error();
	}

}
