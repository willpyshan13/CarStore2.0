package com.yanxin.store.mvvm.entity


data class fff(
    val code: String,
    val `data`: List<RespWorkOrderEntity>,
    val msg: String,
    val pages: Int,
    val size: Int,
    val success: Boolean,
    val total: Int
)

data class RespWorkOrderEntity(
    val actualDate: String,
    val createdBy: String,
    val createdTime: String,
    val duration: Int,
    val durationPrice: Int,
    val lastUpdatedBy: String,
    val lastUpdatedTime: String,
    val orderNum: String,
    val orderSts: Int,
    val price: Int,
    val remark: String,
    val storeName: String,
    val storeUserName: String,
    val storeUserUuid: String,
    val useDate: String,
    val useTime: String,
    val uuid: String,
    val vehicleBrand: String,
    val vehicleBrandName: String,
    val vehicleModel: String,
    val vehicleModelName: String,
    val vehicleUserName: String,
    val vehicleUserUuid: String
)