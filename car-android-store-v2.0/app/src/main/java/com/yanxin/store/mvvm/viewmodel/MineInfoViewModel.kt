package com.yanxin.store.mvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.will.habit.base.BaseViewModel
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.bus.event.SingleLiveEvent
import com.will.habit.extection.launch
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.R
import com.yanxin.store.bean.BrandBean
import com.yanxin.store.bean.CityBean
import com.yanxin.store.bean.DepositBankBean
import com.yanxin.store.mvvm.entity.*
import com.yanxin.store.mvvm.reposityry.MineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import okhttp3.MediaType
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
class MineInfoViewModel(application: Application) : BaseViewModel<MineRepository>(application) {

    val headerUrl = ObservableField("")

    val storeName = ObservableField("")

    val storeType = ObservableField("")

    val storePhone = ObservableField("")

    val storeRole = ObservableField("店铺")

    val storeBrand = ObservableField("")

    val storeCompany = ObservableField("")

    val storeAddress = ObservableField("")

    val storeDetailAddress = ObservableField("")

    val storeBank = ObservableField("")

    val storeAccount = ObservableField("")

    val storeWechat = ObservableField("")

    val storeAlipay = ObservableField("")

    val brandName = ObservableField("")

    val storeLicense = ObservableField("已上传")

    val storePic = ObservableField("已上传")
    val storeHolderLicense = ObservableField("已上传")

    val storeStatus = ObservableField("")

    var detailInfo = ObservableField<RespStoreInfoEntity>()

    var infoItems = ObservableArrayList<MineInfoItem>()

    var infoItemsBinding =
            ItemBinding.of<MineInfoItem>(BR.viewModel, R.layout.activity_mine_info_item)

    val showDialog = SingleLiveEvent<Void>()

    var personTypeListName: Array<String>? = null
    var personTypeList: List<RespTypeEntity>? = null

    /**
     * 是否是店铺
     */
    val accountStoreType = ObservableBoolean(MyApplication.isStore())
    var mBankList: ArrayList<DepositBankBean.DataBean>? = null

    var mBrandList: ArrayList<BrandBean.DataBean>? = null
    val showBrand = SingleLiveEvent<Void>()

    val showPick = SingleLiveEvent<Void>()

    var mSelectBrandList: ArrayList<String> = ArrayList()

    val showBank = SingleLiveEvent<Void>()

    val showArea = SingleLiveEvent<Void>()
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
                    /***
                     * 添加城市数据
                     */
                    areaBean.add(area)
                    /**
                     * 添加地区数据
                     */
                    /**
                     * 添加地区数据
                     */
                    provinceBean.add(province_AreaList)
                }
            }

            showArea.call()
        })
    }

    override fun onCreate() {
        super.onCreate()
        setTitleText("企业信息")
        getPersonInfo()
        getAccountInfo()
        queryListByTypePersonType()
    }

    fun getBank() {
        launch({
            mBankList = model.queryBankList()
            showBank.call()
        })
    }

    val areaClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (provinceBean.isEmpty()) {
                getCityList()
            } else {
                showArea.call()
            }
        }
    })

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

    val headClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showPick.call()
        }
    })

    private fun queryListByTypePersonType() {
        launch({
            val data = model.queryListByTypePersonType()
            personTypeList = data
            personTypeListName = personTypeList?.map { it.lableDesc }?.toTypedArray()
        })
    }

    private fun getPersonInfo() {
        launch({
            val data = model.queryStoreDetailByUser()
            detailInfo.set(data)
            storeName.set(data.storeName)
            storeType.set(if (data.storeType == "102") "4S店" else "其他")
            storePhone.set(data.glyMobile)
            storeBrand.set(data.brandUuidList?.firstOrNull())
            storeCompany.set(data.companyName)
            storeAddress.set(data.addressProvinceName + data.addressCityName + data.addressCountyName)
            storeDetailAddress.set(data.companyAddressDetail)
            storeBank.set(data.storeAccountRes?.depositBank)
            storeAccount.set(data.storeAccountRes?.cardNumbers)
            storeWechat.set(data.storeAccountRes?.weChatAccount)
            storeAlipay.set(data.storeAccountRes?.alipayAccount)
            storeStatus.set(getStatusText(data.checkSts))
            val infoList = data.storeUserResList?.map { MineInfoItem(this, it) }.orEmpty()
            infoItems.addAll(infoList)

            getBrandList(data.storeBrandResList)
        })
    }

    private fun getAccountInfo() {
        launch({
            val data = model.getAccountInfo()
            headerUrl.set(data.userInfoRes.headImage)
        })
    }

    fun setBankInfo(bankName: String?) {
        this.storeBank.set(bankName)
    }

    val submitClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            submitStore()
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

    val addInfoClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            infoItems.add(
                    MineInfoItem(
                            this@MineInfoViewModel,
                            StoreUserRes("", "", "", "", "", "", "")
                    )
            )
        }
    })

    private fun submitStore() {
        launch({
            detailInfo.get()?.let {
                it.storeUserReq = getStoreUser()
                it.storeAccountRes = getStoreAccountRes()
                it.brandUuidList = mSelectBrandList
                it.checkSts = 0
                model.updateStore(it)
                ToastUtils.showShort("修改成功")
            }
        })
    }

    val brandClick = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (mBrandList != null) {
                showBrand.call()
            } else {
                getBrandList(needShowBrand = true)
            }
        }
    })

    fun setSelectBrand(brandName: String?, uuids: List<String>) {
        storeBrand.set(brandName)
        mSelectBrandList.clear()
        mSelectBrandList.addAll(uuids)
    }

    fun getBrandList(brandUuidList: List<StoreBrandResList>? = null, needShowBrand: Boolean = false) {
        launch({
            mBrandList = model.queryBrandList()
            if (needShowBrand) {
                showBrand.call()
            } else {
                brandUuidList?.let {
                    var brand = StringBuffer()
                    for (i in brandUuidList.indices) {
                        brand.append(it[i].configName + "/")
                    }
                    brand.deleteCharAt(brand.length - 1)
                    brandName.set(brand.toString())
                }
            }
        })
    }


    fun getUuid(): String? {
        return detailInfo.get()?.uuid
    }

    private fun getStoreAccountRes(): StoreAccountRes {
        return StoreAccountRes(
                storeBank.get(), "",
                storeAlipay.get(), storeAccount.get(), "",
                storeBank.get(), "", getUuid(),
                storeWechat.get(), getUuid()
        )
    }

    private var currentItem: MineInfoItem? = null
    fun showRolePick(item: MineInfoItem) {
        currentItem = item
        showDialog.call()
    }

    fun setRoleText(positon: Int) {
        currentItem?.typeText?.set(personTypeList?.get(positon)?.lableDesc)
        currentItem?.personUuid = personTypeList?.get(positon)?.uuid
    }

    private fun getStoreUser(): List<StoreUserRes> {
        return infoItems.map { it.getItemData() }
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
}