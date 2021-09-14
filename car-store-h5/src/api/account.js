import {get, post, put, deleteUrl, postUrl } from './index'

import base from './base'

const api = base + 'account/'

export default {
    getOneType(params) {
        return get(api + 'goodsParent/queryListByParent/' + params)
    },
    shangjiaSure(params) {
        return post(api + 'goods/addGoods', params)
    },
    getDetail(params) {
        return get(api + 'goods/queryGoodsDetail/' + params)
    },
    // 商品编辑
    editShangjiaSure(params) {
        return put(api + 'goods/updateGoods', params)
    },
    // 店铺注册
    addShop(params) {
        return post(api + 'store/addStore', params)
    },

    // 技师注册
    rgisterAdd(params) {
        return post(api + 'technician/register', params)
    },
    rgisterEdit(params) {
        return get(api + 'technician/queryTechnicianDetail/' + params)
    },
    //  车辆类型
    carType(type = '0001') {
        return get(api + 'vehicleConfig/queryList/' + type)
    },
    // 查询店铺列表
    searchShop(params) {
        return post(api + 'store/queryStoreList', params)
    },
    // 新增技师关联店铺
    guanlianShop(params) {
        return post(api + 'storeTechnician/addTechnicianRelateStore', params)
    },
    guanlianShopNoToken(params) {
        return post(api + 'storeTechnician/addStoreTechnicianRelate', params)
    },

    // 根据区域查询店铺列表
    searchDianpu(params) {
        return post(api + 'storeTechnician/queryStoreListByArea', params)
    },
    // 查询店铺技师关联列表
    shopJishiGuanlian(params) {
        return post(api + 'storeTechnician/queryStoreTechnicianRelateList', params)
    },
    // 店铺审核技师关联申请同意
    tongyi(params) {
        return put(api + 'storeTechnician/checkStoreTechnicianRelate', params)
    },
    // 店铺解绑技师
    delShopJishi(params) {
        return deleteUrl(api + 'storeTechnician/storeUnbindTechnician/' + params)
    },
    // 修改店铺账号信息
    changeAccount(params) {
        return put(api + 'store/updateStoreAccount', params)
    },
    // 查询技师关联店铺详情
    jsGuanlianShop() {
        return get(api + 'storeTechnician/queryStoreTechnicianRelateDetail')
    },
    // 账户信息
    zhanghu() {
        return get(api + 'account/account')
    },
    // 账户信息资金分类
    zijinClass() {
        return get(api + 'account/classify')
    },
    // 个人信息查询详情
    personalDetail() {
        return get(api + 'technician/queryTechnicianDetail')
    },
    // 个人信息提交审核
    personalSubmit(params) {
        return put(api + 'technician/updateTechnician', params)
    },
    // 技术账号详情
    jsZhanghao() {
        return get(api + 'store/queryAccount')
    },
    // 店铺中心详情
    storeDetail() {
        return get(api + 'store/queryStoreDetail')
    },
    // 修改店铺中心详情
    changeStoreDetail(params) {
        return put(api + 'store/updateStore', params)
    },
    // 新增/修改技师账户信息
    jishizhanghu(params) {
        return post(api + 'technicianAccount/addAndUpdateTechnicianAccount', params)
    },
    // 新增/修改技师账户信息 详情
    jishizhanghuDetail() {
        return get(api + 'technicianAccount/queryTechnicianAccountInfo')
    },
		// 
	  queryCarBrandAllList (params) {
	  	return get(api + 'vehicleConfig/queryAllList', params)
	  },
	  // 添加车辆检查
	  addVehicleTesting (params) {
	  	return post(api + 'vehicletesting/addVehicleTesting', params)
	  },
	  // 查询车辆检查
	  queryVehicleTesting (params) {
	  	return get(api + 'vehicletesting/queryVehicleTesting', params)
	  },
	  // 获取账户信息
	  getUserInfo (params) {
	  	return get(api + 'login/getTokenUser', params)
	  },
	  // 根据登录用户token查询店铺详情
	  queryStoreDetailByUser (params) {
	  	return get(api + 'store/queryStoreDetailByUser', params)
	  }
}