package com.yanxin.store.mvvm.reposityry

import com.will.habit.base.BaseModel
import com.will.habit.extection.check
import com.will.habit.http.RetrofitClient
import com.yanxin.store.MyApplication
import com.yanxin.store.mvvm.entity.*
import com.yanxin.store.mvvm.service.MineService
import okhttp3.MultipartBody

class MineRepository : BaseModel<Any>() {
    private val mineService by lazy { RetrofitClient.getInstance(MyApplication.BASE_URL).create(MineService::class.java) }

    suspend fun getMessageList(type:String) = mineService.getMessageList(type)

    suspend fun getAccountInfo() = mineService.getAccountInfo().check()

    suspend fun queryStoreDetailByUser() = mineService.queryStoreDetailByUser().check()

    suspend fun updateStore(access: RespStoreInfoEntity) = mineService.updateStore(access).check()

    suspend fun queryTechnicianDetail() = mineService.queryTechnicianDetail().check()

    suspend fun updateTechnician(access: RespStoreInfoEntity) = mineService.updateTechnician(access)

    suspend fun getAccountHeaderUrl() = mineService.getAccountHeaderUrl().check()

    suspend fun getBannerList() = mineService.queryBannerList(ReqBannerEntity()).check()

    suspend fun getAccountExit() = mineService.getAccountExit().check()

    suspend fun queryOrderVehicleStationList(data: ReqWorkOrderEntity) = mineService.queryOrderVehicleStationList(data)

    suspend fun sceneOrderStatistics(data: ReqOnSiteSupportEntity) = mineService.sceneOrderStatistics(data).check()

    suspend fun statisticsIncomeAmount(data: ReqOnSiteSupportEntity) = mineService.statisticsIncomeAmount(data).check()

    suspend fun statisticsPayAmount(data: ReqOnSiteSupportEntity) = mineService.statisticsPayAmount(data).check()

    suspend fun updateMessageStatus() = mineService.updateMessageStatus()

    suspend fun updateMessageStatusById(id:String) = mineService.updateMessageStatusById(id)

    suspend fun delMessageStatusById(id:String) = mineService.deleteMessageStatusById(id)

    suspend fun withdrawal(id: ReqWithDrawEntity) = mineService.withdrawal(id)

    suspend fun queryGoodsGroupAmount(id:String) = mineService.queryGoodsGroupAmount(id).check()

    suspend fun queryGoodsGroupCount(id:String) = mineService.queryGoodsGroupCount(id).check()

    suspend fun queryListByTypePersonType() = mineService.queryListByTypePersonType().check()

    suspend fun queryArea() = mineService.queryArea().check()

    suspend fun queryBrandList(parentUUid:String="0001") = mineService.queryBrandList(parentUUid).check()

    suspend fun queryBankList() = mineService.queryBankList().check()

    suspend fun uploadHeadImage(part: MultipartBody.Part,type:String) = mineService.uploadFile(part,type).check()

    suspend fun updateUserPhotoImg(type:String) = mineService.updateUserPhotoImg(ReqUserHeaderEntity(type)).check()
}