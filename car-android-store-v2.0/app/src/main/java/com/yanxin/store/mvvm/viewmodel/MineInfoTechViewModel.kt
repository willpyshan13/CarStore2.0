package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.yanxin.store.bean.BrandBean
import com.yanxin.store.bean.CityBean
import com.yanxin.store.bean.DepositBankBean
import com.yanxin.store.mvvm.entity.*
import com.yanxin.store.mvvm.reposityry.MineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Desc:
 *
 * Date: 2020-12-16
 * Copyright: Copyright (c) 2018-2020
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author: pengyushan
 */
class MineInfoTechViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val headerUrl = ObservableField("")
    val techName = ObservableField("")

    val techLevel = ObservableField("普通")

    val storeType = ObservableField("身份证")

    val storeTypeValue = ObservableField("")

    val storePhone = ObservableField("")

    val techWorkYear = ObservableField("")

    val storeBrand = ObservableField("")

    val storeCompany = ObservableField("")

    val storeAddress = ObservableField("")

    val storeDetailAddress = ObservableField("")

    val technicianAddress = ObservableField("")

    val techType = ObservableField("")

    val emergencyNumber = ObservableField("")

    val bankName = ObservableField("")

    val bankCard = ObservableField("")

    val storeWechat = ObservableField("")

    val storeAlipay = ObservableField("")

    val storeRole = ObservableField("技师")

    val storeStatus = ObservableField("")

    var detailInfo = ObservableField<RespStoreInfoEntity>()

    val showArea = SingleLiveEvent<Void>()

    val showBank = SingleLiveEvent<Void>()

    val showBrand = SingleLiveEvent<Void>()

    val showPick = SingleLiveEvent<Void>()

    val showHeaderPick = SingleLiveEvent<Void>()

    var mBrandList: ArrayList<BrandBean.DataBean>? = null

    var mSelectBrandList: ArrayList<String> = ArrayList()

    var mBankList: ArrayList<DepositBankBean.DataBean>? = null

    override fun onCreate() {
        super.onCreate()
        setTitleText("个人信息")
        getPersonInfo()
        getAccountInfo()
    }

    fun getBank() {
        launch({
            mBankList = model.queryBankList()
            showBank.call()
        })
    }

    fun getBrandList(brandUuidList: List<Brand>? = null, needShowBrand: Boolean = false) {
        launch({
            mBrandList = model.queryBrandList()
            if (needShowBrand) {
                showBrand.call()
            } else {
                brandUuidList?.let {
                    var brand = StringBuffer()
                    for (i in brandUuidList.indices) {
                        brand.append(it[i].brandName + "/")
                    }
                    brand.deleteCharAt(brand.length - 1)
                    storeBrand.set(brand.toString())
                }

            }
        })
    }


    var cityBean = mutableListOf<CityBean.DataBean>()
    var areaBean = mutableListOf<List<CityBean.DataBean>>()
    var provinceBean = mutableListOf<ArrayList<ArrayList<CityBean.DataBean>>>()
    private fun getCityList() {
        launch({
            withContext(Dispatchers.IO) {
                ToastUtils.showShort("地图数据正在加载，请稍后...")
                val mCityAll = model.queryArea()
                //先获取一级的所有省份
                for (i in mCityAll.indices) {
                    if (mCityAll[i].parentUuid == "-1") {
                        cityBean.add(mCityAll[i])
                    }
                }
                for (i in cityBean.indices) {
                    val area = ArrayList<CityBean.DataBean>() //该省的城市列表（第二级）
                    val province_AreaList =
                            ArrayList<ArrayList<CityBean.DataBean>>() //保存第三级区联动信息
                    //获取二级所有市
                    for (j in cityBean.size until mCityAll.size) { //遍历该省份的所有城市
                        if (cityBean[i].uuid == mCityAll[j].parentUuid) {
                            area.add(mCityAll[j])
                            val city_areaList = ArrayList<CityBean.DataBean>() //该城市的所有地区列表
                            //获取三级所有区
                            for (k in cityBean.size until mCityAll.size) {
                                if (mCityAll[j].uuid == mCityAll[k].parentUuid) {
                                    city_areaList.add(mCityAll[k])
                                }
                            }
                            province_AreaList.add(city_areaList)
                        }
                    }
                    /***
                     * 添加城市数据
                     */
                    areaBean.add(area)
                    /**
                     * 添加地区数据
                     */
                    provinceBean.add(province_AreaList)
                }
            }

            showArea.call()
        })
    }

    private fun getPersonInfo() {
        launch({
            val data = model.queryTechnicianDetail()
            detailInfo.set(data)
            headerUrl.set(data.photoImgUrl)
            techName.set(data.userName)
            storeType.set(data.certificateTypeName)
            storeTypeValue.set(data.certificateNum)
            storePhone.set(data.mobile)
            techWorkYear.set(data.workingYear.toString())
            storeAddress.set(data.addressProvinceName + data.addressCityName + data.addressCountyName)
            storeBrand.set(data.brandList.firstOrNull()?.brandName)
            storeCompany.set(data.companyName)
            storeDetailAddress.set(data.companyAddressDetail)
            technicianAddress.set(data.addressDetail)
            techType.set(data.technologyTypeName)
            emergencyNumber.set(data.relativeMobile)
            bankName.set(data.technicianAccount.depositBank)
            bankCard.set(data.technicianAccount.cardNumbers)
            storeWechat.set(data.technicianAccount.weChatAccount)
            storeAlipay.set(data.technicianAccount.alipayAccount)
            techLevel.set(getTechText(data.cybAuth))
            storeStatus.set(getStatusText(data.checkSts))
            getBrandList(data.brandList)
        })
    }

    fun setBankInfo(bankName: String?) {
        this.bankName.set(bankName)
    }

    fun setSelectBrand(brandName: String?, uuids: List<String>) {
        storeBrand.set(brandName)
        mSelectBrandList.clear()
        mSelectBrandList.addAll(uuids)
    }

    val headClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showHeaderPick.call()
        }
    })

    val submitClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            submitTech()
        }
    })

    val bankClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (mBankList == null) {
                getBank()
            } else {
                showBank.call()
            }
        }
    })

    val areaClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (provinceBean.isEmpty()) {
                getCityList()
            } else {
                showArea.call()
            }
        }
    })

    val brandClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (mBrandList != null) {
                showBrand.call()
            } else {
                getBrandList(needShowBrand = true)
            }
        }
    })

    private fun getAccountInfo() {
        launch({
            val data = model.getAccountInfo()
            headerUrl.set(data.userInfoRes.headImage)
        })
    }


    private fun submitTech() {
        launch({
            detailInfo.get()?.let {
                it.certificateNum = storeTypeValue.get()
                it.workingYear = techWorkYear.get()
                it.addressCounty = storeAddress.get()
                it.addressDetail = technicianAddress.get()
                it.technologyTypeName = techType.get()
                it.relativeMobile = emergencyNumber.get()
                it.technicianAccount = getTechAccount()
                it.brandUuidList = mSelectBrandList
                it.checkSts = 0
                model.updateTechnician(it)
                ToastUtils.showShort("修改成功")
            }
        })
        launch({
            detailInfo.get()?.let {
                it.certificateNum = storeTypeValue.get()
                it.workingYear = techWorkYear.get()
                it.addressCounty = storeAddress.get()
                it.addressDetail = technicianAddress.get()
                it.technologyTypeName = techType.get()
                it.relativeMobile = emergencyNumber.get()
                it.technicianAccount = getTechAccount()
                it.brandUuidList = mSelectBrandList
                model.updateTechnician(it)
                ToastUtils.showShort("修改成功")
            }
        })
    }

    private fun getTechAccount(): TechnicianAccount {
        return TechnicianAccount(
                depositBank = bankName.get(),
                cardNumbers = bankCard.get(),
                alipayAccount = storeAlipay.get(),
                weChatAccount = storeWechat.get()
        )
    }

    fun uploadImage(file: File) {
        val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val part = MultipartBody.Part.createFormData("file", file.getName(), requestFile)
        launch({
            val upload = model.uploadHeadImage(part, "other")
            headerUrl.set(upload)
            model.updateUserPhotoImg(upload)
            dismissDialog()
        }, {
            dismissDialog()
        })

    }

    fun getUuid(): String? {
        return detailInfo.get()?.uuid
    }


    private fun getStatusText(status: Int): String {
        return when (status) {
            0 -> {
                "待审核"
            }
            1 -> {
                "审核通过"
            }
            else -> {
                "审核驳回"
            }
        }
    }

    private fun getTechText(status: Int): String {
        return when (status) {
            0 -> {
                "普通"
            }
            else -> {
                "专家"
            }
        }
    }
}