package com.yanxin.store.mvvm.entity

data class ReqWorkOrderEntity(
    val orderSts: List<Int>,
    val pageNum: Int=1,
    val pageSize: Int=10,
    val storeUserUuid: String?=null,
    val uuid: String,
    val vehicleUserUuid: String?=null
)