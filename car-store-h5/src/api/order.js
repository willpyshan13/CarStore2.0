import {get, post, put, deleteUrl, postUrl } from './index'

import base from './base'

const api = base + 'order/'

export default {
	// 提问生成订单
	
	myQuestSend(params){
		return post(api + 'orderConsult/consult', params)
	},
	// 我的问题
	myQuests(params){
		return post(api + 'store/queryContentList', params)
	},
	myQuestsDetail(params){
		return get(api + 'orderConsultFront/queryOrderConsultDetail/' + params)
	},
	// 我的案例
	myCase(params){
		return post(api + 'case/queryCaseForTechnicianList', params)
	},
	myCaseDetail(params){
		return get(api + 'case/queryCaseDetail/' + params)
	},
    // 发布案例（技师）
    getJsFabu(params) {
        return post(api + 'case/addCase', params)
    },
    // 编辑案例
    editFabu(params) {
        return get(api + 'case/queryCaseDetail/' + params)
    },
    // 编辑案例
    changeFabu(params) {
        return put(api + 'case/updateTechnicianCase', params)
    },
    // 代驾订单详情
    daijiaOrder(params) {
        return get(api + 'orderDriving/queryOrderDrivingDetail/' + params)
    },
    // 回答问题内容详情
    wendaDetail(params) {
        return get(api + 'orderConsult/queryOrderConsultDetail/' + params)
    },
    // 回答问题（线上服务）
    answerTheQuestion(params) {
        return put(api + 'orderConsult/updateConsultAnswer', params)
    },
    // 维修保养（我的）
    baoyang(params) {
        return post(api + 'orderGoodsFront/queryOrderGoodsList', params)
    },
    // 维修保养详情
    baoyangDetail(params) {
        return get(api + 'orderGoodsFront/queryOrderGoodsDetail/' + params)
    },
    // 查询订单各状态数量
    searNum(params) {
        return post(api + 'orderGoodsFront/queryOrderStsNum', params)
    },

    //案例订单（我的）
    anli_order(params) {
        return post(api + 'orderCaseFront/queryOrderCaseList', params)
    },
    // 案例详情
    anli_detail(params) {
        return get(api + 'orderCaseFront/queryOrderCaseDetail/' + params)
    },
    // 线上咨询
    onlineZixun(params) {
        return post(api + 'orderConsultFront/queryOrderConsultList', params)
    },
    // 线上咨询详情
    online_detail(params) {
        return get(api + 'orderConsultFront/queryOrderConsultDetail/' + params)
    },
    // Dtc新增
    dtcAdd(params) {
        return post(api + 'dtc/add', params)
    },
    // Dtc编辑
    dtcEdit(params, uuid) {
        return put(api + 'dtc/updateById/' + uuid, params)
    },
    // Dtc详情
    dtcDetail(params) {
        return get(api + 'dtc/getById/' + params)
    },
    // Dtc新增故障
    dtcGuZhang(params) {
        return post(api + 'dtcOrder/addOrder', params)
    },
    // Dtc列表
    dtcList(params) {
        return post(api + 'dtcOrder/myList', params)
    },
    // Dtc列表详情
    dtcOrderDetail(params) {
        return get(api + 'dtcOrder/getById/' + params)
    },
    // 支付管理
    payOrder(params) {
        return post(api + 'pay/payOrder', params)
    },
    // 根据id查询订单状态详情
    allQingQiu(params) {
        return get(api + 'dtcOrder/queryOrderSts/' + params)
    },
    // 课程列表详情
    kechengDetail(params) {
        return get(api + 'course/getById/' + params)
    },
    // 课程列表
    kechengList(params) {
        return post(api + 'course/list', params)
    },
    // 新增订单
    addJiaoyuOrder(params) {
        return post(api + 'courseOrder/add', params)
    },
    // 教程列表
    jiaochengList(params) {
        return post(api + 'courseOrder/list', params)
    },
    // 教程列表详情
    jiaochengDetail(params) {
        return get(api + 'courseOrder/getById/' + params)
    },

    // 现场下单新增
    localOrder(params) {
        return post(api + 'sceneOrder/addSceneOrder', params)
    },
    // 现场下单详情
    localOrderDetail(params) {
        return get(api + 'sceneOrder/querySceneOrderInfo/' + params)
    },
    // 现场下单技师信息新增
    localJishiAdd(params) {
        return post(api + 'sceneOrderTechnician/addSceneTechnicianInfo', params)
    },
    // 共享技师列表（技师预约订单管理）
    shareJs(params) {
        return post(api + 'shareTechnicianOrder/queryShareTechnicianOrderList', params)
    },
    // 共享技师详情
    shareJsDetail(params) {
        return get(api + 'shareTechnicianOrder/queryShareTechnicianOrder/' + params)
    },
    // 共享技师同意接单
    agreeOrder(params) {
        return put(api + 'shareTechnicianOrder/receiveShareTechnicianOrder/' + params)
    },
    // 现场订单抢单
    wancheng(params) {
        return get(api + 'sceneOrder/completeOrder/' + params)
    },
    // 案例详情
    addCaseOrder (params) {
    	return get(api + 'orderCaseFront/orderCaseTwo/' + params)
    },
    // 案例详情
    queryCaseDetail (params) {
    	return get(api + 'case/queryCaseDetail/' + params)
    },
    // 
    queryOrderInfo (params) {
    	return get(api + 'orderFront/queryOrderInfo/' + params)
    },
    // 
    queryOrderSts (params) {
    	return get(api + 'order/queryOrderSts/' + params)
    },
    // 我的提问
    queryOrderConsultList(params){
			return post(api + 'orderConsultFront/queryOrderConsultList', params)
		},
		// 我的案例
		queryOrderCaseList (params) {
			return post(api + 'orderCaseFront/queryOrderCaseList/', params)
		},
		// 咨询详情
		queryConsultDetail (uuid, params) {
			return get(api + `orderConsult/queryConsultDetail/${uuid}`, params)
		},
		//
		addAuditorOrder (params) {
			return post(api + 'orderConsult/addAuditorOrderTwo', params)
		},
		
}